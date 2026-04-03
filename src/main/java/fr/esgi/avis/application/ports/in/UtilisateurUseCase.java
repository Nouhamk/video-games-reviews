package fr.esgi.avis.application.ports.in;

import fr.esgi.avis.domain.model.Joueur;
import fr.esgi.avis.domain.model.Moderateur;

import java.util.List;

public interface UtilisateurUseCase {

    // Joueurs
    List<Joueur>    listerJoueurs();
    Joueur          trouverJoueurParId(Long id);
    Joueur          modifierJoueur(Long id, Joueur joueur);
    void            supprimerJoueur(Long id);

    // Modérateurs
    List<Moderateur> listerModerateurs();
    Moderateur       trouverModerateurParId(Long id);
}

