package fr.esgi.avis.adapters.in.web;

import fr.esgi.avis.adapters.in.web.dto.request.ModifierJoueurRequest;
import fr.esgi.avis.adapters.in.web.dto.response.JoueurResponse;
import fr.esgi.avis.adapters.in.web.dto.response.ModerateurResponse;
import fr.esgi.avis.application.ports.in.UtilisateurUseCase;
import fr.esgi.avis.domain.exception.AccesInterditException;
import fr.esgi.avis.domain.model.Joueur;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurUseCase utilisateurUseCase;

    public UtilisateurController(UtilisateurUseCase utilisateurUseCase) {
        this.utilisateurUseCase = utilisateurUseCase;
    }

    // ── Joueurs ────────────────────────────────────────────────────────────

    @GetMapping("/joueurs")
    @PreAuthorize("hasRole('MODERATEUR')")
    public List<JoueurResponse> listerJoueurs() {
        return utilisateurUseCase.listerJoueurs()
                .stream().map(JoueurResponse::from).toList();
    }

    @GetMapping("/joueurs/{id}")
    @PreAuthorize("hasRole('MODERATEUR') or (hasRole('JOUEUR') and #id == authentication.principal)")
    public JoueurResponse trouverJoueurParId(@PathVariable Long id) {
        try {
            return JoueurResponse.from(utilisateurUseCase.trouverJoueurParId(id));
        } catch (AccesInterditException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping("/joueurs/{id}")
    @PreAuthorize("hasRole('JOUEUR')")
    public JoueurResponse modifierJoueur(@PathVariable Long id,
                                         @Valid @RequestBody ModifierJoueurRequest request) {
        try {
            Joueur joueur = new Joueur(null, request.getPseudo(), request.getEmail(),
                    request.getMotDePasse(), request.getDateDeNaissance(), null);
            return JoueurResponse.from(utilisateurUseCase.modifierJoueur(id, joueur));
        } catch (AccesInterditException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/joueurs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MODERATEUR')")
    public void supprimerJoueur(@PathVariable Long id) {
        try {
            utilisateurUseCase.supprimerJoueur(id);
        } catch (AccesInterditException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // ── Modérateurs ────────────────────────────────────────────────────────

    @GetMapping("/moderateurs")
    @PreAuthorize("hasRole('MODERATEUR')")
    public List<ModerateurResponse> listerModerateurs() {
        return utilisateurUseCase.listerModerateurs()
                .stream().map(ModerateurResponse::from).toList();
    }

    @GetMapping("/moderateurs/{id}")
    @PreAuthorize("hasRole('MODERATEUR')")
    public ModerateurResponse trouverModerateurParId(@PathVariable Long id) {
        try {
            return ModerateurResponse.from(utilisateurUseCase.trouverModerateurParId(id));
        } catch (AccesInterditException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}

