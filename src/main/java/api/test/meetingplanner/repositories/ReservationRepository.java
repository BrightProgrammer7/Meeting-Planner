package api.test.meetingplanner.repositories;

import api.test.meetingplanner.entities.Reservation;
import api.test.meetingplanner.entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByNom(String nom);

    @Query("SELECT r.salle FROM Reservation r WHERE r.id = :reservationId")
    Salle findSalleByReservationId(@Param("reservationId") Long reservationId);

//    Salle findSalleById(Long reservationId);

}
