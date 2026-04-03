package fr.esgi.avis.adapters.in.web;

import fr.esgi.avis.adapters.in.web.dto.request.RedigerAvisRequest;
import fr.esgi.avis.adapters.in.web.dto.response.AvisResponse;
import fr.esgi.avis.adapters.in.web.mapper.AvisWebMapper;
import fr.esgi.avis.application.ports.in.AvisUseCase;
import fr.esgi.avis.domain.exception.AccesInterditException;
import fr.esgi.avis.domain.exception.AvisInvalideException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @GetMapping("/{id}")
    public AvisResponse trouverParId(@PathVariable Long id) {
        try {
            return AvisWebMapper.toResponse(avisUseCase.trouverParId(id));
        } catch (AvisInvalideException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('JOUEUR')")
    public AvisResponse rediger(@Valid @RequestBody RedigerAvisRequest request,
                                 @RequestHeader("X-Joueur-Id") Long joueurId) {
        try {
            return AvisWebMapper.toResponse(avisUseCase.rediger(AvisWebMapper.toDomain(request, joueurId)));
        } catch (AvisInvalideException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/{id}/moderer")
    @PreAuthorize("hasRole('MODERATEUR')")
    public AvisResponse moderer(@PathVariable Long id,
                                 @RequestParam boolean approuver,
                                 @RequestParam(required = false) String raisonRejet) {
        try {
            return AvisWebMapper.toResponse(avisUseCase.moderer(id, approuver, raisonRejet));
        } catch (AvisInvalideException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('JOUEUR') or hasRole('MODERATEUR')")
    public void supprimer(@PathVariable Long id,
                          @RequestHeader(value = "X-Joueur-Id", required = false) Long joueurId) {
        try {
            avisUseCase.supprimer(id, joueurId);
        } catch (AvisInvalideException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (AccesInterditException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }
}
