package fr.esgi.avis.adapters.out.persistence.mapper;

import fr.esgi.avis.adapters.out.persistence.entity.AvatarJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.AvisJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.JeuJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.JoueurJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.ModerateurJpaEntity;
import fr.esgi.avis.domain.model.Avatar;
import fr.esgi.avis.domain.model.Avis;
import fr.esgi.avis.domain.model.Jeu;
import fr.esgi.avis.domain.model.Joueur;
import fr.esgi.avis.domain.model.Moderateur;

// [rôle de la classe] Mapper manuel entre le domaine Avis et son entite JPA.
public final class AvisPersistenceMapper {

    private AvisPersistenceMapper() {
    }

    public static Avis toDomain(AvisJpaEntity e) {
        if (e == null) {
            return null;
        }

        Avis avis = new Avis();
        avis.setId(e.getId());
        avis.setDescription(e.getDescription());
        avis.setNote(e.getNote());
        avis.setDateDEnvoi(e.getDateDEnvoi());
        avis.setStatut(e.getStatut());
        avis.setJeu(toDomainJeu(e.getJeu()));
        avis.setJoueur(toDomainJoueur(e.getJoueur()));
        avis.setModerateur(toDomainModerateur(e.getModerateur()));
        avis.setRaisonRejet(e.getRaisonRejet());
        return avis;
    }

    public static AvisJpaEntity toEntity(Avis a) {
        if (a == null) {
            return null;
        }

        AvisJpaEntity entity = new AvisJpaEntity();
        entity.setId(a.getId());
        entity.setDescription(a.getDescription());
        entity.setNote(a.getNote());
        entity.setDateDEnvoi(a.getDateDEnvoi());
        entity.setStatut(a.getStatut());
        entity.setJeu(toEntityJeu(a.getJeu()));
        entity.setJoueur(toEntityJoueur(a.getJoueur()));
        entity.setModerateur(toEntityModerateur(a.getModerateur()));
        entity.setRaisonRejet(a.getRaisonRejet());
        return entity;
    }

    private static Jeu toDomainJeu(JeuJpaEntity entity) {
        return JeuPersistenceMapper.toDomain(entity);
    }

    private static JeuJpaEntity toEntityJeu(Jeu domain) {
        return JeuPersistenceMapper.toEntity(domain);
    }

    private static Joueur toDomainJoueur(JoueurJpaEntity entity) {
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

    private static JoueurJpaEntity toEntityJoueur(Joueur domain) {
        if (domain == null) {
            return null;
        }
        AvatarJpaEntity avatar = domain.getAvatar() == null
                ? null
                : new AvatarJpaEntity(domain.getAvatar().getId(), domain.getAvatar().getNom());
        return new JoueurJpaEntity(
                domain.getId(),
                domain.getPseudo(),
                domain.getEmail(),
                domain.getMotDePasse(),
                domain.getDateDeNaissance(),
                avatar
        );
    }

    private static Moderateur toDomainModerateur(ModerateurJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Moderateur(
                entity.getId(),
                entity.getPseudo(),
                entity.getEmail(),
                entity.getMotDePasse(),
                entity.getNumeroDeTelephone()
        );
    }

    private static ModerateurJpaEntity toEntityModerateur(Moderateur domain) {
        if (domain == null) {
            return null;
        }
        return new ModerateurJpaEntity(
                domain.getId(),
                domain.getPseudo(),
                domain.getEmail(),
                domain.getMotDePasse(),
                domain.getNumeroDeTelephone()
        );
    }
}

