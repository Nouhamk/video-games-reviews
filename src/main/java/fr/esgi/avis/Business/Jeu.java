package fr.esgi.avis.Business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Jeu {

    private Long id;
    private String nom;
    private String description;
    private LocalDate dateSortie;
    private float prix;
    private String image;
    private Genre genre;
    private Editeur editeur;
    private List<Plateforme> plateformes;
    private Classification classification;
}