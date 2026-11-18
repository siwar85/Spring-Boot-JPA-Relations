package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import tn.esprit.autoloc.service.IEquipementService;
import tn.esprit.autoloc.web.dto.EquipementDto;
import tn.esprit.autoloc.web.mapper.DtoMapper;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Equipements", description = "Gestion des équipements")
public class EquipementController {

    private final IEquipementService equipementService;
    private final DtoMapper mapper;

    @Operation(summary = "Lister")
    @GetMapping
    public List<EquipementDto> findAll() {
        return equipementService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter par identifiant")
    @GetMapping("/{id}")
    public EquipementDto findById(@PathVariable Long id) {
        return mapper.toDto(equipementService.findById(id));
    }

    @Operation(summary = "Créer un équipement")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDto create(@Valid @RequestBody EquipementDto dto) {
        return mapper.toDto(equipementService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier un équipement")
    @PutMapping("/{id}")
    public EquipementDto update(@PathVariable Long id, @Valid @RequestBody EquipementDto dto) {
        return mapper.toDto(equipementService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un équipement")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
