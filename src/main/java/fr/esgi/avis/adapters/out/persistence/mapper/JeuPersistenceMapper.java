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
import java.util.Objects;

// [rôle de la classe] Mapper manuel entre le domaine Jeu et son entite JPA.
public final class JeuPersistenceMapper {

    private JeuPersistenceMapper() {
    }

    public static Jeu toDomain(JeuJpaEntity e) {
        if (e == null) return null;
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
        if (j == null) return null;
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

    private static Genre toDomainGenre(GenreJpaEntity e) {
        return e == null ? null : Genre.builder().id(e.getId()).nom(e.getNom()).build();
    }

    private static GenreJpaEntity toEntityGenre(Genre d) {
        if (d == null) return null;
        GenreJpaEntity e = new GenreJpaEntity();
        e.setId(d.getId());
        e.setNom(d.getNom());
        return e;
    }

    private static Editeur toDomainEditeur(EditeurJpaEntity e) {
        return e == null ? null : Editeur.builder().id(e.getId()).nom(e.getNom()).build();
    }

    private static EditeurJpaEntity toEntityEditeur(Editeur d) {
        if (d == null) return null;
        EditeurJpaEntity e = new EditeurJpaEntity();
        e.setId(d.getId());
        e.setNom(d.getNom());
        return e;
    }

    private static Classification toDomainClassification(ClassificationJpaEntity e) {
        return e == null ? null
                : Classification.builder().id(e.getId()).nom(e.getNom()).couleurRGB(e.getCouleurRGB()).build();
    }

    private static ClassificationJpaEntity toEntityClassification(Classification d) {
        if (d == null) return null;
        ClassificationJpaEntity e = new ClassificationJpaEntity();
        e.setId(d.getId());
        e.setNom(d.getNom());
        e.setCouleurRGB(d.getCouleurRGB());
        return e;
    }

    private static Plateforme toDomainPlateforme(PlateformeJpaEntity e) {
        return e == null ? null
                : Plateforme.builder().id(e.getId()).nom(e.getNom()).dateDeSortie(e.getDateDeSortie()).build();
    }

    private static PlateformeJpaEntity toEntityPlateforme(Plateforme d) {
        if (d == null) return null;
        PlateformeJpaEntity e = new PlateformeJpaEntity();
        e.setId(d.getId());
        e.setNom(d.getNom());
        e.setDateDeSortie(d.getDateDeSortie());
        return e;
    }
}
