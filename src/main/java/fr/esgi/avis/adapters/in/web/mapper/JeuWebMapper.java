package fr.esgi.avis.adapters.in.web.mapper;

import fr.esgi.avis.adapters.in.web.dto.request.AjouterJeuRequest;
import fr.esgi.avis.adapters.in.web.dto.response.JeuResponse;
import fr.esgi.avis.domain.model.*;

import java.util.Collections;

public final class JeuWebMapper {

    private JeuWebMapper() {}

    public static Jeu toDomain(AjouterJeuRequest req) {
        if (req == null) return null;
        Jeu jeu = new Jeu();
        jeu.setNom(req.getNom());
        jeu.setDescription(req.getDescription());
        jeu.setDateSortie(req.getDateSortie());
        jeu.setPrix(req.getPrix());
        jeu.setImage(req.getImage());
        jeu.setGenre(Genre.builder().id(req.getGenreId()).build());
        jeu.setEditeur(Editeur.builder().id(req.getEditeurId()).build());
        jeu.setClassification(Classification.builder().id(req.getClassificationId()).build());
        jeu.setPlateformes(req.getPlateformeIds() == null ? Collections.emptyList() :
                req.getPlateformeIds().stream()
                        .map(id -> Plateforme.builder().id(id).build())
                        .toList());
        return jeu;
    }

    public static JeuResponse toResponse(Jeu jeu) {
        if (jeu == null) return null;
        return JeuResponse.builder()
                .id(jeu.getId())
                .nom(jeu.getNom())
                .description(jeu.getDescription())
                .dateSortie(jeu.getDateSortie())
                .prix(jeu.getPrix())
                .image(jeu.getImage())
                .genreId(jeu.getGenre() != null ? jeu.getGenre().getId() : null)
                .editeurId(jeu.getEditeur() != null ? jeu.getEditeur().getId() : null)
                .classificationId(jeu.getClassification() != null ? jeu.getClassification().getId() : null)
                .plateformeIds(jeu.getPlateformes() == null ? Collections.emptyList() :
                        jeu.getPlateformes().stream().map(Plateforme::getId).toList())
                .build();
    }
}
