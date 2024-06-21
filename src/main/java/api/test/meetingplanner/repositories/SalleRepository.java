package api.test.meetingplanner.repositories;

import api.test.meetingplanner.entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
    Salle findByNom(String nom);

}
