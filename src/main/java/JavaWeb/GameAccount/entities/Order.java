package JavaWeb.GameAccount.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String address;
    private long phone;
    private String Email;
    private String note;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setAddress(String address) {this.address = address;}
    public void setEmail(String Email) {this.Email = Email;}
    public void setNote(String note) {this.note = note;}
    public void setPhone(long phone) {this.phone = phone;}
}
