package fr.esgi.avis.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Modele metier representant un editeur de jeux.
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Editeur {
    private Long id;
    private String nom;
}

