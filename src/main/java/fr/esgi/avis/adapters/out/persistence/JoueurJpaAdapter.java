package fr.esgi.avis.adapters.out.persistence;
import fr.esgi.avis.adapters.out.persistence.entity.AvatarJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.JoueurJpaEntity;
import fr.esgi.avis.adapters.out.persistence.repository.JoueurSpringDataRepository;
import fr.esgi.avis.application.ports.out.JoueurRepositoryPort;
import fr.esgi.avis.domain.model.Avatar;
import fr.esgi.avis.domain.model.Joueur;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public class JoueurJpaAdapter implements JoueurRepositoryPort {
    private final JoueurSpringDataRepository repo;
    public JoueurJpaAdapter(JoueurSpringDataRepository repo) { this.repo = repo; }
    @Override public Joueur save(Joueur joueur) { return toDomain(repo.save(toEntity(joueur))); }
    @Override public Optional<Joueur> findByEmail(String email) {
        return repo.findByEmail(email).map(this::toDomain);
    }
    @Override public Optional<Joueur> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }
    @Override public List<Joueur> findAll() {
        return repo.findAll().stream().map(this::toDomain).toList();
    }
    @Override public void deleteById(Long id) { repo.deleteById(id); }
    private Joueur toDomain(JoueurJpaEntity e) {
        if (e == null) return null;
        Avatar avatar = e.getAvatar() == null ? null :
                Avatar.builder().id(e.getAvatar().getId()).nom(e.getAvatar().getNom()).build();
        return new Joueur(e.getId(), e.getPseudo(), e.getEmail(),
                e.getMotDePasse(), e.getDateDeNaissance(), avatar);
    }
    private JoueurJpaEntity toEntity(Joueur j) {
        if (j == null) return null;
        AvatarJpaEntity av = null;
        if (j.getAvatar() != null && j.getAvatar().getId() != null) {
            av = new AvatarJpaEntity(); av.setId(j.getAvatar().getId());
        }
        return new JoueurJpaEntity(j.getId(), j.getPseudo(), j.getEmail(),
                j.getMotDePasse(), j.getDateDeNaissance(), av);
    }
}
