package api.test.meetingplanner.integration.controllers;

import api.test.meetingplanner.controllers.SalleController;
import api.test.meetingplanner.entities.Reunion;
import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.repositories.SalleRepository;
import api.test.meetingplanner.services.SalleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(SalleController.class)
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
public class SalleControllerTest {
    @Autowired
    private SalleController salleController;

    @MockBean
    private SalleService salleService;

    @Mock
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

    /**
     * Method under test: {@link SalleController#findEquipements(Long)}
     */
    @Test
    void testFindEquipements() throws Exception {
        // given
        salle.setEquipements(new ArrayList<>());
        salle.setId(1L);
        Long id = 1L;

        // when
        when(salleService.findById(Mockito.<Long>any())).thenReturn(salle);
        when(salleService.findEquipementsBySalle(Mockito.<Salle>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/salles/equipement/{salleId}",
                id);

        // then
        MockMvcBuilders.standaloneSetup(salleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link SalleController#findByCapacite(int)}
     */
    @Test
    void testFindByCapacite() throws Exception {
        // given
        int capacite = 10;

        // when
        when(salleService.findSallesByCapacite(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/salles/capacite/{capacite}", capacite);

        // then
        MockMvcBuilders.standaloneSetup(salleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    /**
     * Method under test: {@link SalleController#findByReunion(String)}
     */
    @Test
    void testFindByReunion() throws Exception {
        // given
        Reunion typeReunion = Reunion.VC;

        // when
        when(salleService.findSallesByReunion(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/salles/reunion/{typeReunion}",
                typeReunion);

        // then
        MockMvcBuilders.standaloneSetup(salleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test:
     * {@link SalleController#findByCreneau(LocalDateTime, LocalDateTime)}
     */
    @Test
    void testFindByCreneau() throws Exception {
        // given
        LocalDateTime startReunion = LocalDateTime.parse("2024-06-25T12:00:00");
        LocalDateTime endReunion = LocalDateTime.parse("2024-06-25T13:00:00");
        // when
        when(salleService.findSallesByCreneau(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/v1/salles/creneau/{tempsDebut}/{tempsFin}", startReunion, endReunion);

        // then
        MockMvcBuilders.standaloneSetup(salleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
