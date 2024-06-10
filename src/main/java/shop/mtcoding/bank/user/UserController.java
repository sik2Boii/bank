package shop.mtcoding.bank.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final HttpSession session;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public String login(String username, String password) {
        User sessionUser = userRepository.findByUsernameAndPasswordV2(username, password);
        if (sessionUser == null) {
            throw new RuntimeException("아이디 혹은 패스워드가 틀렸습니다");
        } else {
            session.setAttribute("sessionUser", sessionUser);
            return "redirect:/account/list";
        }
    }

    // V1
    @GetMapping("/hello1")
    public void hello(HttpServletResponse response) {
        System.out.println("==========helloV1==========");
        response.setHeader("Location", "http://localhost:8080/login-form");
        response.setStatus(302);
    }

    // V2
    @GetMapping("/hello2")
    public String helloV2(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("==========helloV2==========");
        return "redirect:/login-form";
    }

    // V3
    @GetMapping("/hello3")
    public void helloV3(HttpServletResponse response) throws IOException {
        System.out.println("==========helloV3==========");
        response.sendRedirect("/login-form");
    }


    @GetMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
        // response.sendRedirect("/login-form");
        response.setHeader("Location", "http://localhost:8080/login-form");
        response.setStatus(302);
    }

    // http3.0
    // http1.1 - 사용중 Post, Get, Put, Delete
    // http1.0 - Post(insert, delete, update), Get(select)
    // Post(insert), Get(select)
    @PostMapping("/join")
    public String join(String username, String password, String email, String fullname) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("email: " + email);
        System.out.println("fullname: " + fullname);

        return "redirect:/login-form";
    }

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
