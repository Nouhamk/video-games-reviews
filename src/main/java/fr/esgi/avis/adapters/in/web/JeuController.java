package fr.esgi.avis.adapters.in.web;

import fr.esgi.avis.adapters.in.web.dto.request.AjouterJeuRequest;
import fr.esgi.avis.adapters.in.web.dto.response.JeuResponse;
import fr.esgi.avis.adapters.in.web.mapper.JeuWebMapper;
import fr.esgi.avis.application.ports.in.JeuUseCase;
import fr.esgi.avis.domain.exception.JeuNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// [rôle de la classe] Controleur REST exposant les operations publiques et protegees sur les jeux.
@RestController
@RequestMapping("/api/jeux")
public class JeuController {

    private final JeuUseCase jeuUseCase;

    public JeuController(JeuUseCase jeuUseCase) {
        this.jeuUseCase = jeuUseCase;
    }

    @GetMapping
    public List<JeuResponse> listerTous() {
        return jeuUseCase.listerTous().stream().map(JeuWebMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public JeuResponse trouverParId(@PathVariable Long id) {
        try {
            return JeuWebMapper.toResponse(jeuUseCase.trouverParId(id));
        } catch (JeuNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MODERATEUR')")
    public JeuResponse ajouter(@Valid @RequestBody AjouterJeuRequest request) {
        return JeuWebMapper.toResponse(jeuUseCase.ajouter(JeuWebMapper.toDomain(request)));
    }
}

