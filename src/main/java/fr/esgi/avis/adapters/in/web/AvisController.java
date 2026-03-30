package fr.esgi.avis.adapters.in.web;

import fr.esgi.avis.adapters.in.web.dto.request.RedigerAvisRequest;
import fr.esgi.avis.adapters.in.web.dto.response.AvisResponse;
import fr.esgi.avis.adapters.in.web.mapper.AvisWebMapper;
import fr.esgi.avis.application.ports.in.AvisUseCase;
import fr.esgi.avis.domain.exception.AvisInvalideException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// [rôle de la classe] Controleur REST exposant la consultation, la redaction et la moderation des avis.
@RestController
@RequestMapping("/api/avis")
public class AvisController {

    private final AvisUseCase avisUseCase;

    public AvisController(AvisUseCase avisUseCase) {
        this.avisUseCase = avisUseCase;
    }

    @GetMapping("/jeu/{jeuId}")
    public List<AvisResponse> listerParJeu(@PathVariable Long jeuId) {
        return avisUseCase.listerParJeu(jeuId).stream().map(AvisWebMapper::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('JOUEUR')")
    public AvisResponse rediger(
            @Valid @RequestBody RedigerAvisRequest request,
            @RequestHeader("X-Joueur-Id") Long joueurId
    ) {
        try {
            return AvisWebMapper.toResponse(avisUseCase.rediger(AvisWebMapper.toDomain(request, joueurId)));
        } catch (AvisInvalideException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }

    @PutMapping("/{id}/moderer")
    @PreAuthorize("hasRole('MODERATEUR')")
    public AvisResponse moderer(
            @PathVariable Long id,
            @RequestParam boolean approuver,
            @RequestParam(required = false) String raisonRejet
    ) {
        try {
            return AvisWebMapper.toResponse(avisUseCase.moderer(id, approuver, raisonRejet));
        } catch (AvisInvalideException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }
}

