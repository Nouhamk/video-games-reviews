package fr.esgi.avis.adapters.out.persistence.mapper;

import fr.esgi.avis.adapters.out.persistence.entity.ClassificationJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.EditeurJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.GenreJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.JeuJpaEntity;
import fr.esgi.avis.adapters.out.persistence.entity.PlateformeJpaEntity;
import fr.esgi.avis.domain.model.Classification;
import fr.esgi.avis.domain.model.Editeur;
import fr.esgi.avis.domain.model.Genre;
import fr.esgi.avis.domain.model.Jeu;
import fr.esgi.avis.domain.model.Plateforme;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// [rôle de la classe] Mapper manuel entre le domaine Jeu et son entite JPA.
public final class JeuPersistenceMapper {

    private JeuPersistenceMapper() {
    }

    public static Jeu toDomain(JeuJpaEntity e) {
        if (e == null) {
            return null;
        }

        Jeu jeu = new Jeu();
        jeu.setId(e.getId());
        jeu.setNom(e.getNom());
        jeu.setDescription(e.getDescription());
        jeu.setDateSortie(e.getDateSortie());
        jeu.setPrix(e.getPrix());
        jeu.setImage(e.getImage());
        jeu.setGenre(toDomainGenre(e.getGenre()));
        jeu.setEditeur(toDomainEditeur(e.getEditeur()));
        jeu.setClassification(toDomainClassification(e.getClassification()));
        jeu.setPlateformes(
                e.getPlateformes() == null
                        ? Collections.emptyList()
                        : e.getPlateformes().stream().map(JeuPersistenceMapper::toDomainPlateforme).toList()
        );
        return jeu;
    }

    public static JeuJpaEntity toEntity(Jeu j) {
        if (j == null) {
            return null;
        }

        JeuJpaEntity entity = new JeuJpaEntity();
        entity.setId(j.getId());
        entity.setNom(j.getNom());
        entity.setDescription(j.getDescription());
        entity.setDateSortie(j.getDateSortie());
        entity.setPrix(j.getPrix());
        entity.setImage(j.getImage());
        entity.setGenre(toEntityGenre(j.getGenre()));
        entity.setEditeur(toEntityEditeur(j.getEditeur()));
        entity.setClassification(toEntityClassification(j.getClassification()));
        entity.setPlateformes(
                j.getPlateformes() == null
                        ? Collections.emptyList()
                        : j.getPlateformes().stream().map(JeuPersistenceMapper::toEntityPlateforme).filter(Objects::nonNull).toList()
        );
        return entity;
    }

    private static Genre toDomainGenre(GenreJpaEntity entity) {
        return entity == null ? null : Genre.builder().id(entity.getId()).nom(entity.getNom()).build();
    }

    private static GenreJpaEntity toEntityGenre(Genre domain) {
        return domain == null ? null : new GenreJpaEntity(domain.getId(), domain.getNom());
    }

    private static Editeur toDomainEditeur(EditeurJpaEntity entity) {
        return entity == null ? null : Editeur.builder().id(entity.getId()).nom(entity.getNom()).build();
    }

    private static EditeurJpaEntity toEntityEditeur(Editeur domain) {
        return domain == null ? null : new EditeurJpaEntity(domain.getId(), domain.getNom());
    }

    private static Classification toDomainClassification(ClassificationJpaEntity entity) {
        return entity == null
                ? null
                : Classification.builder().id(entity.getId()).nom(entity.getNom()).couleurRGB(entity.getCouleurRGB()).build();
    }

    private static ClassificationJpaEntity toEntityClassification(Classification domain) {
        return domain == null ? null : new ClassificationJpaEntity(domain.getId(), domain.getNom(), domain.getCouleurRGB());
    }

    private static Plateforme toDomainPlateforme(PlateformeJpaEntity entity) {
        return entity == null
                ? null
                : Plateforme.builder().id(entity.getId()).nom(entity.getNom()).dateDeSortie(entity.getDateDeSortie()).build();
    }

    private static PlateformeJpaEntity toEntityPlateforme(Plateforme domain) {
        return domain == null ? null : new PlateformeJpaEntity(domain.getId(), domain.getNom(), domain.getDateDeSortie());
    }
}

