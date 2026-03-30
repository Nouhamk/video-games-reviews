package fr.esgi.avis.application.ports.out;

import fr.esgi.avis.domain.model.Jeu;
import java.util.List;
import java.util.Optional;

// [rôle de la classe] Port sortant pour persister et lire les jeux.
public interface JeuRepositoryPort {

    Jeu save(Jeu jeu);

    Optional<Jeu> findById(Long id);

    List<Jeu> findAll();
}

