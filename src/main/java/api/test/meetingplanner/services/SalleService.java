package api.test.meetingplanner.services;

import api.test.meetingplanner.dao.IDao;
import api.test.meetingplanner.entities.Equipement;
import api.test.meetingplanner.entities.Salle;
import api.test.meetingplanner.exception.UnavailableSalleException;
import api.test.meetingplanner.repositories.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalleService implements IDao<Salle> {

    // Declare constants
    private static final int MINIMUM_RS_PLACES = 3;
    // Réduire la capacité de 30% en raison des restrictions liées au COVID
    private static final double COVID_POURCENTAGE = 0.7;

    @Autowired
    private SalleRepository salle;

    @Override
    public Salle create(Salle o) {
        return salle.save(o);
    }

    @Override
    public boolean delete(Salle o) {
        try {
            salle.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Salle update(Salle o) {
        return salle.save(o);
    }

    @Override
    public Salle findById(Long id) {
        return salle.findById(id).orElse(null);
    }

    @Override
    public List<Salle> findAll() {
        return salle.findAll();
    }

    public Salle findByName(String name) {
        return salle.findByNom(name);
    }


    // Obtenir tous equipement pour salle donnee.
    public List<String> findEquipementsBySalle(Salle s) {
        List<String> equipments = new ArrayList<>();
        if (s.getEquipements() != null) {
            for (Equipement equipment : s.getEquipements()) {
                equipments.add(equipment.getNom());
            }
        }
        return equipments;
    }

    // Rechercher des salles par capacite donnee
    public List<Salle> findSallesByCapacite(int nombrePersonnes) {
        List<Salle> salles = findAll();
        List<Salle> s = new ArrayList<>();
        for (Salle salle : salles) {
            if (salle.getCapacite() * COVID_POURCENTAGE >= nombrePersonnes) {
                s.add(salle);
            }
        }
        if (s.isEmpty()) {
            throw new UnavailableSalleException("Aucune salle disponible pour contenir ce nombre de personnes avec 70% de sa capacité initial");
        }
        return s;
    }


    // Rechercher des salles disponibles par creneau: heures de debut et de fin specifies
    public List<Salle> findSallesByCreneau(@Param("tempsDebut") LocalDateTime tempsDebut, @Param("tempsFin") LocalDateTime tempsFin) {
        List<Salle> sallesDisponible = new ArrayList<>();

        for (Salle salle : salle.findAll()) {
            // Verifier si la salle est non reservee ou elle est reserve avant le temps fourni
            if (salle.getDernierTempsReserve() == null
                    || salle.getDernierTempsReserve().isBefore(tempsDebut)
                    || salle.getDernierTempsReserve().isEqual(tempsDebut)
            ) {
                sallesDisponible.add(salle);
            }
        }
        if (sallesDisponible.isEmpty()) {
            throw new UnavailableSalleException("Aucune salle disponible pour ce creneau et libre de 1H pour nettoyage");
        }
        return sallesDisponible;
    }

    // Rechercher des salles par type de reunion
    public List<Salle> findSallesByReunion(String typeReunion) {
        List<Salle> salles = findAll();
        List<Salle> s = new ArrayList<>();

        for (Salle salle : salles) {
            List<String> equipementsPerSalle = findEquipementsBySalle(salle);
            switch (typeReunion) {
                case "VC":
                    // VC: VisioConférences
                    if (equipementsPerSalle.contains("Ecran") && equipementsPerSalle.contains("Pieuvre") &&
                            equipementsPerSalle.contains("Webcam")) {
                        s.add(salle);
                    }
                    break;
                case "SPEC":
                    // SPEC: Séances de Partage et d'Etudes de Cas
                    if (equipementsPerSalle.contains("Tableau")) {
                        s.add(salle);
                    }
                    break;
                case "RC":
                    // RC: Réunions Couplées
                    if (equipementsPerSalle.contains("Tableau") && equipementsPerSalle.contains("Ecran") &&
                            equipementsPerSalle.contains("Pieuvre")) {
                        s.add(salle);
                    }
                    break;
                case "RS":
                    // RS: Réunions Simples
                    if (salle.getCapacite() * COVID_POURCENTAGE >= MINIMUM_RS_PLACES) {
                        s.add(salle);
                    }
                    break;
                default:
                    // Invalide Type de Reunion
                    throw new IllegalArgumentException("Type de Reunion non valide: " + typeReunion);
            }
        }
        if (s.isEmpty()) {
            throw new UnavailableSalleException("Aucune salle disponible pour ce type de reunion");
        }
        return s;
    }


}
