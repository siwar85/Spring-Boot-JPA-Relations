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
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.web.dto.AgenceDto;
import tn.esprit.autoloc.web.mapper.DtoMapper;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {

    private final IAgenceService agenceService;
    private final DtoMapper mapper;

    @Operation(summary = "Lister")
    @GetMapping
    public List<AgenceDto> findAll() {
        return agenceService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter par identifiant")
    @GetMapping("/{id}")
    public AgenceDto findById(@PathVariable Long id) {
        return mapper.toDto(agenceService.findById(id));
    }

    @Operation(summary = "Créer une agence")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDto create(@Valid @RequestBody AgenceDto dto) {
        return mapper.toDto(agenceService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier une agence")
    @PutMapping("/{id}")
    public AgenceDto update(@PathVariable Long id, @Valid @RequestBody AgenceDto dto) {
        return mapper.toDto(agenceService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer une agence")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
