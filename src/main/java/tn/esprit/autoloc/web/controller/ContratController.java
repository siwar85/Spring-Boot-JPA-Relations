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
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.web.dto.ContratDto;
import tn.esprit.autoloc.web.mapper.DtoMapper;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats", description = "Gestion des contrats (avec leurs paiements)")
public class ContratController {

    private final IContratService contratService;
    private final DtoMapper mapper;

    @Operation(summary = "Lister")
    @GetMapping
    public List<ContratDto> findAll() {
        return contratService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter par identifiant")
    @GetMapping("/{id}")
    public ContratDto findById(@PathVariable Long id) {
        return mapper.toDto(contratService.findById(id));
    }

    @Operation(summary = "Créer un contrat")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDto create(@Valid @RequestBody ContratDto dto) {
        return mapper.toDto(contratService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier un contrat")
    @PutMapping("/{id}")
    public ContratDto update(@PathVariable Long id, @Valid @RequestBody ContratDto dto) {
        return mapper.toDto(contratService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un contrat")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}
