package fr.esgi.avis.adapters.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter @NoArgsConstructor @AllArgsConstructor
public class InscriptionRequest {
    @NotBlank private String pseudo;
    @Email @NotBlank private String email;
    @NotBlank private String motDePasse;
    @NotNull  private LocalDate dateDeNaissance;
    private Long avatarId;
}
