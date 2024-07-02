package JavaWeb.GameAccount.repositories;

import JavaWeb.GameAccount.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface ICategoryRepository extends JpaRepository<Category, Integer>{
    Category findByLink(String link);

}
