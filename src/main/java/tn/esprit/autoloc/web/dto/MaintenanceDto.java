package tn.esprit.autoloc.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MaintenanceDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long idMaintenance,

        @NotNull(message = "La date de début est obligatoire")
        LocalDate dateDebut,

        LocalDate dateFin,

        @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
        String description,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long idVehicule,

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String immatriculationVehicule
) {}