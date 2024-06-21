package api.test.meetingplanner.unit.entities;

import api.test.meetingplanner.entities.Equipement;
import api.test.meetingplanner.entities.Salle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SalleTest {
    private Salle salle;

    @BeforeEach
    void setUp() {
        salle = new Salle();
    }

    @Test
    void testNoArgsConstructor() {
        Salle newSalle = new Salle();
        assertNull(newSalle.getId());
        assertNull(newSalle.getNom());
        assertEquals(0, newSalle.getCapacite());
        assertNull(newSalle.getEquipements());
    }

    @Test
    void testAllArgsConstructor() {
        String nom = "E2001";
        int capaciteMaximale = 100;
        List<Equipement> equipements = Arrays.asList(
                new Equipement(1L, "Ecran", null),
                new Equipement(1L, "PIEUVRE", null)
        );

        Salle newSalle = new Salle(null, nom, capaciteMaximale, null, equipements);

        assertNull(newSalle.getId());
        assertEquals(nom, newSalle.getNom());
        assertEquals(capaciteMaximale, newSalle.getCapacite());
        assertEquals(equipements, newSalle.getEquipements());
    }

    @Test
    void testGettersAndSetters() {
        String nom = "E1001";
        int capaciteMaximale = 80;
        List<Equipement> equipements = Arrays.asList(
                new Equipement(1L, "WEBCAM", null),
                new Equipement(1L, "TABLEAU", null)
        );

        salle.setId(1L);
        salle.setNom(nom);
        salle.setCapacite(capaciteMaximale);
        salle.setEquipements(equipements);

        assertEquals(1L, salle.getId());
        assertEquals(nom, salle.getNom());
        assertEquals(capaciteMaximale, salle.getCapacite());
        assertEquals(equipements, salle.getEquipements());
    }

    @Test
    void testToString() {
        String nom = "E1009";
        int capacite = 120;


        salle = new Salle(0L, nom, capacite, null, null);
        salle.setId(1L);
        String expectedToString = "Salle(id=1, nom=E1009, capacite=120, dernierTempsReserve=null, equipements=null)";

        assertEquals(expectedToString, salle.toString());
    }
}
