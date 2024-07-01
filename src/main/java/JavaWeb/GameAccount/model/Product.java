package JavaWeb.GameAccount.model;

import JavaWeb.GameAccount.validators.annotations.ValidCategoryId;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ValidCategoryId
    @ToString.Exclude
    private Category category;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "nums", nullable = false)
    private int nums;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "detail")
    private String detail;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ProductImages> images = new ArrayList<>();
    @Column(name = "LINK")
    private String link;

    @Column(name = "`order`", nullable = false)
    private int order;

    @Column(name = "hide", nullable = false)
    private boolean hide;
}
