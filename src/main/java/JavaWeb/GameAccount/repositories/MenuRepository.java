package JavaWeb.GameAccount.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> { }
