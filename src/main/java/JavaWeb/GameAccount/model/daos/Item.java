package JavaWeb.GameAccount.model.daos;


import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long productId;
    private String productName;
    private Double price;
    private int quantity;
}
