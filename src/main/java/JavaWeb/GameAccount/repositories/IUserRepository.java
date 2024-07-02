package JavaWeb.GameAccount.repositories;

import JavaWeb.GameAccount.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByUsername(String username);
}


