package shop.mtcoding.bank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.history.History;
import shop.mtcoding.bank.history.HistoryRepository;
import shop.mtcoding.bank.user.User;

import java.util.List;
import java.util.Objects;

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
        if (reqDTO.getWNumber().equals(0)) throw new RuntimeException("출금계좌가 존재하지 않습니다");
        // 2. 입금 계좌 존재 여부 확인
        accountRepository.findByAccountNumber(reqDTO.getDNumber().toString());
        if (reqDTO.getDNumber().equals(0)) throw new RuntimeException("입금계좌가 존재하지 않습니다");

        // 3. 출금 계좌 잔액 검증(DB 조회가 필요) - amount
        Integer balance = accountRepository.amountCheck(reqDTO.getWNumber().toString());
        if (balance < reqDTO.getAmount()) throw new RuntimeException("잔액이 부족합니다.");

        // 4. 출금 패스워드 검증 - password
        String password = accountRepository.passwordCheck(reqDTO.getWNumber().toString());
        if (!password.equals(reqDTO.getPassword())) throw new RuntimeException("비밀번호가 일치하지 않습니다.");

        // 5. 출금 계좌 업데이트
        accountRepository.updateWNumber(reqDTO.getWNumber().toString(), balance - reqDTO.getAmount());

        // 6. 입금 계좌 업데이트
        Integer dBalance = accountRepository.amountCheck(reqDTO.getDNumber().toString());
        accountRepository.updateDNumber(reqDTO.getDNumber().toString(), dBalance + reqDTO.getAmount());

        // 7. 히스토리 인서트
        History history = new History();
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
