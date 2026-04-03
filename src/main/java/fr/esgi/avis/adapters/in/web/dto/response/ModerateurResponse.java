package fr.esgi.avis.adapters.in.web.dto.response;

import fr.esgi.avis.domain.model.Moderateur;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModerateurResponse {

    private Long id;
    private String pseudo;
    private String email;
    private String numeroDeTelephone;

    public static ModerateurResponse from(Moderateur moderateur) {
        return ModerateurResponse.builder()
                .id(moderateur.getId())
                .pseudo(moderateur.getPseudo())
                .email(moderateur.getEmail())
                .numeroDeTelephone(moderateur.getNumeroDeTelephone())
                .build();
    }
}

