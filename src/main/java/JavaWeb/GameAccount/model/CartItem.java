package JavaWeb.GameAccount.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
@Data
@Entity
@Table(name = "Cart")
@RequiredArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CART")
    private int id;


    @ManyToOne
    @JoinColumn(name = "ID_PRO") // Thêm JoinColumn để chỉ định cách ánh xạ
    private Product product;
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;
    // Constructors
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
