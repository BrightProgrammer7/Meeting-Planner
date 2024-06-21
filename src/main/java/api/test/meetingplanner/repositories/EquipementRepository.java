package api.test.meetingplanner.repositories;

import api.test.meetingplanner.entities.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Long> {
//    List<Equipement> findByNom(String nom);
}
