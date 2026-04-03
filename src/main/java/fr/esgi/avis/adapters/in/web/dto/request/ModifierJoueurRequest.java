package fr.esgi.avis.adapters.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifierJoueurRequest {

    @NotBlank
    private String pseudo;

    @NotBlank
    @Email
    private String email;

    private String motDePasse; // optionnel — si vide, conserve l'existant

    private LocalDate dateDeNaissance;
}

