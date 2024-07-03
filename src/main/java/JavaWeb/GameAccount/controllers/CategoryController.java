package JavaWeb.GameAccount.controllers;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import JavaWeb.GameAccount.services.CategoryService;
import JavaWeb.GameAccount.model.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    @GetMapping("")
    public String showAllCategories(@NotNull Model model)
    {
        model.addAttribute("categories",categoryService.getAllCategories());
        return "category/categories-list";
    }

    @GetMapping("/add")
    public String showAddForm(@NotNull Model model) {
        model.addAttribute("category", new Category());
        return "category/add-category";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category,
                              @NotNull BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            var errors = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "category/add-category";
        }
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@NotNull Model model, @PathVariable long id) {
        var category = categoryService.getCategoryById(id);
        model.addAttribute("category", category.orElseThrow(() -> new
                IllegalArgumentException("Category not found")));
        return "category/update-category";
    }

    @PostMapping("/edit")
    public String editCategory(@Valid @ModelAttribute("category") Category category,
                               @NotNull BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "category/update-category";
        }
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }

    // GET request for deleting category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable long id) {
        categoryService.getCategoryById(id)
                .ifPresentOrElse(
                        category -> categoryService.deleteCategoryById(id),
                        () -> {
                            throw new IllegalArgumentException("Category not found");
                        });
        return "redirect:/categories";
    }
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, @Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            category.setId((long) Math.toIntExact(id));
            return "/categories/update-category";
        }
        categoryService.updateCategory(category);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "redirect:/categories";
    }
}
