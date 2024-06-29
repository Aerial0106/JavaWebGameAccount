package JavaWeb.GameAccount.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Slide")
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SLIDE")
    private int id;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "DATEBEGIN", nullable = false)
    private java.sql.Date dateBegin;
    @Column(name = "META")
    private String meta;
    @Column(name = "`ORDER`", nullable = false)
    private int order;
    @Column(name = "LINK")
    private String link;
    @Column(name = "HIDE", nullable = false)
    private boolean hide;
}