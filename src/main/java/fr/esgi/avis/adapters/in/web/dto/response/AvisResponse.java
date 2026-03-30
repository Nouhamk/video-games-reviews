package fr.esgi.avis.adapters.in.web.dto.response;

import fr.esgi.avis.domain.model.StatutAvis;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

// [rôle de la classe] DTO HTTP de sortie d'un avis.
@Getter
@Builder
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

