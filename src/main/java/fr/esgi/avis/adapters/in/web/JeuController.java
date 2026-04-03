package fr.esgi.avis.adapters.in.web;

import fr.esgi.avis.adapters.in.web.dto.request.AjouterJeuRequest;
import fr.esgi.avis.adapters.in.web.dto.response.JeuResponse;
import fr.esgi.avis.adapters.in.web.mapper.JeuWebMapper;
import fr.esgi.avis.application.ports.in.JeuUseCase;
import fr.esgi.avis.domain.exception.JeuNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        } catch (JeuNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MODERATEUR')")
    public JeuResponse ajouter(@Valid @RequestBody AjouterJeuRequest request) {
        return JeuWebMapper.toResponse(jeuUseCase.ajouter(JeuWebMapper.toDomain(request)));
    }
}
