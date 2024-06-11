package JavaWeb.GameAccount.model;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@Table(name = "CartItem")
@RequiredArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARTITEM")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_PRO") // Thêm JoinColumn để chỉ định cách ánh xạ
    private Product product;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "ID_USER") // Add JoinColumn to specify the mapping
    private User user;

    // Constructors
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
