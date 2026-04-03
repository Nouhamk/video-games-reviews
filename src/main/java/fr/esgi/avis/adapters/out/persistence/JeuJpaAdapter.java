package fr.esgi.avis.adapters.out.persistence;
import fr.esgi.avis.adapters.out.persistence.mapper.JeuPersistenceMapper;
import fr.esgi.avis.adapters.out.persistence.repository.JeuSpringDataRepository;
import fr.esgi.avis.application.ports.out.JeuRepositoryPort;
import fr.esgi.avis.domain.model.Jeu;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public class JeuJpaAdapter implements JeuRepositoryPort {
    private final JeuSpringDataRepository repo;
    public JeuJpaAdapter(JeuSpringDataRepository repo) { this.repo = repo; }
    @Override public Jeu save(Jeu jeu) {
        return JeuPersistenceMapper.toDomain(repo.save(JeuPersistenceMapper.toEntity(jeu)));
    }
    @Override public Optional<Jeu> findById(Long id) {
        return repo.findById(id).map(JeuPersistenceMapper::toDomain);
    }
    @Override public List<Jeu> findAll() {
        return repo.findAll().stream().map(JeuPersistenceMapper::toDomain).toList();
    }
    @Override public void deleteById(Long id) { repo.deleteById(id); }
}
