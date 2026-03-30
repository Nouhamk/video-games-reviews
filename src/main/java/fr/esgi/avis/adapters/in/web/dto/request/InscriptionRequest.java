package fr.esgi.avis.adapters.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// [rôle de la classe] DTO HTTP d'inscription d'un joueur.
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionRequest {

    @NotBlank
    private String pseudo;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String motDePasse;

    @NotNull
    private LocalDate dateDeNaissance;

    @NotNull
    private Long avatarId;
}

