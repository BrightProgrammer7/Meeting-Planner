package api.test.meetingplanner.controllers;

import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.services.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/salles")
public class SalleController {
    @Autowired
    private SalleService salle;

    @GetMapping
    public List<Salle> findAllSalle() {
        return salle.findAll();
    }

    //    @GetMapping("/reservation")
    //    public List<Salle> findByReservation(@RequestBody Reservation reservation) {
    //        return salle.findByReservation(reservation);
    //    }

    @PostMapping
    public Salle createSalle(@RequestBody Salle s) {
        s.setId(0L);
        return salle.create(s);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Salle s = salle.findById(id);
        if (s == null) {
            return new ResponseEntity<>("Salle avec ID = " + id + " n'existe pas", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(s);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSalle(@PathVariable Long id, @RequestBody Salle newSalle) {
        Salle oldSalle = salle.findById(id);
        if (oldSalle == null) {
            return new ResponseEntity<>("Salle avec ID = " + id + " n'existe pas", HttpStatus.BAD_REQUEST);
        } else {
            newSalle.setId(id);
            return ResponseEntity.ok(salle.update(newSalle));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSalle(@PathVariable Long id) {
        Salle s = salle.findById(id);
        if (s == null) {
            return new ResponseEntity<>("Salle avec ID = " + id + " n'existe pas", HttpStatus.BAD_REQUEST);
        } else {
            salle.delete(s);
            return ResponseEntity.ok("Salle supprimée");
        }
    }

    @GetMapping("/equipement/{salleId}")
    public List<String> findEquipements(@PathVariable Long salleId) {
        Salle s = salle.findById(salleId);
        return salle.findEquipementsBySalle(s);
    }

    @GetMapping("/capacite/{capacite}")
    public List<Salle> findByCapacite(@PathVariable int capacite) {
        return salle.findSallesByCapacite(capacite);
    }

    @GetMapping("/reunion/{typeReunion}")
    public List<Salle> findByReunion(@PathVariable String typeReunion) {
        return salle.findSallesByReunion(typeReunion);
    }

    @GetMapping("/creneau/{tempsDebut}/{tempsFin}")
    public List<Salle> findByCreneau(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime tempsDebut,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime tempsFin) {
        return salle.findSallesByCreneau(tempsDebut, tempsFin);
    }

}
