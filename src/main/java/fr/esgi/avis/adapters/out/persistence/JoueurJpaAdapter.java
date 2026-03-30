package fr.esgi.avis.adapters.out.persistence;

import fr.esgi.avis.adapters.out.persistence.entity.AvatarJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.JoueurJpaEntity;
import fr.esgi.avis.adapters.out.persistence.repository.JoueurSpringDataRepository;
import fr.esgi.avis.application.ports.out.JoueurRepositoryPort;
import fr.esgi.avis.domain.model.Avatar;
import fr.esgi.avis.domain.model.Joueur;
import java.util.Optional;
import org.springframework.stereotype.Repository;

// [rôle de la classe] Adapter de persistance JPA pour le port des joueurs.
@Repository
public class JoueurJpaAdapter implements JoueurRepositoryPort {

    private final JoueurSpringDataRepository repo;

    public JoueurJpaAdapter(JoueurSpringDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public Joueur save(Joueur joueur) {
        return toDomain(repo.save(toEntity(joueur)));
    }

    @Override
    public Optional<Joueur> findByEmail(String email) {
        return repo.findByEmail(email).map(this::toDomain);
    }

    private Joueur toDomain(JoueurJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        Avatar avatar = entity.getAvatar() == null ? null : Avatar.builder()
                .id(entity.getAvatar().getId())
                .nom(entity.getAvatar().getNom())
                .build();
        return new Joueur(
                entity.getId(),
                entity.getPseudo(),
                entity.getEmail(),
                entity.getMotDePasse(),
                entity.getDateDeNaissance(),
                avatar
        );
    }

    private JoueurJpaEntity toEntity(Joueur joueur) {
        if (joueur == null) {
            return null;
        }
        AvatarJpaEntity avatar = joueur.getAvatar() == null
                ? null
                : new AvatarJpaEntity(joueur.getAvatar().getId(), joueur.getAvatar().getNom());
        return new JoueurJpaEntity(
                joueur.getId(),
                joueur.getPseudo(),
                joueur.getEmail(),
                joueur.getMotDePasse(),
                joueur.getDateDeNaissance(),
                avatar
        );
    }
}

