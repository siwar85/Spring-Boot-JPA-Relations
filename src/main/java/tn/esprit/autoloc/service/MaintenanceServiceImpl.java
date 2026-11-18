package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        if (maintenance.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        verifierDates(maintenance);
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        Maintenance existante = findById(id);
        verifierDates(maintenance);
        existante.setDateDebut(maintenance.getDateDebut());
        existante.setDateFin(maintenance.getDateFin());
        existante.setDescription(maintenance.getDescription());
        return maintenanceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Maintenance affecterMaintenanceAVehicule(Long idMaintenance, Long idVehicule) {
        if (idMaintenance == null || idVehicule == null) {
            throw new IllegalArgumentException(
                    "Les identifiants de la maintenance et du véhicule sont obligatoires");
        }

        // 404 si l'un des deux n'existe pas
        Maintenance maintenance = findById(idMaintenance);
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));

        // Déjà affectée à ce véhicule ?
        if (maintenance.getVehicule() != null
                && maintenance.getVehicule().getIdVehicule().equals(idVehicule)) {
            throw new IllegalArgumentException(
                    "La maintenance est déjà affectée à ce véhicule");
        }

        // Maintenance porte la clé étrangère : on pose le lien de ce côté
        maintenance.setVehicule(vehicule);
        return maintenanceRepository.save(maintenance);
    }

    private void verifierDates(Maintenance maintenance) {
        if (maintenance.getDateDebut() == null) {
            throw new IllegalArgumentException(
                    "La date de début de la maintenance est obligatoire");
        }
        if (maintenance.getDateFin() != null
                && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}