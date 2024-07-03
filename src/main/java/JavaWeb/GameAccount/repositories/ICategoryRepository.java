package JavaWeb.GameAccount.repositories;

import JavaWeb.GameAccount.model.Category;
import JavaWeb.GameAccount.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ICategoryRepository extends JpaRepository<Category, Long>{
    Category findByLink(String link);
}
