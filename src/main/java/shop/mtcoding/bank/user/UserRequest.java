package shop.mtcoding.bank.user;

import lombok.Data;

// 요청 바디 데이터 관리
public class UserRequest {

    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
        private String fullname;
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }
}
