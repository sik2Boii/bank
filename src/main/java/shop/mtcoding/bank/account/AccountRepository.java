package shop.mtcoding.bank.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.mtcoding.bank.user.User;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AccountRepository {
    private final EntityManager em;

    // 계좌 번호로 계좌 조회 필요 (JPQL)
    public Account findByAccount(String number) {
        // JPQL 쿼리를 사용하여 Account 엔티티에서 주어진 계좌 번호로 계좌를 조회
        Query query = em.createQuery("select ac from Account ac where number = :number", Account.class);
        // 쿼리 파라미터로 계좌 번호 설정
        query.setParameter("number", number);
        // 쿼리 결과를 단일 결과로 가져오기
        Account account = (Account) query.getSingleResult();
        return account;
    }

    public Account findByNumberJoinUser(String number) {
        Query query = em.createQuery("select ac from Account ac join fetch ac.user where ac.number=:number", Account.class);
        query.setParameter("number", number);

        Account account = (Account) query.getSingleResult();
        return account;
    }

    // 출금 계좌 잔액 조회
    public Integer amountCheck(String wNumber) {
        // JPQL 쿼리를 사용하여 Account 엔티티에서 주어진 계좌 번호로 잔액을 조회
        Query query = em.createQuery("select a.balance from Account a where a.number = :number");

        // 쿼리 파라미터로 계좌 번호 설정
        query.setParameter("number", wNumber);
        return (Integer) query.getSingleResult();
    }

    // 비밀번호 검증
    public String passwordCheck(String wNumber) {
        Query query = em.createQuery("select password from Account where number = :number");
        query.setParameter("number", wNumber);
        return (String) query.getSingleResult();
    }

    // TODO: 업데이트 메서드 필요 (JPQA)
    public void updateWNumber(String wNumber, Integer amount) {
        Query query = em.createQuery("update Account set balance = :amount where number = :number");
        query.setParameter("amount", amount);
        query.setParameter("number", wNumber);

        query.executeUpdate();
    }

    // 입금 계좌 업데이트
    public void updateDNumber(String wNumber, Integer amount) {
        Query query = em.createQuery("update Account set balance = :amount where number = :number");
        query.setParameter("amount", amount);
        query.setParameter("number", wNumber);
        query.executeUpdate();
    }

    // 히스토리 인서트
    public void save(Account account) {
        em.persist(account);
    }

    public List<Account> findAll(Integer sessionUserId) {
        // select * from account_tb ac inner join user_tb u on ac.user_id = u.id where u.id
        // select * from account_tb where user_id = ?
        Query query = em.createQuery("select ac from Account ac join fetch ac.user where ac.user.id = :sessionUserId");
        query.setParameter("sessionUserId", sessionUserId);

        List<Account> accountList = query.getResultList();
        return accountList;
    }

    public List<Account> findAllV2(Integer sessionUserId) {

        Query query = em.createNativeQuery("select * from account_tb ac inner join user_tb u on ac.user_id = u.id where ac.user_id = ?");
        query.setParameter(1, sessionUserId);

        List<Object[]> rs = query.getResultList();

        List<Account> accountList = new ArrayList<>();

        for (Object[] obs : rs) {
            System.out.println(obs[0]); // balance
            System.out.println(obs[1]); // accountId
            System.out.println(obs[2]); // number
            System.out.println(obs[3]); // userId
            System.out.println(obs[4]); // password
            System.out.println(obs[5]); // user객체의 id
            System.out.println(obs[6]); // user객체의 password
            System.out.println(obs[7]); // user객체의 email
            System.out.println(obs[8]); // user객체의 fullname
            System.out.println(obs[9]); // user객체의 username
            System.out.println("-----------------");

            Account account = new Account();
            account.setId((Integer) obs[1]);
            account.setNumber((String) obs[2]);
            account.setPassword((String) obs[4]);
            account.setBalance((Integer) obs[0]);

            User user = new User();
            user.setId((Integer) obs[5]);
            user.setUsername((String) obs[9]);
            user.setPassword((String) obs[6]);
            user.setEmail((String) obs[7]);
            user.setFullname((String) obs[8]);

            account.setUser(user);

            accountList.add(account);
        }

        return accountList;
    }


    public void saveV2(Account account) {
        Query query =
                em.createNativeQuery("insert into account_tb(number, password, balance, user_id) values(?,?,?,?)");
        query.setParameter(1, account.getNumber());
        query.setParameter(2, account.getPassword());
        query.setParameter(3, account.getBalance());
        query.setParameter(4, account.getUser().getId());

        query.executeUpdate();
    }
}