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
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationDto;
import tn.esprit.autoloc.web.mapper.DtoMapper;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Gestion des réservations")
public class ReservationController {

    private final IReservationService reservationService;
    private final DtoMapper mapper;

    @Operation(summary = "Lister")
    @GetMapping
    public List<ReservationDto> findAll() {
        return reservationService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter par identifiant")
    @GetMapping("/{id}")
    public ReservationDto findById(@PathVariable Long id) {
        return mapper.toDto(reservationService.findById(id));
    }

    @Operation(summary = "Créer une réservation")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto create(@Valid @RequestBody ReservationDto dto) {
        return mapper.toDto(reservationService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Modifier une réservation")
    @PutMapping("/{id}")
    public ReservationDto update(@PathVariable Long id, @Valid @RequestBody ReservationDto dto) {
        return mapper.toDto(reservationService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer une réservation")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
