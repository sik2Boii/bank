package shop.mtcoding.bank.account;

import lombok.Data;
import shop.mtcoding.bank.user.User;

public class AccountRequest {

    @Data
    public static class TransferDTO {
        private Integer wNumber;
        private Integer dNumber;
        private Integer amount;
        private String password;
    }

    // SaveDTO 책임: 요청데이터 파싱 잘하고, 엔티티로 변화하기
    @Data
    public static class SaveDTO {
        private String number;
        private String password;

        public Account toEntity(User sessionUser) {
            Account account = new Account();
            account.setNumber(number);
            account.setPassword(password);
            account.setUser(sessionUser);
            account.setBalance(1000);
            return account;
        }
    }
}
