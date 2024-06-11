package JavaWeb.GameAccount.services;

import JavaWeb.GameAccount.model.CartItem;
import JavaWeb.GameAccount.model.Order;
import JavaWeb.GameAccount.model.OrderDetail;
import JavaWeb.GameAccount.model.Product;
import JavaWeb.GameAccount.repositories.IOrderDetailRepository;
import JavaWeb.GameAccount.repositories.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private CartItemService cartItemService; // Assuming you have a CartService
    @Autowired
    private ProductService productService;
    @Autowired
    private Product product;

    @Transactional
    public Order createOrder(String customerName, String Email, long phone, String address, String note, List<CartItem> cartItems)
    {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setAddress(address);
        order.setEmail(Email);
        order.setPhone(phone);
        order.setNote(note);// Gọi phương thức setCustomerName
// Lưu đơn hàng vào cơ sở dữ liệu
        order = orderRepository.save(order);
// Lưu các chi tiết đơn hàng vào cơ sở dữ liệu
        for (CartItem item : cartItems) {
//            if(item.getQuantity() > product.getNums())
//            {
//
//            }
//            else
//            {
            product = item.getProduct();
            product.setNums(product.getNums() - item.getQuantity());
            productService.updateProduct(product);
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
//            }
        }
// Xóa giỏ hàng sau khi đặt hàng (tùy chọn)
        cartItemService.clearCart();
        return order;
    }
}
