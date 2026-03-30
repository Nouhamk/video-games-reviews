package fr.esgi.avis.domain.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Modele metier representant une plateforme de jeu.
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plateforme {
    private Long id;
    private String nom;
    private LocalDate dateDeSortie;
}

