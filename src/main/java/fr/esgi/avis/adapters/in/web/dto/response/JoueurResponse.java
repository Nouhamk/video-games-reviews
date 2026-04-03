package fr.esgi.avis.adapters.in.web.dto.response;

import fr.esgi.avis.domain.model.Joueur;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class JoueurResponse {
    private Long id;
    private String pseudo;
    private String email;
    private LocalDate dateDeNaissance;

    public static JoueurResponse from(Joueur joueur) {
        return JoueurResponse.builder()
                .id(joueur.getId())
                .pseudo(joueur.getPseudo())
                .email(joueur.getEmail())
                .dateDeNaissance(joueur.getDateDeNaissance())
                .build();
    }
}

