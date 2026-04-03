package fr.esgi.avis.application.ports.out;
import fr.esgi.avis.domain.model.Avis;
import java.util.List;
import java.util.Optional;
public interface AvisRepositoryPort {
    Avis save(Avis avis);
    Optional<Avis> findById(Long id);
    List<Avis> findByJeuId(Long jeuId);
}
