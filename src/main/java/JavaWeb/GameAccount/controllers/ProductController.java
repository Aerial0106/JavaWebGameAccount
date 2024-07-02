package JavaWeb.GameAccount.controllers;

import JavaWeb.GameAccount.services.*;
import JavaWeb.GameAccount.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Controller
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    private final MenuService menuService;
    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/product-list";
    }

    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/add-product";
    }
    // Process the form for adding a new product
    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "products/add-product";
        }
        productService.addProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid productId:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product,
                                BindingResult result) {
        if (result.hasErrors()) {
            product.setId(Math.toIntExact(id));
            return "/products/update-product";
        }
        productService.updateProduct(product);
        return "redirect:/products";
    }
    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }
    @GetMapping("/{category}")
    public String ProductCategory(Model model, @PathVariable String category,
                                  @RequestParam(name = "page", defaultValue = "0") int page) {
        Category cat = categoryService.findByLink(category);
        if (cat == null) {
            return "error"; // Xử lý khi không tìm thấy danh mục
        }
        int categoryId = cat.getId();
        List<Product> productsForCategory =
                productService.getProductsByCategoryId(categoryId);
        model.addAttribute("categoryName", cat.getName());
        model.addAttribute("productsForCategory", productsForCategory);
        addCommonAttributes(model);
        return "products/product";
    }
    private void addCommonAttributes(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("menus", menuService.findAllMenu());
    }
}
