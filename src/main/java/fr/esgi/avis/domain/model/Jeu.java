package fr.esgi.avis.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jeu {
    private Long id;
    private String nom;
    private String description;
    private LocalDate dateSortie;
    private Float prix;
    private String image;
    private Genre genre;
    private Editeur editeur;
    @Builder.Default
    private List<Plateforme> plateformes = new ArrayList<>();
    private Classification classification;
}
