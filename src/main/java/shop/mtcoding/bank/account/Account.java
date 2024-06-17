package shop.mtcoding.bank.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.user.User;

/**
 * 사용자 정보를 나타내는 JPA 엔티티 클래스입니다.
 * 이 클래스는 JPA 엔티티로 지정되어 테이블을 자동으로 생성합니다.
 */

@Entity // 해당 클래스를 JPA 엔티티로 지정합니다.
@Table(name = "account_tb") // 테이블의 이름을 "user_tb"로 지정합니다.
@Getter // 해당 필드에 대한 Getter 메서드를 자동으로 생성합니다. 값을 외부에서 읽기 위해 사용합니다.
@Setter // 해당 필드에 대한 Setter 메서드를 자동으로 생성합니다. 해당 필드에 값을 할당하기 위해 사용합니다.
public class Account {

    @Id // Primary Key로 지정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스에서 자동으로 생성되는 값(auto_increment)으로 설정합니다.
    private Integer id; // 사용자 식별자

    @Column(unique = true, length = 4) // 해당 필드가 유일한 값을 가지도록 제약을 설정합니다.
    private String number; // 계좌 번호

    @Column(length = 12) // 비밀번호의 최대 길이를 12자로 제한합니다.
    private String password; // 계좌 비밀번호

    // 1000원 디폴트
    private Integer balance; // 잔액

    // FK
    @ManyToOne
    private User user; // hibernate - orm 기술
}

