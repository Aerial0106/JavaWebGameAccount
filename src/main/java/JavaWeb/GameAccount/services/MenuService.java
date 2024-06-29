package JavaWeb.GameAccount.services;


import JavaWeb.GameAccount.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class MenuService {
    @Autowired
    private final MenuRepository menuRepository;
    public List<Menu> findAllMenu() {
        return menuRepository.findAll();
    }
}
