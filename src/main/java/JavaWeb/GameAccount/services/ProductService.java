package JavaWeb.GameAccount.services;

import JavaWeb.GameAccount.model.*;
import JavaWeb.GameAccount.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final IProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " + product.getId() + " does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setNums(product.getNums());
        existingProduct.setDetail(product.getDetail());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setOrder(product.getOrder());
        return productRepository.save(existingProduct);
    }

    public void deleteProductById(int id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }

    public Map<Category, List<Product>> getTop6ProductsByCategory() {
        List<Product> allProducts = productRepository.findAll();
        Map<Category, List<Product>> productsByCategory = allProducts.stream()
                .filter(Product::isHide)
                .collect(Collectors.groupingBy(Product::getCategory));
        productsByCategory.forEach((category, products) -> {
            List<Product> top6Products = products.stream()
                    .sorted(Comparator.comparingInt(Product::getOrder))
                    .limit(6)
                    .collect(Collectors.toList());
            productsByCategory.put(category, top6Products);
        });
        return productsByCategory;
    }

    public Map<Category, List<Product>> getTop3ProductsByCategory() {
        List<Product> allProducts = productRepository.findAll();
        Map<Category, List<Product>> productsByCategory = allProducts.stream()
                .filter(Product::isHide)
                .collect(Collectors.groupingBy(Product::getCategory));
        productsByCategory.forEach((category, products) -> {
            List<Product> top3Products = products.stream()
                    .sorted(Comparator.comparingInt(Product::getOrder))
                    .limit(3)
                    .collect(Collectors.toList());
            productsByCategory.put(category, top3Products);
        });
        return productsByCategory;
    }
    public List<Product> getProductsByCategoryId(int categoryId) {
        return
                productRepository.findByCategoryIdAndHideTrueOrderByOrderAsc(categoryId);
    }
}
