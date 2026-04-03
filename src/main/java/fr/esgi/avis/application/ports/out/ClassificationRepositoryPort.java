package fr.esgi.avis.application.ports.out;
import fr.esgi.avis.domain.model.Classification;
import java.util.List;
import java.util.Optional;
public interface ClassificationRepositoryPort {
    List<Classification> findAll();
    Optional<Classification> findById(Long id);
}
