package fr.esgi.avis.adapters.out.persistence;
import fr.esgi.avis.adapters.out.persistence.mapper.AvisPersistenceMapper;
import fr.esgi.avis.adapters.out.persistence.repository.AvisSpringDataRepository;
import fr.esgi.avis.application.ports.out.AvisRepositoryPort;
import fr.esgi.avis.domain.model.Avis;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public class AvisJpaAdapter implements AvisRepositoryPort {
    private final AvisSpringDataRepository repo;
    public AvisJpaAdapter(AvisSpringDataRepository repo) { this.repo = repo; }
    @Override public Avis save(Avis avis) {
        return AvisPersistenceMapper.toDomain(repo.save(AvisPersistenceMapper.toEntity(avis)));
    }
    @Override public Optional<Avis> findById(Long id) {
        return repo.findById(id).map(AvisPersistenceMapper::toDomain);
    }
    @Override public List<Avis> findByJeuId(Long jeuId) {
        return repo.findByJeuId(jeuId).stream().map(AvisPersistenceMapper::toDomain).toList();
    }
}
