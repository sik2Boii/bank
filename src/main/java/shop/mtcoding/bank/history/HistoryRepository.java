package shop.mtcoding.bank.history;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HistoryRepository {
    private final EntityManager em;

    public void save(History history) {
        em.persist(history);
    }
}