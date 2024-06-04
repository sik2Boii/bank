package shop.mtcoding.bank.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    // 회원가입
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    // 로그인
    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/account/list";
    }
}
