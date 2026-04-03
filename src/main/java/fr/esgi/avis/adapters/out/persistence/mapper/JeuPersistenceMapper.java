package fr.esgi.avis.adapters.out.persistence.mapper;

import fr.esgi.avis.adapters.out.persistence.entity.*;
import fr.esgi.avis.domain.model.*;

import java.util.Collections;

public final class JeuPersistenceMapper {

    private JeuPersistenceMapper() {}

    public static Jeu toDomain(JeuJpaEntity e) {
        if (e == null) return null;
        Jeu jeu = new Jeu();
        jeu.setId(e.getId());
        jeu.setNom(e.getNom());
        jeu.setDescription(e.getDescription());
        jeu.setDateSortie(e.getDateSortie());
        jeu.setPrix(e.getPrix());
        jeu.setImage(e.getImage());
        jeu.setGenre(e.getGenre() == null ? null :
                Genre.builder().id(e.getGenre().getId()).nom(e.getGenre().getNom()).build());
        jeu.setEditeur(e.getEditeur() == null ? null :
                Editeur.builder().id(e.getEditeur().getId()).nom(e.getEditeur().getNom()).build());
        jeu.setClassification(e.getClassification() == null ? null :
                Classification.builder()
                        .id(e.getClassification().getId())
                        .nom(e.getClassification().getNom())
                        .couleurRGB(e.getClassification().getCouleurRGB())
                        .build());
        jeu.setPlateformes(e.getPlateformes() == null ? Collections.emptyList() :
                e.getPlateformes().stream().map(p ->
                        Plateforme.builder().id(p.getId()).nom(p.getNom())
                                .dateDeSortie(p.getDateDeSortie()).build()
                ).toList());
        return jeu;
    }

    public static JeuJpaEntity toEntity(Jeu j) {
        if (j == null) return null;
        JeuJpaEntity e = new JeuJpaEntity();
        e.setId(j.getId());
        e.setNom(j.getNom());
        e.setDescription(j.getDescription());
        e.setDateSortie(j.getDateSortie());
        e.setPrix(j.getPrix());
        e.setImage(j.getImage());
        // Proxies FK — seul l'ID est renseigné
        if (j.getGenre() != null && j.getGenre().getId() != null) {
            GenreJpaEntity g = new GenreJpaEntity();
            g.setId(j.getGenre().getId());
            e.setGenre(g);
        }
        if (j.getEditeur() != null && j.getEditeur().getId() != null) {
            EditeurJpaEntity ed = new EditeurJpaEntity();
            ed.setId(j.getEditeur().getId());
            e.setEditeur(ed);
        }
        if (j.getClassification() != null && j.getClassification().getId() != null) {
            ClassificationJpaEntity c = new ClassificationJpaEntity();
            c.setId(j.getClassification().getId());
            e.setClassification(c);
        }
        e.setPlateformes(j.getPlateformes() == null ? Collections.emptyList() :
                j.getPlateformes().stream()
                        .filter(p -> p.getId() != null)
                        .map(p -> { PlateformeJpaEntity pe = new PlateformeJpaEntity();
                                    pe.setId(p.getId()); return pe; })
                        .toList());
        return e;
    }
}
