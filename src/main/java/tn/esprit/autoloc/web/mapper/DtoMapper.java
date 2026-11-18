package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.AgenceDto;
import tn.esprit.autoloc.web.dto.ClientDto;
import tn.esprit.autoloc.web.dto.ContratDto;
import tn.esprit.autoloc.web.dto.EmployeDto;
import tn.esprit.autoloc.web.dto.EquipementDto;
import tn.esprit.autoloc.web.dto.MaintenanceDto;
import tn.esprit.autoloc.web.dto.PaiementDto;
import tn.esprit.autoloc.web.dto.ReservationDto;
import tn.esprit.autoloc.web.dto.VehiculeDto;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Conversion entité <-> DTO.
 * Les liens entre entités (agence, client, véhicule...) ne sont pas posés par
 * toEntity : ils se font avec les méthodes d'affectation.
 */
@Component
public class DtoMapper {

    // ----- Agence -----
    public AgenceDto toDto(Agence agence) {
        return new AgenceDto(agence.getIdAgence(), agence.getNom(), agence.getVille(),
                agence.getAdresse(), agence.getTelephone());
    }

    public Agence toEntity(AgenceDto dto) {
        Agence agence = new Agence();
        agence.setNom(dto.nom());
        agence.setVille(dto.ville());
        agence.setAdresse(dto.adresse());
        agence.setTelephone(dto.telephone());
        return agence;
    }

    // ----- Client -----
    public ClientDto toDto(Client client) {
        return new ClientDto(client.getIdClient(), client.getNom(), client.getPrenom(),
                client.getEmail(), client.getTelephone(), client.getNumPermis(),
                client.getDateInscription());
    }

    public Client toEntity(ClientDto dto) {
        Client client = new Client();
        client.setNom(dto.nom());
        client.setPrenom(dto.prenom());
        client.setEmail(dto.email());
        client.setTelephone(dto.telephone());
        client.setNumPermis(dto.numPermis());
        client.setDateInscription(dto.dateInscription());
        return client;
    }

    // ----- Employe -----
    public EmployeDto toDto(Employe employe) {
        Agence agence = employe.getAgence();
        return new EmployeDto(employe.getIdEmploye(), employe.getNom(), employe.getPrenom(),
                employe.getRole(),
                agence != null ? agence.getIdAgence() : null,
                agence != null ? agence.getNom() : null);
    }

    public Employe toEntity(EmployeDto dto) {
        Employe employe = new Employe();
        employe.setNom(dto.nom());
        employe.setPrenom(dto.prenom());
        employe.setRole(dto.role());
        return employe;
    }

    // ----- Equipement -----
    public EquipementDto toDto(Equipement equipement) {
        return new EquipementDto(equipement.getIdEquipement(), equipement.getLibelle());
    }

    public Equipement toEntity(EquipementDto dto) {
        Equipement equipement = new Equipement();
        equipement.setLibelle(dto.libelle());
        return equipement;
    }

    // ----- Vehicule -----
    public VehiculeDto toDto(Vehicule vehicule) {
        Agence agence = vehicule.getAgence();
        Set<EquipementDto> equipements = vehicule.getEquipements().stream()
                .map(this::toDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return new VehiculeDto(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                agence != null ? agence.getIdAgence() : null,
                agence != null ? agence.getNom() : null,
                equipements
        );
    }

    public Vehicule toEntity(VehiculeDto dto) {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation(dto.immatriculation());
        vehicule.setMarque(dto.marque());
        vehicule.setModele(dto.modele());
        vehicule.setCategorie(dto.categorie());
        vehicule.setTarifJournalier(dto.tarifJournalier());
        vehicule.setStatut(dto.statut());
        return vehicule;
    }

    // ----- Maintenance -----
    public MaintenanceDto toDto(Maintenance maintenance) {
        Vehicule vehicule = maintenance.getVehicule();
        return new MaintenanceDto(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                vehicule != null ? vehicule.getIdVehicule() : null,
                vehicule != null ? vehicule.getImmatriculation() : null
        );
    }

    public Maintenance toEntity(MaintenanceDto dto) {
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(dto.dateDebut());
        maintenance.setDateFin(dto.dateFin());
        maintenance.setDescription(dto.description());
        return maintenance;
    }

    // ----- Reservation -----
    public ReservationDto toDto(Reservation reservation) {
        Client client = reservation.getClient();
        Vehicule vehicule = reservation.getVehicule();
        return new ReservationDto(reservation.getIdReservation(), reservation.getDateDebut(),
                reservation.getDateFin(), reservation.getStatut(),
                client != null ? client.getIdClient() : null,
                vehicule != null ? vehicule.getIdVehicule() : null);
    }

    public Reservation toEntity(ReservationDto dto) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(dto.dateDebut());
        reservation.setDateFin(dto.dateFin());
        reservation.setStatut(dto.statut());
        return reservation;
    }

    // ----- Paiement -----
    public PaiementDto toDto(Paiement paiement) {
        Contrat contrat = paiement.getContrat();
        return new PaiementDto(paiement.getIdPaiement(), paiement.getMontant(),
                paiement.getDatePaiement(), paiement.getModePaiement(),
                contrat != null ? contrat.getIdContrat() : null);
    }

    public Paiement toEntity(PaiementDto dto) {
        Paiement paiement = new Paiement();
        paiement.setMontant(dto.montant());
        paiement.setDatePaiement(dto.datePaiement());
        paiement.setModePaiement(dto.modePaiement());
        return paiement;
    }

    // ----- Contrat (composition : un contrat contient ses paiements) -----
    public ContratDto toDto(Contrat contrat) {
        Reservation reservation = contrat.getReservation();
        List<PaiementDto> paiements = contrat.getPaiements().stream()
                .map(this::toDto)
                .toList();
        return new ContratDto(contrat.getIdContrat(), contrat.getDateSignature(),
                contrat.getMontantTotal(), contrat.isValide(),
                reservation != null ? reservation.getIdReservation() : null,
                paiements);
    }

    public Contrat toEntity(ContratDto dto) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dto.dateSignature());
        contrat.setMontantTotal(dto.montantTotal());
        contrat.setValide(dto.valide());
        if (dto.paiements() != null) {
            for (PaiementDto paiementDto : dto.paiements()) {
                Paiement paiement = toEntity(paiementDto);
                paiement.setContrat(contrat);      // chaque paiement référence son contrat
                contrat.getPaiements().add(paiement);
            }
        }
        return contrat;
    }
}
