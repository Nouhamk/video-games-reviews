package fr.esgi.avis.adapters.out.persistence;
import fr.esgi.avis.adapters.out.persistence.entity.GenreJpaEntity;
import fr.esgi.avis.adapters.out.persistence.repository.GenreSpringDataRepository;
import fr.esgi.avis.application.ports.out.GenreRepositoryPort;
import fr.esgi.avis.domain.model.Genre;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public class GenreJpaAdapter implements GenreRepositoryPort {
    private final GenreSpringDataRepository repo;
    public GenreJpaAdapter(GenreSpringDataRepository repo) { this.repo = repo; }
    @Override public List<Genre> findAll() {
        return repo.findAll().stream().map(this::toDomain).toList();
    }
    @Override public Optional<Genre> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }
    private Genre toDomain(GenreJpaEntity e) {
        if (e == null) return null;
        return Genre.builder().id(e.getId()).nom(e.getNom()).build();
    }
}
