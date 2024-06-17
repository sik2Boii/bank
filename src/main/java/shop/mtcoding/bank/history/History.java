package shop.mtcoding.bank.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.bank.account.Account;

import java.sql.Timestamp;

@Entity // 해당 클래스를 JPA 엔티티로 지정합니다.
@Table(name = "account_tb") // 테이블의 이름을 "user_tb"로 지정합니다.
@Getter // 해당 필드에 대한 Getter 메서드를 자동으로 생성합니다. 값을 외부에서 읽기 위해 사용합니다.
@Setter
public class History {

    @Id // Primary Key로 지정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스에서 자동으로 생성되는 값(auto_increment)으로 설정합니다.
    private Integer id;

    @ManyToOne
    private Account withdrawAccount; // 출금 계좌
    @ManyToOne
    private Account depositAccount; // 입금 계좌

    private Integer amount; // 이체 금액(원래 Longd으로 해야함)

    private Integer withdrawbalance; // 잔액
    private Integer depositbalance; // 잔액

    @CreationTimestamp // insert 할 때, 현재 시간 들어감
    private Timestamp createdAt; //
}
