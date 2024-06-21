package api.test.meetingplanner.unit.repositories;

import api.test.meetingplanner.entities.Reservation;
import api.test.meetingplanner.entities.Reunion;
import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.repositories.ReservationRepository;
import api.test.meetingplanner.repositories.SalleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationTest;
    @Autowired
    private SalleRepository salleTest;
    private Salle salle;

    @BeforeEach
    void setUp() {
        salle = Salle.builder()
                .nom("E1001")
                .capacite(50)
                .dernierTempsReserve(LocalDateTime.parse("2024-06-16T11:00:00"))
                .build();
        salleTest.save(salle);
    }

    @AfterEach
    void tearDown() {
        reservationTest.deleteAll();
        salleTest.deleteAll();
    }

    @Test
    void itShouldFindReservationByNom() {
        // Given
        Reservation reservation = Reservation.builder()
                .nom("Reunion 1")
                .reunion(Reunion.VC)
                .nombrePersonnes(8)
                .tempsDebut(LocalDateTime.parse("2024-06-16T10:00:00"))
                .tempsFin(LocalDateTime.parse("2024-06-16T11:00:00"))
                .salle(salle)
                .build();
        reservationTest.save(reservation);

        // When
        Reservation reservationFound = reservationTest.findByNom("Reunion 1");

        // Then
        assertThat(reservationFound).isNotNull();
        assertThat(reservationFound.getNom()).isEqualTo("Reunion 1");
        assertThat(reservationFound.getSalle().getNom()).isEqualTo("E1001");
    }

    @Test
    void itShouldFindAllReservations() {
        // Arrange
        Reservation reservation1 = Reservation.builder()
                .nom("Reunion 1")
                .reunion(Reunion.valueOf("VC"))
                .nombrePersonnes(10)
                .tempsDebut(LocalDateTime.parse("2024-06-16T09:00:00"))
                .tempsFin(LocalDateTime.parse("2024-06-16T10:00:00"))
                .salle(salle)
                .build();

        Reservation reservation2 = Reservation.builder()
                .nom("Reunion 2")
                .reunion(Reunion.valueOf("RS"))
                .nombrePersonnes(30)
                .tempsDebut(LocalDateTime.parse("2024-06-16T11:00:00"))
                .tempsFin(LocalDateTime.parse("2024-06-16T12:00:00"))
                .salle(salle)
                .build();
        reservationTest.save(reservation1);
        reservationTest.save(reservation2);

        // Act
        List<Reservation> listOfReservations = reservationTest.findAll();

        // Assert
        assertThat(listOfReservations).isNotNull();
        assertThat(listOfReservations.size()).isEqualTo(2);
        assertThat(listOfReservations.get(0).getNom()).isEqualTo("Reunion 1");
        assertThat(listOfReservations.get(1).getNom()).isEqualTo("Reunion 2");
    }

    @Test
    void itShouldSaveReservation() {
        // Arrange
        Reservation reservation = Reservation.builder()
                .nom("Reunion 2")
                .reunion(Reunion.RS)
                .nombrePersonnes(30)
                .tempsDebut(LocalDateTime.parse("2024-06-16T13:00:00"))
                .tempsFin(LocalDateTime.parse("2024-06-16T14:00:00"))
                .salle(salle)
                .build();

        // Act
        Reservation savedReservation = reservationTest.save(reservation);

        // Assert
        assertThat(savedReservation).isNotNull();
        assertThat(savedReservation.getNom()).isEqualTo("Reunion 2");
    }

    @Test
    void itShouldDeleteReservation() {
        // Given
        Reservation reservation = Reservation.builder()
                .nom("Reunion 3")
                .reunion(Reunion.SPEC)
                .nombrePersonnes(20)
                .tempsDebut(LocalDateTime.parse("2024-06-16T15:00:00"))
                .tempsFin(LocalDateTime.parse("2024-06-16T16:00:00"))
                .salle(salle)
                .build();
        reservationTest.save(reservation);
        Long id = reservation.getId();

        // When
        reservationTest.deleteById(id);

        // Then
        Optional<Reservation> deletedReservation = reservationTest.findById(id);
        assertThat(deletedReservation).isEmpty();
        assertThat(reservationTest.existsById(id)).isFalse();
    }

    @Test
    void itShouldUpdateReservation() {
        // Arrange
        Reservation reservation = Reservation.builder()
                .nom("Reunion 6")
                .reunion(Reunion.VC)
                .nombrePersonnes(12)
                .tempsDebut(LocalDateTime.parse("2024-06-16T14:00:00"))
                .tempsFin(LocalDateTime.parse("2024-06-16T15:00:00"))
                .salle(salle)
                .build();
        reservationTest.save(reservation);

        // Act
        reservation.setNom("Reunion 7");
        Reservation updatedReservation = reservationTest.save(reservation);

        // Assert
        assertThat(updatedReservation).isNotNull();
        assertThat(updatedReservation.getNom()).isEqualTo("Reunion 7");
    }


}