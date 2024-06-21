package api.test.meetingplanner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    //    @JoinColumn(name = "salle")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Salle salle;

}
