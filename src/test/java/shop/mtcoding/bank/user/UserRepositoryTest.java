package shop.mtcoding.bank.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

/**
 * UserRepository 클래스의 테스트 클래스입니다.
 */

@Import(UserRepository.class) // UserRepository를 Import하여 의존성을 주입합니다.
@DataJpaTest // 데이터베이스 관련 테스트를 진행합니다.
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository; // UserRepository를 주입받습니다.

    @Test
    public void findByUsername_test() {
        String username = "ssar";
        String password = "1234";

        User user = userRepository.findByUsernameAndPassword(username, password);
        System.out.println(user.getUsername());
    }

    @Test
    public void findByUsernameV2_test() {
        String username = "ssar";
        String password = "1234";

        User user = userRepository.findByUsernameAndPasswordV2(username, password);
        System.out.println("===========" + user.getUsername());
    }

    @Test
    public void findByUsernameV3_test() {
        String username = "ssar";
        String password = "1234";

        User user = userRepository.findByUsernameAndPasswordV3(username, password);
        System.out.println("===========" + user.getUsername());
    }

    @Test // UserRepository의 save 메서드를 테스트하는 메서드입니다.
    public void save_test() {
        // given (데이터 준비)
        String username = "haha";
        String password = "test";
        String email = "haha@test.com";
        String fullname = "하하";

        // when (테스트 대상 메서드 호출)
        userRepository.save(username, password, email, fullname);
    }

    @Test // UserRepository의 save 메서드를 테스트하는 메서드입니다.
    public void save_testV2() {
        // given (데이터 준비)
        String username = "haha";
        String password = "test";
        String email = "haha@test.com";
        String fullname = "하하";

        // when (테스트 대상 메서드 호출)
        userRepository.saveV2(username, password, email, fullname);
    }
}
