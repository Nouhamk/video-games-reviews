package fr.esgi.avis.application.ports.in;

import fr.esgi.avis.domain.model.Jeu;
import java.util.List;

// [rôle de la classe] Port entrant definissant les cas d'usage lies aux jeux.
public interface JeuUseCase {

    Jeu ajouter(Jeu jeu);

    List<Jeu> listerTous();

    Jeu trouverParId(Long id);

    Jeu modifier(Long id, Jeu jeu);

    void supprimer(Long id);
}
