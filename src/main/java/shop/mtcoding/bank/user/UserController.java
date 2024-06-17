package shop.mtcoding.bank.user;

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
    // STEP: 세번째 단계 (의존성 변경)
    private final UserService userService;

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/login-form";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO reqDTO) {
        User sessionUser = userService.로그인(reqDTO);
        if (sessionUser == null) {
            throw new RuntimeException("아이디 혹은 패스워드가 틀렸습니다");
        } else {
            session.setAttribute("sessionUser", sessionUser);
            return "redirect:/account/list";
        }
    }

    @GetMapping("/test1")
    public void test1(HttpServletResponse response) throws IOException {
        //response.sendRedirect("/login-form");
        response.setHeader("Location", "http://localhost:8080/login-form");
        response.setStatus(302);
    }

    // http3.0
    // http1.1 - 사용중 Post, Get, Put, Delete
    // http1.0 - Post(insert, delete, update), Get(select)
    // Post(insert), Get(select)
    @PostMapping("/join") // 회원가입
    public String join(UserRequest.JoinDTO reqDTO) {


        // STEP: 네번째 단계
        userService.회원가입(reqDTO);

        return "redirect:/login-form";
    }


    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }
}