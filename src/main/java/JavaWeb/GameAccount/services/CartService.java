package JavaWeb.GameAccount.services;

import JavaWeb.GameAccount.model.Order;
import JavaWeb.GameAccount.model.OrderDetail;
import JavaWeb.GameAccount.model.daos.Cart;
import JavaWeb.GameAccount.model.daos.Item;
import JavaWeb.GameAccount.model.Product;
import JavaWeb.GameAccount.repositories.IOrderDetailRepository;
import JavaWeb.GameAccount.repositories.IOrderRepository;
import JavaWeb.GameAccount.repositories.IProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class CartService {
    private static final String CART_SESSION_KEY = "cart";
    private final IOrderRepository orderRepository;
    private final IOrderDetailRepository orderDetailRepository;
    private final IProductRepository productRepository;

    public Cart getCart(@NotNull HttpSession session) {
        return Optional.ofNullable((Cart)
                        session.getAttribute(CART_SESSION_KEY))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    session.setAttribute(CART_SESSION_KEY, cart);
                    return cart;
                });
    }

    public void updateCart(@NotNull HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeCart(@NotNull HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    public int getSumQuantity(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }

    public double getSumPrice(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToDouble(item -> item.getPrice() *
                        item.getQuantity())
                .sum();
    }

    public void saveCart(@NotNull HttpSession session, String note, String address) {
        var cart = getCart(session);
        if (cart.getCartItems().isEmpty()) return;
        var order = new Order();
        order.setOrderDate(new Date(new Date().getTime()));
        order.setPrice(getSumPrice(session));
        orderRepository.save(order);
        cart.getCartItems().forEach(item -> {
            var orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setProduct(productRepository.findById(item.getProductId())
                    .orElseThrow());
            orderDetailRepository.save(orderDetail);
        });
        removeCart(session);
    }
}