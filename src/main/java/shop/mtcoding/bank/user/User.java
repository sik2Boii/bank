package shop.mtcoding.bank.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 정보를 나타내는 JPA 엔티티 클래스입니다.
 * 이 클래스는 JPA 엔티티로 지정되어 테이블을 자동으로 생성합니다.
 */

@Entity // 해당 클래스를 JPA 엔티티로 지정합니다.
@Table(name = "user_tb") // 테이블의 이름을 "user_tb"로 지정합니다.
@Getter // 해당 필드에 대한 Getter 메서드를 자동으로 생성합니다. 값을 외부에서 읽기 위해 사용합니다.
@Setter // 해당 필드에 대한 Setter 메서드를 자동으로 생성합니다. 해당 필드에 값을 할당하기 위해 사용합니다.
public class User {

    @Id // Primary Key로 지정합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스에서 자동으로 생성되는 값(auto_increment)으로 설정합니다.
    private Integer id; // 사용자 식별자

    @Column(unique = true) // 해당 필드가 유일한 값을 가지도록 제약을 설정합니다.
    private String username; // 사용자 아이디

    @Column(length = 12) // 비밀번호의 최대 길이를 12자로 제한합니다.
    private String password; // 사용자 비밀번호

    private String email; // 사용자 이메일 주소
    private String fullname; // 사용자의 실제 이름
}

