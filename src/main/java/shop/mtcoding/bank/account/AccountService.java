package shop.mtcoding.bank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.history.HistoryRepository;
import shop.mtcoding.bank.user.User;

import java.util.List;

// 트랜잭션 관리, 비지니스 로직 관리
// 화면에 필요한 데이터만 담기
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public void 계좌이체(AccountRequest.TransferDTO reqDTO) {
        // 1. 출금 계좌 존재 여부 확인
        accountRepository.findByAccountNumber(reqDTO.getWNumber().toString());
        if (reqDTO.getWNumber() == null) throw new RuntimeException("출금계좌가 존재하지 않습니다");
        System.out.println(reqDTO.getWNumber().toString());
        // 2. 입금 계좌 존재 여부 확인
        accountRepository.findByAccountNumber(reqDTO.getDNumber().toString());
        if (reqDTO.getDNumber() == null) throw new RuntimeException("입금계좌가 존재하지 않습니다");

        // 3. 출금 계좌 잔액 검증(DB 조회가 필요) - amount
        // accountRepository.amountCheck()

        // 4. 출금 패스워드 검증 - password

        // 5. 출금 계좌 업데이트

        // 6. 입금 계좌 업데이트

        // 7. 히스토리 인서트
    }


    // 서비스 이름은 명확하게 정해야 함!
    public AccountResponse.ListDTO 나의계좌목록(Integer sessionUserId) {
        List<Account> accountList = accountRepository.findAll(sessionUserId);
        return new AccountResponse.ListDTO(accountList);
    }

    public List<Account> 나의계좌목록V3(Integer sessionUserId) {
        List<Account> accountList = accountRepository.findAll(sessionUserId);
        return accountList;
    }

    @Transactional
    public void 계좌생성(AccountRequest.SaveDTO reqDTO, User sessionUser) {

        accountRepository.save(reqDTO.toEntity(sessionUser));
    }
}
