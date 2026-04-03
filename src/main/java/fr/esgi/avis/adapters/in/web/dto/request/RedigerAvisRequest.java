package fr.esgi.avis.adapters.in.web.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class RedigerAvisRequest {
    @NotBlank private String description;
    @NotNull @DecimalMin("0.0") @DecimalMax("10.0") private Float note;
    @NotNull  private Long jeuId;
}
