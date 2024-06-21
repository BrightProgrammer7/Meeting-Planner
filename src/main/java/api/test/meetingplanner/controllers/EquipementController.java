package api.test.meetingplanner.controllers;

import api.test.meetingplanner.entities.Equipement;
import api.test.meetingplanner.services.EquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipement")
public class EquipementController {
    @Autowired
    private EquipementService equipement;

    @GetMapping
    public List<Equipement> findAllEquipement() {
        return equipement.findAll();
    }

    //    @GetMapping("/equipement")
    //    public List<Equipement> findByEquipement(@RequestBody Equipement equipement) {
    //        return equipement.findByEquipement(equipement);
    //    }

    @PostMapping
    public Equipement createEquipement(@RequestBody Equipement e) {
        e.setId(0L);
        return equipement.create(e);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Equipement e = equipement.findById(id);
        if (e == null) {
            return new ResponseEntity<>("Equipement avec ID = " + id + " n'existe pas", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEquipement(@PathVariable Long id, @RequestBody Equipement newEquipement) {
        Equipement oldEquipement = equipement.findById(id);
        if (oldEquipement == null) {
            return new ResponseEntity<>("Equipement avec ID = " + id + " n'existe pas", HttpStatus.BAD_REQUEST);
        } else {
            newEquipement.setId(id);
            return ResponseEntity.ok(equipement.update(newEquipement));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEquipement(@PathVariable Long id) {
        Equipement e = equipement.findById(id);
        if (e == null) {
            return new ResponseEntity<>("Equipement avec ID = " + id + " n'existe pas", HttpStatus.BAD_REQUEST);
        } else {
            equipement.delete(e);
            return ResponseEntity.ok("Equipement supprimée");
        }
    }
}
