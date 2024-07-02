package JavaWeb.GameAccount.model.daos;


import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private int productId;
    private String productName;
    private Double price;
    private int quantity;
}