package api.test.meetingplanner.unit.entities;

import api.test.meetingplanner.entities.Reservation;
import api.test.meetingplanner.entities.Reunion;
import api.test.meetingplanner.entities.Salle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
    }

    @Test
    void testNoArgsConstructor() {
        Reservation newReservation = new Reservation();
        assertNull(newReservation.getId());
        assertNull(newReservation.getNom());
        assertNull(newReservation.getTempsDebut());
        assertNull(newReservation.getTempsFin());
        assertNull(newReservation.getReunion());
        assertEquals(0, newReservation.getNombrePersonnes());
        assertNull(newReservation.getSalle());
    }

    @Test
    void testAllArgsConstructor() {
        String nom = "Réunion 1";
        int nombrePersonnes = 10;
        LocalDateTime debut = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LocalDateTime fin = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0));

        Reunion typeReunion = Reunion.VC;
        Salle salle = new Salle();

        Reservation newReservation = new Reservation(1L, nom, nombrePersonnes, debut, fin, typeReunion, salle);

        assertEquals(1L, newReservation.getId());
        assertEquals(nom, newReservation.getNom());
        assertEquals(debut, newReservation.getTempsDebut());
        assertEquals(fin, newReservation.getTempsFin());
        assertEquals(typeReunion, newReservation.getReunion());
        assertEquals(nombrePersonnes, newReservation.getNombrePersonnes());
        assertEquals(salle, newReservation.getSalle());
    }

    @Test
    void testGettersAndSetters() {
        String nom = "Réunion 2";
        LocalDateTime debut = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LocalDateTime fin = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0));
        Reunion typeReunion = Reunion.VC;
        int nombrePersonnes = 10;
        Salle salle = new Salle();

        reservation.setId(1L);
        reservation.setNom(nom);
        reservation.setTempsDebut(debut);
        reservation.setTempsFin(fin);
        reservation.setReunion(typeReunion);
        reservation.setNombrePersonnes(nombrePersonnes);
        reservation.setSalle(salle);

        assertEquals(1L, reservation.getId());
        assertEquals(nom, reservation.getNom());
        assertEquals(debut, reservation.getTempsDebut());
        assertEquals(fin, reservation.getTempsFin());
        assertEquals(typeReunion, reservation.getReunion());
        assertEquals(nombrePersonnes, reservation.getNombrePersonnes());
        assertEquals(salle, reservation.getSalle());
    }

    @Test
    void testToString() {
        String nom = "Réunion 3";
        LocalDateTime debut = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LocalDateTime fin = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0));
        Reunion typeReunion = Reunion.VC;
        int nombrePersonnes = 10;
        Salle salle = new Salle();

        reservation = new Reservation(1L, nom, nombrePersonnes, debut, fin, typeReunion, salle);
        String expectedToString = "Reservation(id=1, nom=Réunion 3, nombrePersonnes=10, tempsDebut=" + debut + ", tempsFin=" + fin + ", reunion=" + typeReunion + ", salle=" + salle + ")";

        assertEquals(expectedToString, reservation.toString());
    }
}