package fr.esgi.avis.adapters.in.web.dto.response;

import fr.esgi.avis.domain.model.StatutAvis;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class AvisResponse {
    private Long id;
    private String description;
    private Float note;
    private LocalDateTime dateDEnvoi;
    private StatutAvis statut;
    private Long jeuId;
    private Long joueurId;
    private Long moderateurId;
    private String raisonRejet;
}
