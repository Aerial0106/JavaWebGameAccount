package JavaWeb.GameAccount.controllers;

import JavaWeb.GameAccount.model.*;
import JavaWeb.GameAccount.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trang-chu")
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final BlogService blogService;
    private final MenuService menuService;
    private final SlideService slideService;
    @GetMapping
    public String Home(Model model){
        List<Blog> blogs = blogService.findAllBlogs();
        List<Menu> menus = menuService.findAllMenu();
        List<Slide> slides = slideService.findAllSlide();
        Map<Category, List<Product>> categoryProducts =
                productService.getTop3ProductsByCategory();
        model.addAttribute("categoryProducts", categoryProducts);
        model.addAttribute("slides", slides);
        model.addAttribute("blogs", blogs);
        model.addAttribute("menus", menus);
        return "layout";
    }
}