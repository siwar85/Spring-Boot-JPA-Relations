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
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceDto;
import tn.esprit.autoloc.web.mapper.DtoMapper;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;
    private final DtoMapper mapper;

    @Operation(summary = "Lister les maintenances")
    @GetMapping
    public List<MaintenanceDto> findAll() {
        return maintenanceService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter une maintenance")
    @GetMapping("/{id}")
    public MaintenanceDto findById(@PathVariable Long id) {
        return mapper.toDto(maintenanceService.findById(id));
    }

    @Operation(summary = "Créer une maintenance")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDto create(@Valid @RequestBody MaintenanceDto dto) {
        return mapper.toDto(maintenanceService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier une maintenance")
    @PutMapping("/{id}")
    public MaintenanceDto update(@PathVariable Long id, @Valid @RequestBody MaintenanceDto dto) {
        return mapper.toDto(maintenanceService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Affecter une maintenance à un véhicule")
    @PutMapping("/{idMaintenance}/vehicule/{idVehicule}")
    public MaintenanceDto affecterMaintenanceAVehicule(
            @PathVariable @Positive(message = "L'identifiant de la maintenance doit être strictement positif")
            Long idMaintenance,
            @PathVariable @Positive(message = "L'identifiant du véhicule doit être strictement positif")
            Long idVehicule) {
        return mapper.toDto(
                maintenanceService.affecterMaintenanceAVehicule(idMaintenance, idVehicule));
    }

    @Operation(summary = "Supprimer une maintenance")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
