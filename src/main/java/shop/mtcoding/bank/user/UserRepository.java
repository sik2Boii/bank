package shop.mtcoding.bank.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // final 변수를 초기화하는 생성자를 만들어준다.
@Repository // ioc 등록
public class UserRepository {

    //@Autowired // 의존성 주입
    private final EntityManager em;


    // 의존성 주입
//    public UserRepository(EntityManager em) {
//        this.em = em;
//    }

    @Transactional
    public void save(String username, String password, String email, String fullname) {
        Query query =
                em.createNativeQuery("insert into user_tb(username, password, email, fullname) values(?,?,?,?)");
        query.setParameter(1, username);
        query.setParameter(2, password);
        query.setParameter(3, email);
        query.setParameter(4, fullname);

        query.executeUpdate(); // write (insert, delete, update)
    }

    @Transactional
    public void saveV2(String username, String password, String email, String fullname) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFullname(fullname);

        em.persist(user);
    }

    @Transactional
    public User findByUsernameAndPassword(String username, String password) {
        Query query =
                em.createNativeQuery("select * from user_tb where username=? and password=?");
        query.setParameter(1, username);
        query.setParameter(2, password);

        try {
            Object[] obs = (Object[]) query.getSingleResult();
            // [0] -> id, [1] -> username, [2] -> password, [3] -> email, [4] -> fullname

            User user = new User();
            user.setId((Integer) obs[0]);
            user.setPassword((String) obs[1]);
            user.setEmail((String) obs[2]);
            user.setFullname((String) obs[3]);
            user.setUsername((String) obs[4]);

            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public User findByUsernameAndPasswordV2(String username, String password) {
        Query query =
                em.createNativeQuery("select * from user_tb where username=? and password=?", User.class); // 엔티티
        query.setParameter(1, username);
        query.setParameter(2, password);

        try {
            // NOTE: user 클래스에 디폴트 생성자가 있어야 하이버네이트가 new를 해서 파싱해준다.
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }

    }

    public User findByUsernameAndPasswordV3(String username, String password) {
        // JPQL
        Query query =
                em.createQuery("select u from User u where u.username=:username and u.password=:password", User.class); // 엔티티
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }

    }

}