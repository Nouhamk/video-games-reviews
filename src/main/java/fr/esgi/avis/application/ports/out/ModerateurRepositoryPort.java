package fr.esgi.avis.application.ports.out;
import fr.esgi.avis.domain.model.Moderateur;
import java.util.Optional;
public interface ModerateurRepositoryPort {
    Moderateur save(Moderateur moderateur);
    Optional<Moderateur> findByEmail(String email);
}
