package fr.esgi.avis.adapters.out.persistence;
import fr.esgi.avis.adapters.out.persistence.entity.ClassificationJpaEntity;
import fr.esgi.avis.adapters.out.persistence.repository.ClassificationSpringDataRepository;
import fr.esgi.avis.application.ports.out.ClassificationRepositoryPort;
import fr.esgi.avis.domain.model.Classification;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public class ClassificationJpaAdapter implements ClassificationRepositoryPort {
    private final ClassificationSpringDataRepository repo;
    public ClassificationJpaAdapter(ClassificationSpringDataRepository repo) { this.repo = repo; }
    @Override public List<Classification> findAll() {
        return repo.findAll().stream().map(this::toDomain).toList();
    }
    @Override public Optional<Classification> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }
    private Classification toDomain(ClassificationJpaEntity e) {
        if (e == null) return null;
        return Classification.builder().id(e.getId()).nom(e.getNom())
                .couleurRGB(e.getCouleurRGB()).build();
    }
}
