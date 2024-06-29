package JavaWeb.GameAccount.repositories;

import JavaWeb.GameAccount.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository  extends JpaRepository<CartItem, Long>{
}
