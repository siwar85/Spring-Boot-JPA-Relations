package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        verifierTarif(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = findById(id);
        verifierTarif(vehicule);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Vehicule affecterAAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = findById(idVehicule);
        Agence nouvelleAgence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));

        Agence ancienneAgence = vehicule.getAgence();
        if (ancienneAgence != null) {
            if (ancienneAgence.getIdAgence().equals(idAgence)) {
                throw new IllegalArgumentException(
                        "Le véhicule est déjà affecté à cette agence");
            }
            ancienneAgence.getVehicules().remove(vehicule);
        }

        vehicule.setAgence(nouvelleAgence);
        nouvelleAgence.getVehicules().add(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        if (idVehicule == null || idEquipement == null) {
            throw new IllegalArgumentException(
                    "Les identifiants du véhicule et de l'équipement sont obligatoires");
        }

        // 404 si l'un des deux n'existe pas
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));

        // add() renvoie false si l'équipement est déjà dans le Set
        if (!vehicule.getEquipements().add(equipement)) {
            throw new IllegalArgumentException(
                    "L'équipement '" + equipement.getLibelle()
                            + "' est déjà affecté à ce véhicule");
        }
        return vehiculeRepository.save(vehicule);
    }

    private void verifierTarif(Vehicule vehicule) {
        BigDecimal tarif = vehicule.getTarifJournalier();
        if (tarif == null || tarif.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier du véhicule ne doit pas être négatif");
        }
    }
}