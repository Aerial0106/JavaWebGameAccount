package JavaWeb.GameAccount.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Table(name = "Catelogy")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAT")
    private int id;

    @Column(name = "NAME_CAT", nullable = false)
    private String name;

    @Column(name = "`ORDER`", nullable = false)
    private int order;


    @OneToMany(mappedBy = "category")
    private Set<Product> products;

}
