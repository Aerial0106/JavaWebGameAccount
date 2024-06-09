package JavaWeb.GameAccount.entities;
import jakarta.persistence.*;
import lombok.*;




@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "game_account_image")
public class GameAccountImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "game_account_id", nullable = false)
    @ToString.Exclude
    private GameAccount gameAccount;
}
