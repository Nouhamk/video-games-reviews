package fr.esgi.avis.application.ports.in;

import fr.esgi.avis.domain.model.Joueur;

// [rôle de la classe] Port entrant definissant les cas d'usage d'authentification.
public interface AuthUseCase {

    Joueur inscrire(Joueur joueur);

    String connecter(String email, String motDePasse);
}

