package shop.mtcoding.bank.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.bank.user.User;

import java.util.List;

@Import(AccountRepository.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void passwordTest(){
        String password = accountRepository.passwordCheck("1111");

        System.out.println("@@@@@@@@@@@@@@" + password);
    }

    @Test
    public void findByAccountNumber() {
        String wNumber = "1100";
        int result = accountRepository.findByAccountNumber(wNumber);

        System.out.println("@@@@@@@@@@@@@: " + result);
    }

    @Test
    public void balanceCheck() {
        Integer balace = accountRepository.amountCheck("2222");
        System.out.println("@@@@@@@@@@:" + balace);
    }

    @Test
    public void findAll_test() {
        // given
        Integer sessionUserId = 1;

        // when
        List<Account> accountList = accountRepository.findAll(sessionUserId);

        // eye
        accountList.forEach(account -> {
            System.out.println(account.getId());
            System.out.println(account.getNumber());
            System.out.println(account.getPassword());
            System.out.println(account.getBalance());
            System.out.println(account.getUser().getFullname());
            System.out.println("=====");
        });
    }

    @Test
    public void findAllV2_test() {
        System.out.println("테스트 시작 V2 @@@@@@@@@@");
        // given
        Integer sessionUserId = 1;

        // when
        List<Account> accountList = accountRepository.findAllV2(sessionUserId);

        // eye
        accountList.forEach(account -> {
            System.out.println(account.getId());
            System.out.println(account.getNumber());
            System.out.println(account.getBalance());
            System.out.println(account.getPassword());
            System.out.println(account.getUser().getFullname()); // ORM
            System.out.println("=================");
        });
    }


    @Test
    public void save_test() {
        // given
        User sessionUser = new User();
        sessionUser.setId(1);
        sessionUser.setUsername("ssar");
        sessionUser.setPassword("1234");
        sessionUser.setEmail("ssar@nate.com");
        sessionUser.setFullname("쌀");

        Account account = new Account();
        account.setNumber("3333");
        account.setPassword("1234");
        account.setBalance(1000);
        account.setUser(sessionUser);

        // when
        accountRepository.save(account);

        // then
        System.out.println("account id : " + account.getId());
        System.out.println("account number : " + account.getNumber());
        System.out.println("account password : " + account.getPassword());
        System.out.println("account balance : " + account.getBalance());
        System.out.println("account userId : " + account.getUser().getId());
    }
}