package fr.esgi.avis.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Modele metier representant un avatar de joueur.
@Getter
@Setter
@NoArgsConstructor
public class Avatar {
    private Long id;
    private String nom;
    private Joueur joueur;

    @Builder
    public Avatar(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
}



