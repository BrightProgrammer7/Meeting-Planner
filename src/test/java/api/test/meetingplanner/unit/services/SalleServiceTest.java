package api.test.meetingplanner.unit.services;

import api.test.meetingplanner.entities.Equipement;
import api.test.meetingplanner.entities.Reservation;
import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.repositories.SalleRepository;
import api.test.meetingplanner.services.SalleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SalleServiceTest {
    @Mock
    private SalleRepository salleTest;

    @InjectMocks
    private SalleService salleService;

    private Salle salle;
    private Reservation reservation;

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
     * Method under test: {@link SalleService#findEquipementsBySalle(Salle)}
     */
    @Test
    void itShouldFindEquipementsBySalle() {
        // Given
        Equipement equipement = Equipement.builder().nom("Ecran").build();
        equipement.setSalle(salle);

        List<Equipement> salleEquipements = Optional.ofNullable(salle.getEquipements())
                .orElse(new ArrayList<>());

        salleEquipements.add(equipement);
        salle.setEquipements(salleEquipements);
        salleService.create(salle);

        // When
        List<String> listEquipments = salleService.findEquipementsBySalle(salle);

        // Then
        assertThat(listEquipments).isNotNull();
        assertThat(listEquipments.size()).isGreaterThan(0);
        assertEquals(1, listEquipments.size());
        assertEquals("Ecran", listEquipments.get(0));
    }

    /**
     * Method under test: {@link SalleService#findSallesByCapacite(int)}
     */
    @Test
    void itShouldFindSallesByCapacite() {
        // Given
        ArrayList<Salle> salleList = new ArrayList<>();
        salleList.add(salle);
        when(salleTest.findAll()).thenReturn(salleList);

        // When
        List<Salle> salles = salleService.findSallesByCapacite(30);

        // Then
        verify(salleTest).findAll();
        assertEquals(1, salles.size());
        assertThat(salles.get(0).getNom()).isEqualTo("E1001");
        assertSame(salle, salles.get(0));
    }

    /**
     * Method under test: {@link SalleService#findSallesByCreneau(LocalDateTime, LocalDateTime)}
     */
    @Test
    void itShouldFindSallesByCreneau() {
        // Given
        when(salleTest.findAll()).thenReturn(Collections.singletonList(salle));

        LocalDateTime tempsDebut = LocalDateTime.parse("2024-06-16T12:00:00");
        LocalDateTime tempsFin = LocalDateTime.parse("2024-06-16T13:00:00");

        // When
        List<Salle> salles = salleService.findSallesByCreneau(tempsDebut, tempsFin);

        // Then
        assertThat(salles.size()).isEqualTo(1);
        boolean containsE1001 = salles.stream().anyMatch(salle -> "E1001".equals(salle.getNom()));
        assertThat(containsE1001).isTrue();

    }

    /**
     * Method under test: {@link SalleService#findSallesByReunion(String)} 
     */
    @Test
    void itShouldFindSallesByReunionType() {
        Equipement equipement1 = new Equipement();
        equipement1.setNom("Ecran");

        Equipement equipement2 = new Equipement();
        equipement2.setNom("Webcam");

        Equipement equipement3 = new Equipement();
        equipement3.setNom("Pieuvre");

        Equipement equipement4 = new Equipement();
        equipement4.setNom("Tableau");

        salle.setEquipements(Arrays.asList(equipement1, equipement2, equipement3));

        when(salleTest.findAll()).thenReturn(Arrays.asList(salle));

        List<Salle> sallesVC = salleService.findSallesByReunion("VC");
//        List<Salle> sallesSPEC = salleService.findSallesByReunion("SPEC");
//        List<Salle> sallesRC = salleService.findSallesByReunion("RC");
        List<Salle> sallesRS = salleService.findSallesByReunion("RS");

//        assertThat(sallesVC).hasSize(1).extracting(Salle::getNom).contains("Salle 1");
        assertThat(sallesVC.size()).isEqualTo(1);
//        assertThat(sallesSPEC.size()).isEqualTo(0);
//        assertThat(sallesRC.size()).isEqualTo(0);
        assertThat(sallesRS.size()).isEqualTo(1);
        assertThat(sallesRS.stream().anyMatch(salle -> "E1001".equals(salle.getNom()))).isTrue();

    }
}