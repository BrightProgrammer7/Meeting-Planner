package api.test.meetingplanner.unit.services;

import api.test.meetingplanner.entities.Equipement;
import api.test.meetingplanner.entities.Reservation;
import api.test.meetingplanner.entities.Reunion;
import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.exception.InvalidTimeException;
import api.test.meetingplanner.exception.UnavailableSalleException;
import api.test.meetingplanner.repositories.ReservationRepository;
import api.test.meetingplanner.services.ReservationService;
import api.test.meetingplanner.services.SalleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationTest;
    @Mock
    private SalleService salleService;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private Salle salle;

    @BeforeEach
    void setUp() {
        salle = Salle.builder()
                .id(1L)
                .nom("E1001")
                .capacite(50)
                .dernierTempsReserve(LocalDateTime.parse("2024-06-17T11:00:00"))
                .equipements(Arrays.asList(
                        new Equipement(1L, "Ecran", null),
                        new Equipement(2L, "Webcam", null),
                        new Equipement(3L, "Pieuvre", null)
                ))
                .build();
        salleService.create(salle);

        reservation = Reservation.builder()
                .id(1L)
                .nom("Réunion 1")
                .nombrePersonnes(8)
                .reunion(Reunion.valueOf("VC"))
                .tempsDebut(LocalDateTime.parse("2024-06-24T09:00:00"))
                .tempsFin(LocalDateTime.parse("2024-06-24T10:00:00"))
                .salle(salle)
                .build();
        reservationTest.save(reservation);
//        when(reservationTest.save(reservation)).thenReturn(reservation);
    }

    @Test
    void itShouldThrowExceptionForInvalidBusinessHours() {
        // Given
        LocalDateTime invalidDebut = LocalDateTime.parse("2024-06-24T07:00:00");
        LocalDateTime invalidFin = LocalDateTime.parse("2024-06-24T08:00:00");

        // Then
        assertThrows(InvalidTimeException.class, () -> {
            reservationService.findMeilleureSalle("VC", 10, invalidDebut, invalidFin);
        });
    }

    @Test
    void itShouldThrowExceptionForWeekendReservation() {
        // Given
        LocalDateTime weekendDebut = LocalDateTime.parse("2024-06-22T12:00:00");
        LocalDateTime weekendFin = LocalDateTime.parse("2024-06-22T13:00:00");

        // Then
        assertThrows(InvalidTimeException.class, () -> {
            reservationService.findMeilleureSalle("VC", 10, weekendDebut, weekendFin);
        });
    }

    /**
     * Method under test:
     * {@link ReservationService#findMeilleureSalle(String, int, LocalDateTime, LocalDateTime)}
     */
//    @Test
//    void itShouldFindBestSalleForMeeting() {
//        // Given
//        LocalDateTime tempsDebut = LocalDateTime.parse("2024-06-24T12:00:00");
//        LocalDateTime tempsFin = LocalDateTime.parse("2024-06-24T13:00:00");
//        when(salleService.findSallesByCapacite(anyInt())).thenReturn(Collections.singletonList(salle));
//        when(salleService.findSallesByReunion(anyString())).thenReturn(Collections.singletonList(salle));
//        when(salleService.findSallesByCreneau(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(Collections.singletonList(salle));
//
//        // When
//        Salle bestSalle = reservationService.findMeilleureSalle("VC", 10, tempsDebut, tempsFin);
//
//        // Then
//        assertThat(bestSalle).isNotNull();
//        assertThat(bestSalle.getNom()).isEqualTo("E1001");
//    }

    @Test
    void itShouldThrowExceptionWhenNoSalleAvailable() {
        // Given
        LocalDateTime tempsDebut = LocalDateTime.parse("2024-06-24T09:00:00");
        LocalDateTime tempsFin = LocalDateTime.parse("2024-06-24T10:00:00");
        when(salleService.findSallesByCapacite(anyInt())).thenReturn(List.of());
        when(salleService.findSallesByReunion(anyString())).thenReturn(List.of());
        when(salleService.findSallesByCreneau(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of());

        // Then
        assertThrows(UnavailableSalleException.class, () -> {
            reservationService.findMeilleureSalle("VC", 10, tempsDebut, tempsFin);
        });
    }


}
