package api.test.meetingplanner.services;

import api.test.meetingplanner.dao.IDao;
import api.test.meetingplanner.entities.Reservation;
import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.exception.InvalidTimeException;
import api.test.meetingplanner.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReservationService implements IDao<Reservation> {
    @Autowired
    private ReservationRepository reservation;

    @Autowired
    private SalleService salle;

    private Salle s;

    @Override
    public Reservation create(Reservation o) {
        return reservation.save(o);
    }

    @Override
    public boolean delete(Reservation o) {
        try {
            s = reservation.findSalleByReservationId(o.getId());
            s.setDernierTempsReserve(null);
            reservation.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public Reservation update(Reservation o) {
        return reservation.save(o);
    }

    @Override
    public Reservation findById(Long id) {
        return reservation.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> findAll() {
        return reservation.findAll();
    }

    public Reservation findByName(String nom) {
        return reservation.findByNom(nom);
    }

    private boolean isWithinBusinessHours(LocalDateTime tempsDebut, LocalDateTime tempsFin) {
        LocalTime debutTime = tempsDebut.toLocalTime();
        LocalTime finTime = tempsFin.toLocalTime();

        // Definir les heures de debut et de fin de disponibilite de la salle (8h à 20h)
        LocalTime tempsDebutDisponible = LocalTime.of(8, 0);
        LocalTime tempsFinDisponible = LocalTime.of(20, 0);

        // Verifier si le créneau fourni est de 1 heure.
        if (!tempsFin.equals(tempsDebut.plusHours(1))) {
            throw new InvalidTimeException("Une salle est réservée par créneau de 1 heure.");
        }
        // Verifier si le créneau fourni se situent dans la plage de disponibilite
        if (debutTime.isBefore(tempsDebutDisponible) || finTime.isAfter(tempsFinDisponible)) {
            throw new InvalidTimeException("Une salle est réservée de 8h-20h");
        }

        return (debutTime.isAfter(tempsDebutDisponible) || debutTime.equals(tempsDebutDisponible)) &&
                (finTime.isBefore(tempsFinDisponible) || finTime.equals(tempsFinDisponible)) &&
                tempsDebut.plusHours(1).isEqual(tempsFin);
    }

    private boolean isWeekday(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new InvalidTimeException("Une salle est réservée tous les jours sauf le week-end.\n");
        }
//        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
        return true;
    }

    // Trouvez une salle en tenant compte de tous facteurs: capacite, type de reunion, disponibilite par creneau
    public Salle findMeilleureSalle(String typeReunion, int capacite, LocalDateTime tempsDebut, LocalDateTime tempsFin) {

        // Assurer que le délai est valide (créneau de 1 heure) et pendant les heures ouvrables (8h-20h tous les jours sauf le week-end)
        if (!isWithinBusinessHours(tempsDebut, tempsFin) || !isWeekday(tempsDebut)) {
            return null;
        }

        List<String> capaciteSalles = salle.findSallesByCapacite(capacite)
                .stream()
                .map(Salle::getNom)
                .toList();

        List<String> reunionSalles = salle.findSallesByReunion(typeReunion)
                .stream()
                .map(Salle::getNom)
                .toList();

        List<String> creneauSalles = salle.findSallesByCreneau(tempsDebut, tempsFin)
                .stream()
                .map(Salle::getNom)
                .toList();

        // Filtrage des salles en fonction du type de reunion, capacité et  disponibilité
        Set<String> capaciteSet = new HashSet<>(capaciteSalles);
        Set<String> reunionSet = new HashSet<>(reunionSalles);
        Set<String> creneauSet = new HashSet<>(creneauSalles);

        reunionSet.retainAll(capaciteSet);
        reunionSet.retainAll(creneauSet);

//        HashSet<Salle> IntersectionSet = new HashSet<>();
//        for (String nomSalle : reunionSet) {
//            Salle s = salle.findByName(nomSalle);
//            IntersectionSet.add(s);
//        }
//        List<Salle> sallesDisponibles = new ArrayList<>(IntersectionSet);

        // Tri des salles par capacite par ordre croissant
        List<Salle> sallesDisponibles = reunionSet.stream()
                .map(salle::findByName).sorted(Comparator.comparingInt(Salle::getCapacite)).toList();


        //        sallesDisponibles.sort(Comparator.comparingInt(Salle::getCapacite));

        if (!sallesDisponibles.isEmpty()) {
            Salle salleDisponible = sallesDisponibles.get(0);

            // Maintenir la salle indisponible pendant une 1H supplementaire avant sa prochaine reservation pour nettoyage (desinfection des locaux)
//            Time newTime = new Time(tempsFin.getTime() + TimeUnit.HOURS.toMillis(1));
            LocalDateTime newTime = tempsFin.plusHours(1);
            salleDisponible.setDernierTempsReserve(newTime);
            salle.update(salleDisponible);
            return salleDisponible;

        }

        return null;
    }


}
