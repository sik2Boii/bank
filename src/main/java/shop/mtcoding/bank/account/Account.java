package shop.mtcoding.bank.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account_tb")
@Getter
@Setter
public class Account {

    @Id
    private Integer account_num; // 계좌 번호
    private String name; // 예금주명
    private Integer amount; // 잔액
}
