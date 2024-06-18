package shop.mtcoding.bank.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.history.History;
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
        Account wAccount = accountRepository.findByAccount(reqDTO.getWNumber());
        if (wAccount == null) throw new RuntimeException("출금계좌가 존재하지 않습니다");
        // 2. 입금 계좌 존재 여부 확인
        Account dAccount = accountRepository.findByAccount(reqDTO.getDNumber());
        if (dAccount == null) throw new RuntimeException("입금계좌가 존재하지 않습니다");

        // 3. 출금 계좌 잔액 검증(DB 조회가 필요) - amount
        if (wAccount.getBalance() < reqDTO.getAmount())
            throw new RuntimeException("잔액이 부족합니다. 현재잔액: " + wAccount.getBalance());

        // 4. 출금 패스워드 검증 - password
        if (!wAccount.getPassword().equals(reqDTO.getPassword())) throw new RuntimeException("출금 계좌의 패스워드 오류");

        // 5. 출금 계좌 업데이트
        wAccount.setBalance(wAccount.getBalance() - reqDTO.getAmount());

        // 6. 입금 계좌 업데이트
        dAccount.setBalance(dAccount.getBalance() + reqDTO.getAmount());

        // 7. 히스토리 인서트
        History history = new History();
        history.setWithdrawAccount(wAccount);
        history.setDepositAccount(dAccount);
        history.setWithdrawbalance(wAccount.getBalance());
        history.setDepositbalance(dAccount.getBalance());
        history.setAmount(reqDTO.getAmount());

        historyRepository.save(history);
    } // 영속화 된 객체의 상태가 변경되면, update가 일어난다.


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
