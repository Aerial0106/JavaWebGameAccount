package JavaWeb.GameAccount.repositories;

import JavaWeb.GameAccount.entities.GameAccount;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IGameAccountRepository extends JpaRepository<GameAccount, Long> {

    default List<GameAccount> findAllGameAccounts(Integer pageNo,
                                                  Integer pageSize,
                                                  String sortBy) {
        return findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy)))
                .getContent();
    }

    @Query("""
            SELECT ga FROM GameAccount ga
            WHERE ga.username LIKE %?1%
            OR ga.description LIKE %?1%
            OR ga.game.gameName LIKE %?1%
            """)
    List<GameAccount> searchGameAccount(String keyword);
}

