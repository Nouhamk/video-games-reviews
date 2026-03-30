package fr.esgi.avis.application.ports.out;

import fr.esgi.avis.domain.model.Avis;
import java.util.List;
import java.util.Optional;

// [rôle de la classe] Port sortant pour persister et lire les avis.
public interface AvisRepositoryPort {

    Avis save(Avis avis);

    Optional<Avis> findById(Long id);

    List<Avis> findByJeuId(Long jeuId);
}

