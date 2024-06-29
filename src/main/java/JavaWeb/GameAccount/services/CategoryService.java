package JavaWeb.GameAccount.services;

import JavaWeb.GameAccount.model.Category;
import JavaWeb.GameAccount.repositories.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final ICategoryRepository categoryRepository;


    public  List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
    public void updateCategory(@NotNull Category category) {
        Category existingCategory = categoryRepository.findById((int)
                        category.getId())
                .orElseThrow(() -> new IllegalStateException("Category with ID " + category.getId() + " does not exist."));
        existingCategory.setName(category.getName());
        existingCategory.setOrder(category.getOrder());
        existingCategory.setProducts(category.getProducts());
        categoryRepository.save(existingCategory);
    }
    public void deleteCategoryById(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Category with ID " + id + " does not exist.");
        }
        categoryRepository.deleteById(id);
    }
    public Category findByLink(String link) {
        return categoryRepository.findByLink(link);
    }

}
