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
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.web.dto.EmployeDto;
import tn.esprit.autoloc.web.mapper.DtoMapper;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employes", description = "Gestion des employés")
public class EmployeController {

    private final IEmployeService employeService;
    private final DtoMapper mapper;

    @Operation(summary = "Lister")
    @GetMapping
    public List<EmployeDto> findAll() {
        return employeService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter par identifiant")
    @GetMapping("/{id}")
    public EmployeDto findById(@PathVariable Long id) {
        return mapper.toDto(employeService.findById(id));
    }

    @Operation(summary = "Créer un employé")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDto create(@Valid @RequestBody EmployeDto dto) {
        return mapper.toDto(employeService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier un employé")
    @PutMapping("/{id}")
    public EmployeDto update(@PathVariable Long id, @Valid @RequestBody EmployeDto dto) {
        return mapper.toDto(employeService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un employé")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}
