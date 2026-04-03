package fr.esgi.avis.adapters.out.persistence;
import fr.esgi.avis.adapters.out.persistence.entity.ModerateurJpaEntity;
import fr.esgi.avis.adapters.out.persistence.repository.ModerateurSpringDataRepository;
import fr.esgi.avis.application.ports.out.ModerateurRepositoryPort;
import fr.esgi.avis.domain.model.Moderateur;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public class ModerateurJpaAdapter implements ModerateurRepositoryPort {
    private final ModerateurSpringDataRepository repo;
    public ModerateurJpaAdapter(ModerateurSpringDataRepository repo) { this.repo = repo; }
    @Override public Moderateur save(Moderateur m) { return toDomain(repo.save(toEntity(m))); }
    @Override public Optional<Moderateur> findByEmail(String email) {
        return repo.findByEmail(email).map(this::toDomain);
    }
    @Override public Optional<Moderateur> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }
    @Override public List<Moderateur> findAll() {
        return repo.findAll().stream().map(this::toDomain).toList();
    }
    private Moderateur toDomain(ModerateurJpaEntity e) {
        if (e == null) return null;
        return new Moderateur(e.getId(), e.getPseudo(), e.getEmail(),
                e.getMotDePasse(), e.getNumeroDeTelephone());
    }
    private ModerateurJpaEntity toEntity(Moderateur m) {
        if (m == null) return null;
        return new ModerateurJpaEntity(m.getId(), m.getPseudo(), m.getEmail(),
                m.getMotDePasse(), m.getNumeroDeTelephone());
    }
}
