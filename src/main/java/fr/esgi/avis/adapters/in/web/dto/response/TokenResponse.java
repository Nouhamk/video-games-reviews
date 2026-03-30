package fr.esgi.avis.adapters.in.web.dto.response;

import lombok.Builder;
import lombok.Getter;

// [rôle de la classe] DTO HTTP de sortie contenant un JWT.
@Getter
@Builder
public class TokenResponse {
    private String token;
}

