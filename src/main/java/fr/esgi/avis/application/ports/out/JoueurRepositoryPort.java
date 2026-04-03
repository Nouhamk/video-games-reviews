package fr.esgi.avis.application.ports.out;
import fr.esgi.avis.domain.model.Joueur;
import java.util.Optional;
public interface JoueurRepositoryPort {
    Joueur save(Joueur joueur);
    Optional<Joueur> findByEmail(String email);
}
