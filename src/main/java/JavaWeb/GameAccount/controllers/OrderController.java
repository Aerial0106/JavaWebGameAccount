package JavaWeb.GameAccount.controllers;

import JavaWeb.GameAccount.model.CartItem;
import JavaWeb.GameAccount.model.Order;
import JavaWeb.GameAccount.model.User;
import JavaWeb.GameAccount.model.daos.Cart;
import JavaWeb.GameAccount.repositories.IUserRepository;
import JavaWeb.GameAccount.services.CartItemService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final CartItemService cartService;
    private final HttpSession session;
    private final IUserRepository userRepository;

    @Autowired
    public OrderController(CartItemService cartService, HttpSession session, IUserRepository userRepository) {
        this.cartService = cartService;
        this.session = session;
        this.userRepository = userRepository;
    }

    @GetMapping("/checkout")
public String checkout(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
    }
    model.addAttribute("order", new Order());
    return "/cart/checkout";
}
@PostMapping("/checkout")
public String submitOrder(@ModelAttribute("order") Order order, @RequestParam String note, @RequestParam String address) {
    Cart cart = cartService.getCart(session);
    if (cart.getCartItems().isEmpty()) {
        return "redirect:/cart"; // Redirect if cart is empty
    }
    order.setPrice(cartService.getSumPrice(session));
    cartService.saveCart(session, note, address); // Save order and clear cart
    return "redirect:/order/confirmation";
}

@GetMapping("/confirmation")
public String orderConfirmation(Model model) {
    model.addAttribute("message", "Your order has been successfully placed.");
    return "cart/order-confirmation";
    }
}
