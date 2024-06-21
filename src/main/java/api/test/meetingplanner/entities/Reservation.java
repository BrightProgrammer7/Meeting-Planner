package api.test.meetingplanner.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int nombrePersonnes;
    private LocalDateTime tempsDebut;
    private LocalDateTime tempsFin;

    @Enumerated(EnumType.STRING)
    private Reunion reunion;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Salle salle;

}
