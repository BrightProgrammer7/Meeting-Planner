package api.test.meetingplanner.controllers;


import api.test.meetingplanner.entities.Reservation;
import api.test.meetingplanner.entities.Reunion;
import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservation;

    @GetMapping
    public List<Reservation> findAllReservation() {
        return reservation.findAll();
    }

    //    @GetMapping("/reservation")
    //    public List<Reservation> findByReservation(@RequestBody Reservation reservation) {
    //        return reservation.findByReservation(reservation);
    //    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation r) {
        r.setId(0L);
        return reservation.create(r);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Reservation r = reservation.findById(id);
        if (r == null) {
            return new ResponseEntity<>("Reservation avec ID = " + id + " n'existe pas", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(r);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReservation(@PathVariable Long id, @RequestBody Reservation newReservation) {
        Reservation oldReservation = reservation.findById(id);
        if (oldReservation == null) {
            return new ResponseEntity<>("Reservation avec ID = " + id + " n'existe pas", HttpStatus.BAD_REQUEST);
        } else {
            newReservation.setId(id);
            return ResponseEntity.ok(reservation.update(newReservation));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        Reservation r = reservation.findById(id);
        if (r == null) {
            return new ResponseEntity<>("Reservation avec ID = " + id + " n'existe pas", HttpStatus.BAD_REQUEST);
        } else {
            reservation.delete(r);
            return ResponseEntity.ok("Reservation supprimée");
        }
    }


    // Endpoint pour trouver une salle par disponibilite en fonction de divers facteurs
    @GetMapping("/meilleure")
    public ResponseEntity<Object> findMeilleureReservation(
            @RequestParam String nom,
            @RequestParam String typeReunion,
            @RequestParam int capacite,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime tempsDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime tempsFin) {

        Salle salle = reservation.findMeilleureSalle(typeReunion, capacite, tempsDebut, tempsFin);

        if (salle != null) {
            // And here make the reservation
            Reservation r = Reservation.builder()
                    .nom(nom)
                    .salle(salle)
                    .tempsDebut(tempsDebut)
                    .tempsFin(tempsFin)
                    .nombrePersonnes(capacite)
                    .reunion(Reunion.valueOf(typeReunion))
                    .build();

            reservation.create(r);
//            throw new SuccesReservationException("Réservation réussie:" + salle);
            return ResponseEntity.ok("Réservation réussie:");
//            return new ResponseEntity<>("Réservation réussie:" + salle, HttpStatus.ACCEPTED);

        } else {
            // Renvoie la réponse non trouvée si aucune salle appropriée n'est trouvée
//            return ResponseEntity.notFound().build();
            return new ResponseEntity<>("Aucune Salle appropriée trouvée pour la réservation demandée", HttpStatus.BAD_REQUEST);
        }

        // Renvoie la réponse non trouvée si aucune salle appropriée n'est trouvée
//        return ResponseEntity.notFound().build();
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("Aucune Salle appropriée trouvée pour la réservation demandée");
    }


}
