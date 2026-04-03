package fr.esgi.avis.adapters.in.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter @Builder
public class JeuResponse {
    private Long id;
    private String nom;
    private String description;
    private LocalDate dateSortie;
    private Float prix;
    private String image;
    private Long genreId;
    private Long editeurId;
    private List<Long> plateformeIds;
    private Long classificationId;
}
