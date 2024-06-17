package shop.mtcoding.bank.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.bank.user.User;

import java.util.List;

// 스프링이 관리하는 객체가 됨 - IOC(Inversion of Control)
@Slf4j // 로그 라이브러리
@RequiredArgsConstructor
@Controller // 컴퍼넌트 스캔(shop.mtcoding.bank 패키지 이하)
public class AccountController {

    private final HttpSession session;
    private final AccountService accountService;

    @PostMapping("/account/transfer")
    public String accountTransfer(AccountRequest.TransferDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다.");

        // 0원 이하 검증 amount (유효성 검사)
        if (reqDTO.getAmount() <= 0) throw new RuntimeException("이체 금액은 1원 이상이어야 합니다.");

        accountService.계좌이체(reqDTO);

        return "redirect:/account/" + reqDTO.getWNumber();
    }

    @PostMapping("/account/save")
    public String accountSave(AccountRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다.");

        accountService.계좌생성(reqDTO, sessionUser);

        return "redirect:/account/list";
    }

    // @RequestMapping(method = RequestMethod.GET, value = "/home")
    // 계좌 리스트 페이지
    @GetMapping({"/", "/account/list"})
    public String accountList(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다.");

        AccountResponse.ListDTO resDTO = accountService.나의계좌목록(sessionUser.getId());
        request.setAttribute("model", resDTO);

        return "account/list"; // templates/list.mustache 파일을 읽어서 응답
    }

    @GetMapping("/account/list/v2")
    public @ResponseBody AccountResponse.ListDTO accountListV2() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다.");

        AccountResponse.ListDTO resDTO = accountService.나의계좌목록(sessionUser.getId());

        return resDTO;
    }

    @GetMapping("/account/list/v3")
    public @ResponseBody List<Account> accountListV3() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다.");

        List<Account> resDTO = accountService.나의계좌목록V3(sessionUser.getId());

        return resDTO;
    }

    // 계좌 생성 페이지
    @GetMapping("/account/save-form")
    public String accountSaveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다.");
        System.out.println("save-form");
        return "account/save-form";
    }

    // 이체 페이지
    @GetMapping("/account/transfer-form")
    public String accountTransferForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다.");
        System.out.println("accountTransferForm");
        return "account/transfer-form";
    }

    // 계좌 상세 페이지 {유니크 한 것만 담자 !!! UK, PK}
    @GetMapping("/account/{number}")
    public String detail(@PathVariable("number") Integer number) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new RuntimeException("인증되지 않은 사용자입니다.");
        System.out.println("detail");
        return "account/detail";
    }
}
