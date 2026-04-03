package fr.esgi.avis.adapters.in.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class TokenResponse {
    private String token;
}
