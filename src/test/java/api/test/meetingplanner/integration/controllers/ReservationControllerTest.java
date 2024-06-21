package api.test.meetingplanner.integration.controllers;

import api.test.meetingplanner.controllers.ReservationController;
import api.test.meetingplanner.entities.Equipement;
import api.test.meetingplanner.entities.Reservation;
import api.test.meetingplanner.entities.Reunion;
import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.repositories.ReservationRepository;
import api.test.meetingplanner.repositories.SalleRepository;
import api.test.meetingplanner.services.ReservationService;
import api.test.meetingplanner.services.SalleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(ReservationController.class)
@ExtendWith(SpringExtension.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationController reservationController;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private SalleService salleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private SalleRepository salleTest;
    @Mock
    private ReservationRepository reservationTest;

    private Salle salle;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        salleTest.deleteAll();
        reservationTest.deleteAll();
        salle = Salle.builder()
                .id(1L)
                .nom("E1001")
                .capacite(50)
                .dernierTempsReserve(LocalDateTime.parse("2024-06-20T11:00:00"))
                .equipements(Arrays.asList(
                        new Equipement(1L, "Ecran", null),
                        new Equipement(2L, "Webcam", null),
                        new Equipement(3L, "Pieuvre", null)
                ))
                .build();
        salleTest.save(salle);

        reservation = Reservation.builder()
                .id(1L)
                .nom("Réunion 1")
                .nombrePersonnes(20)
                .reunion(Reunion.VC)
                .tempsDebut(LocalDateTime.parse("2024-06-24T09:00:00"))
                .tempsFin(LocalDateTime.parse("2024-06-24T10:00:00"))
                .salle(salle)
                .build();
        reservationTest.save(reservation);

    }

    @Test
    void testFindAllReservations() throws Exception {
        mockMvc.perform(get("/api/v1/reservation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Reunion 1"));
    }

    @Test
    void testFindReservationById() throws Exception {
        mockMvc.perform(get("/api/v1/reservation/{id}", reservation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Reunion 1"));
    }

    @Test
    void testUpdateReservation() throws Exception {
        Reservation updatedReservation = Reservation.builder()
                .nom("Reunion 2")
                .salle(salle)
                .tempsDebut(LocalDateTime.now().plusDays(1))
                .tempsFin(LocalDateTime.now().plusDays(1).plusHours(1))
                .nombrePersonnes(30)
                .reunion(Reunion.SPEC)
                .build();

        mockMvc.perform(put("/api/v1/reservation/{id}", reservation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Reunion 2"));
    }

    @Test
    void testDeleteReservation() throws Exception {
        mockMvc.perform(delete("/api/v1/reservation/{id}", reservation.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation supprimée"));

        mockMvc.perform(get("/api/v1/reservation/{id}", reservation.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindMeilleureReservation() throws Exception {
        mockMvc.perform(get("/api/v1/reservation/meilleure")
                        .param("nom", "Reunion 4")
                        .param("typeReunion", "VC")
                        .param("capacite", "20")
                        .param("tempsDebut", "2024-06-25T15:00:00")
                        .param("tempsFin", "2024-06-25T16:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation réussie: E1001"));
    }


    @Test
    void itShouldCheckIfTheEndpointIsAccessible() throws Exception {
        Salle mockSalle = new Salle();
        mockSalle.setNom("Salle 1");
        mockSalle.setCapacite(10);

        Mockito.when(reservationService.findMeilleureSalle(Mockito.anyString(), Mockito.anyInt(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(mockSalle);


        mockMvc.perform(get("/api/v1/reservation/meilleure")
                        .param("nom", "Reunion 7")
                        .param("typeReunion", "VC")
                        .param("capacite", "10")
                        .param("tempsDebut", "2024-06-25T12:00:00")
                        .param("tempsFin", "2024-06-25T13:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
