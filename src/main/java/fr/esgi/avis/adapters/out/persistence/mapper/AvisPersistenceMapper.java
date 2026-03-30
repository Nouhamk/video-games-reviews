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
import fr.esgi.avis.domain.model.StatutAvis;

// [rôle de la classe] Mapper manuel entre le domaine Avis et son entite JPA.
public final class AvisPersistenceMapper {

    private AvisPersistenceMapper() {
    }

    public static Avis toDomain(AvisJpaEntity e) {
        if (e == null) return null;
        Avis avis = new Avis();
        avis.setId(e.getId());
        avis.setDescription(e.getDescription());
        avis.setNote(e.getNote());
        avis.setDateDEnvoi(e.getDateDEnvoi());
        avis.setStatut(e.getStatut() == null ? StatutAvis.EN_ATTENTE : e.getStatut());
        avis.setJeu(JeuPersistenceMapper.toDomain(e.getJeu()));
        avis.setJoueur(toDomainJoueur(e.getJoueur()));
        avis.setModerateur(toDomainModerateur(e.getModerateur()));
        avis.setRaisonRejet(e.getRaisonRejet());
        return avis;
    }

    public static AvisJpaEntity toEntity(Avis a) {
        if (a == null) return null;
        AvisJpaEntity entity = new AvisJpaEntity();
        entity.setId(a.getId());
        entity.setDescription(a.getDescription());
        entity.setNote(a.getNote());
        entity.setDateDEnvoi(a.getDateDEnvoi());
        entity.setStatut(a.getStatut() == null ? StatutAvis.EN_ATTENTE : a.getStatut());
        entity.setJeu(JeuPersistenceMapper.toEntity(a.getJeu()));
        entity.setJoueur(toEntityJoueur(a.getJoueur()));
        entity.setModerateur(toEntityModerateur(a.getModerateur()));
        entity.setRaisonRejet(a.getRaisonRejet());
        return entity;
    }

    private static Joueur toDomainJoueur(JoueurJpaEntity e) {
        if (e == null) return null;
        Avatar avatar = null;
        if (e.getAvatar() != null) {
            avatar = Avatar.builder().id(e.getAvatar().getId()).nom(e.getAvatar().getNom()).build();
        }
        return new Joueur(e.getId(), e.getPseudo(), e.getEmail(), e.getMotDePasse(), e.getDateDeNaissance(), avatar);
    }

    private static JoueurJpaEntity toEntityJoueur(Joueur d) {
        if (d == null) return null;
        AvatarJpaEntity avatar = null;
        if (d.getAvatar() != null) {
            avatar = new AvatarJpaEntity();
            avatar.setId(d.getAvatar().getId());
            avatar.setNom(d.getAvatar().getNom());
        }
        JoueurJpaEntity e = new JoueurJpaEntity();
        e.setId(d.getId());
        e.setPseudo(d.getPseudo());
        e.setEmail(d.getEmail());
        e.setMotDePasse(d.getMotDePasse());
        e.setDateDeNaissance(d.getDateDeNaissance());
        e.setAvatar(avatar);
        return e;
    }

    private static Moderateur toDomainModerateur(ModerateurJpaEntity e) {
        if (e == null) return null;
        return new Moderateur(e.getId(), e.getPseudo(), e.getEmail(), e.getMotDePasse(), e.getNumeroDeTelephone());
    }

    private static ModerateurJpaEntity toEntityModerateur(Moderateur d) {
        if (d == null) return null;
        return new ModerateurJpaEntity(d.getId(), d.getPseudo(), d.getEmail(), d.getMotDePasse(), d.getNumeroDeTelephone());
    }
}
