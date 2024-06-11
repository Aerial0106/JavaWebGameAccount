package JavaWeb.GameAccount.services;

import JavaWeb.GameAccount.model.CartItem;
import JavaWeb.GameAccount.model.Product;
import JavaWeb.GameAccount.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope

public class CartItemService {
    private List<CartItem> cartItems = new ArrayList<>();
    @Autowired
    private IProductRepository productRepository;
    public void addToCart(long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        cartItems.add(new CartItem(product, quantity));
    }
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void removeFromCart(int productId) {
        cartItems.removeIf(item -> item.getProduct().getId() == (productId));
    }
    public void clearCart() {
        cartItems.clear();
    }
}
