package shop.mtcoding.bank.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 스프링이 관리하는 객체가 됨 - IOC(Inversion of Control)
@Slf4j // 로그 라이브러리
@Controller // 컴퍼넌트 스캔(shop.mtcoding.bank 패키지 이하)
public class AccountController {

    // @RequestMapping(method = RequestMethod.GET, value = "/home")
    // 계좌 리스트 페이지
    @GetMapping("/account/list")
    public String accountList() {
        System.out.println("home 호출됨");
        return "account/list"; // templates/home.mustache 파일을 읽어서 응답
    }

    // 계좌 생성 페이지
    @GetMapping("/account/save-form")
    public String accountSaveForm() {
        System.out.println("save-form");
        return "account/save-form";
    }

    // 이체 페이지
    @GetMapping("/account/transfer-form")
    public String accountTransferForm() {
        System.out.println("accountTransferForm");
        return "account/transfer-form";
    }

    // 계좌 상세 페이지
    @GetMapping("/account/1111")
    public String detail() {
        System.out.println("detail");
        return "account/detail";
    }
}
