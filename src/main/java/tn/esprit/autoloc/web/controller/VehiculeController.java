package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDto;
import tn.esprit.autoloc.web.mapper.DtoMapper;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Vehicules", description = "Gestion des véhicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;
    private final DtoMapper mapper;

    @Operation(summary = "Lister les véhicules")
    @GetMapping
    public List<VehiculeDto> findAll() {
        return vehiculeService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter un véhicule")
    @GetMapping("/{id}")
    public VehiculeDto findById(@PathVariable Long id) {
        return mapper.toDto(vehiculeService.findById(id));
    }

    @Operation(summary = "Créer un véhicule")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDto create(@Valid @RequestBody VehiculeDto dto) {
        return mapper.toDto(vehiculeService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier un véhicule")
    @PutMapping("/{id}")
    public VehiculeDto update(@PathVariable Long id, @Valid @RequestBody VehiculeDto dto) {
        return mapper.toDto(vehiculeService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Affecter un véhicule à une agence")
    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public VehiculeDto affecterAAgence(
            @PathVariable @Positive(message = "L'identifiant du véhicule doit être strictement positif")
            Long idVehicule,
            @PathVariable @Positive(message = "L'identifiant de l'agence doit être strictement positif")
            Long idAgence) {
        return mapper.toDto(vehiculeService.affecterAAgence(idVehicule, idAgence));
    }

    @Operation(summary = "Affecter un équipement à un véhicule")
    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public VehiculeDto affecterVehiculeAEquipement(
            @PathVariable @Positive(message = "L'identifiant du véhicule doit être strictement positif")
            Long idVehicule,
            @PathVariable @Positive(message = "L'identifiant de l'équipement doit être strictement positif")
            Long idEquipement) {
        return mapper.toDto(
                vehiculeService.affecterVehiculeAEquipement(idVehicule, idEquipement));
    }

    @Operation(summary = "Supprimer un véhicule")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
