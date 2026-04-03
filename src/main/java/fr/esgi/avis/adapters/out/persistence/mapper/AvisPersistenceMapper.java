package fr.esgi.avis.adapters.out.persistence.mapper;

import fr.esgi.avis.adapters.out.persistence.entity.AvatarJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.AvisJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.JoueurJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.ModerateurJpaEntity;
import fr.esgi.avis.domain.model.Avatar;
import fr.esgi.avis.domain.model.Avis;
import fr.esgi.avis.domain.model.Joueur;
import fr.esgi.avis.domain.model.Moderateur;

public final class AvisPersistenceMapper {

    private AvisPersistenceMapper() {}

    public static Avis toDomain(AvisJpaEntity e) {
        if (e == null) return null;
        Avis avis = new Avis();
        avis.setId(e.getId());
        avis.setDescription(e.getDescription());
        avis.setNote(e.getNote());
        avis.setDateDEnvoi(e.getDateDEnvoi());
        avis.setStatut(e.getStatut());
        avis.setJeu(JeuPersistenceMapper.toDomain(e.getJeu()));
        avis.setJoueur(toDomainJoueur(e.getJoueur()));
        avis.setModerateur(toDomainModerateur(e.getModerateur()));
        avis.setRaisonRejet(e.getRaisonRejet());
        return avis;
    }

    public static AvisJpaEntity toEntity(Avis a) {
        if (a == null) return null;
        AvisJpaEntity e = new AvisJpaEntity();
        e.setId(a.getId());
        e.setDescription(a.getDescription());
        e.setNote(a.getNote());
        e.setDateDEnvoi(a.getDateDEnvoi());
        e.setStatut(a.getStatut());
        e.setJeu(JeuPersistenceMapper.toEntity(a.getJeu()));
        e.setJoueur(toEntityJoueur(a.getJoueur()));
        e.setModerateur(toEntityModerateur(a.getModerateur()));
        e.setRaisonRejet(a.getRaisonRejet());
        return e;
    }

    private static Joueur toDomainJoueur(JoueurJpaEntity e) {
        if (e == null) return null;
        Avatar avatar = e.getAvatar() == null ? null :
                Avatar.builder().id(e.getAvatar().getId()).nom(e.getAvatar().getNom()).build();
        return new Joueur(e.getId(), e.getPseudo(), e.getEmail(),
                e.getMotDePasse(), e.getDateDeNaissance(), avatar);
    }

    private static JoueurJpaEntity toEntityJoueur(Joueur d) {
        if (d == null) return null;
        JoueurJpaEntity e = new JoueurJpaEntity();
        e.setId(d.getId());
        if (d.getAvatar() != null && d.getAvatar().getId() != null) {
            AvatarJpaEntity av = new AvatarJpaEntity();
            av.setId(d.getAvatar().getId());
            e.setAvatar(av);
        }
        return e;
    }

    private static Moderateur toDomainModerateur(ModerateurJpaEntity e) {
        if (e == null) return null;
        return new Moderateur(e.getId(), e.getPseudo(), e.getEmail(),
                e.getMotDePasse(), e.getNumeroDeTelephone());
    }

    private static ModerateurJpaEntity toEntityModerateur(Moderateur d) {
        if (d == null) return null;
        ModerateurJpaEntity e = new ModerateurJpaEntity();
        e.setId(d.getId());
        return e;
    }
}
