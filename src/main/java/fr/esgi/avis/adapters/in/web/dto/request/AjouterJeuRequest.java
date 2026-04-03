package fr.esgi.avis.adapters.in.web.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter @NoArgsConstructor @AllArgsConstructor
public class AjouterJeuRequest {
    @NotBlank private String nom;
    @NotBlank private String description;
    @NotNull  private LocalDate dateSortie;
    @NotNull @DecimalMin("0.0") @DecimalMax("1000.0") private Float prix;
    @NotBlank private String image;
    @NotNull  private Long genreId;
    @NotNull  private Long editeurId;
    @NotNull @NotEmpty private List<Long> plateformeIds;
    @NotNull  private Long classificationId;
}
