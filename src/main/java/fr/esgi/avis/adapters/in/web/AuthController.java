package fr.esgi.avis.adapters.in.web;

import fr.esgi.avis.adapters.in.web.dto.request.InscriptionRequest;
import fr.esgi.avis.adapters.in.web.dto.request.LoginRequest;
import fr.esgi.avis.adapters.in.web.dto.response.TokenResponse;
import fr.esgi.avis.application.ports.in.AuthUseCase;
import fr.esgi.avis.domain.exception.AccesInterditException;
import fr.esgi.avis.domain.model.Avatar;
import fr.esgi.avis.domain.model.Joueur;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// [rôle de la classe] Controleur REST gerant l'inscription et la connexion des joueurs.
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/inscription")
    @ResponseStatus(HttpStatus.CREATED)
    public Joueur inscrire(@Valid @RequestBody InscriptionRequest request) {
        Joueur joueur = new Joueur(
                null,
                request.getPseudo(),
                request.getEmail(),
                request.getMotDePasse(),
                request.getDateDeNaissance(),
                Avatar.builder().id(request.getAvatarId()).build()
        );
        return authUseCase.inscrire(joueur);
    }

    @PostMapping("/connexion")
    public TokenResponse connecter(@Valid @RequestBody LoginRequest request) {
        try {
            return TokenResponse.builder()
                    .token(authUseCase.connecter(request.getEmail(), request.getMotDePasse()))
                    .build();
        } catch (AccesInterditException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage(), exception);
        }
    }
}

