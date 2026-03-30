package fr.esgi.avis.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Modele metier representant un jeu video.
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

