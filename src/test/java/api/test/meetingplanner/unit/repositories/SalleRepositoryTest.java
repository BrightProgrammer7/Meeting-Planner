package api.test.meetingplanner.unit.repositories;

import api.test.meetingplanner.entities.Reservation;
import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.repositories.SalleRepository;
import api.test.meetingplanner.services.SalleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DataJpaTest
class SalleRepositoryTest {

    @Autowired
    private SalleRepository salleTest;

    @Mock
    private SalleService salleService;

    @AfterEach
    void tearDown() {
        salleTest.deleteAll();
    }

    @Test
    void itShouldSaveSalle() {
        // Given
        Salle salle = Salle.builder()
                .nom("E1002")
                .capacite(10)
                .dernierTempsReserve(LocalDateTime.parse("2024-06-16T11:00:00"))
                .build();
        // When
        Salle savedSalle = salleTest.save(salle);

        // Then
        assertThat(savedSalle).isNotNull();
        assertThat(savedSalle.getNom()).isEqualTo("E1002");
        assertThat(savedSalle.getCapacite()).isEqualTo(10);
        assertThat(savedSalle.getDernierTempsReserve()).isEqualTo(LocalDateTime.parse("2024-06-16T11:00:00"));
    }

    @Test
    void itShouldFindSalleByNom() {
        // Given
        Salle salle = Salle.builder()
                .nom("E1001")
                .capacite(50)
                .dernierTempsReserve(LocalDateTime.parse("2024-06-16T11:00:00"))
                .build();
        salleTest.save(salle);

        // When
        Salle savedSalle = salleTest.findByNom("E1001");

        // Then
        assertThat(savedSalle).isNotNull();
        assertThat(savedSalle.getNom()).isEqualTo("E1001");
        assertThat(savedSalle.getCapacite()).isEqualTo(50);
        assertThat(savedSalle.getDernierTempsReserve()).isEqualTo(LocalDateTime.parse("2024-06-16T11:00:00"));
    }

    @Test
    void itShouldDeleteSalle() {
        // Given
        Salle salle = Salle.builder()
                .nom("E1001")
                .capacite(50)
                .dernierTempsReserve(LocalDateTime.parse("2024-06-16T11:00:00"))
                .build();
        salleTest.save(salle);
        Long id = salle.getId();

        // When
        salleTest.deleteById(id);

        // Then
        Optional<Salle> deletedSalle = salleTest.findById(id);
        assertThat(salleTest.existsById(id)).isFalse();
        assertThat(deletedSalle).isEmpty();
    }

    @Test
    void itShouldFindAllSalles() {
        // Arrange
        Salle salle1 = Salle.builder()
                .nom("E2001")
                .capacite(100)
                .dernierTempsReserve(LocalDateTime.parse("2024-06-16T11:00:00"))
                .build();

        Salle salle2 = Salle.builder()
                .nom("E2002")
                .capacite(100)
                .dernierTempsReserve(LocalDateTime.parse("2024-06-17T16:00:00"))
                .build();

        salleTest.save(salle1);
        salleTest.save(salle2);

        // Act
        List<Salle> listOfSalles = salleTest.findAll();

        // Assert
        assertThat(listOfSalles).isNotNull();
        assertThat(listOfSalles.size()).isEqualTo(2);
//        assertThat(listOfSalle).extracting(Salle::getNom).containsExactlyInAnyOrder("E2001", "E2002");
        boolean containsE2001 = listOfSalles.stream().anyMatch(salle -> "E2001".equals(salle.getNom()));
        boolean containsE2002 = listOfSalles.stream().anyMatch(salle -> "E2002".equals(salle.getNom()));

        assertThat(containsE2001).isTrue();
        assertThat(containsE2002).isTrue();
    }

    @Test
    void itShouldUpdateSalle() {
        // Arrange
        Salle salle = Salle.builder()
                .nom("E3001")
                .capacite(100)
                .dernierTempsReserve(LocalDateTime.parse("2024-06-16T11:00:00"))
                .build();
        Salle savedSalle = salleTest.save(salle);

        // Act
        savedSalle.setNom("E3002");
        salleTest.save(savedSalle);

        // Assert
        Salle updatedSalle = salleTest.findById(savedSalle.getId()).orElse(null);
        assertThat(updatedSalle).isNotNull();
        assert updatedSalle != null;
        assertThat(updatedSalle.getNom()).isEqualTo("E3002");
    }
}