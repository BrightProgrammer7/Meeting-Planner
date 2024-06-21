package api.test.meetingplanner.entities;

import jakarta.persistence.*;
import lombok.*;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@Data
//@Builder
////@Table(name="Reunion")
//public class Reunion {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String nom;
//
//    //    @JoinColumn(name = "reservation")
//    //    @JsonIgnore
//    @OneToOne(fetch = FetchType.LAZY)
//    private Reservation reservation;
//
//}

public enum Reunion {
    VC, SPEC, RS, RC
}
