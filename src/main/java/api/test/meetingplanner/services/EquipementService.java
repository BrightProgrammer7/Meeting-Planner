package api.test.meetingplanner.services;

import api.test.meetingplanner.dao.IDao;
import api.test.meetingplanner.entities.Equipement;
import api.test.meetingplanner.repositories.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipementService implements IDao<Equipement> {
    @Autowired
    private EquipementRepository equipement;

    @Override
    public Equipement create(Equipement o) {
        return equipement.save(o);
    }

    @Override
    public boolean delete(Equipement o) {
        try {
            equipement.delete(o);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Equipement update(Equipement o) {
        return equipement.save(o);
    }

    @Override
    public Equipement findById(Long id) {
        return equipement.findById(id).orElse(null);
    }

    @Override
    public List<Equipement> findAll() {
        return equipement.findAll();
    }
}
