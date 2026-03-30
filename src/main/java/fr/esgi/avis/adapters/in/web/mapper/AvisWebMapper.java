package fr.esgi.avis.adapters.in.web.mapper;

import fr.esgi.avis.adapters.in.web.dto.request.RedigerAvisRequest;
import fr.esgi.avis.adapters.in.web.dto.response.AvisResponse;
import fr.esgi.avis.domain.model.Avis;
import fr.esgi.avis.domain.model.Jeu;
import fr.esgi.avis.domain.model.Joueur;
import java.time.LocalDateTime;

// [rôle de la classe] Mapper manuel entre les DTO web et le domaine Avis.
public final class AvisWebMapper {

    private AvisWebMapper() {
    }

    public static Avis toDomain(RedigerAvisRequest req, Long joueurId) {
        if (req == null) {
            return null;
        }
        Avis avis = new Avis();
        avis.setDescription(req.getDescription());
        avis.setNote(req.getNote());
        avis.setDateDEnvoi(LocalDateTime.now());
        avis.setJeu(Jeu.builder().id(req.getJeuId()).build());
        avis.setJoueur(new Joueur(joueurId, null, null, null, null, null));
        return avis;
    }

    public static AvisResponse toResponse(Avis avis) {
        if (avis == null) {
            return null;
        }
        return AvisResponse.builder()
                .id(avis.getId())
                .description(avis.getDescription())
                .note(avis.getNote())
                .dateDEnvoi(avis.getDateDEnvoi())
                .statut(avis.getStatut())
                .jeuId(avis.getJeu() != null ? avis.getJeu().getId() : null)
                .joueurId(avis.getJoueur() != null ? avis.getJoueur().getId() : null)
                .moderateurId(avis.getModerateur() != null ? avis.getModerateur().getId() : null)
                .raisonRejet(avis.getRaisonRejet())
                .build();
    }
}

