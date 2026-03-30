package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.in.JeuUseCase;
import fr.esgi.avis.application.ports.out.JeuRepositoryPort;
import fr.esgi.avis.domain.exception.JeuNotFoundException;
import fr.esgi.avis.domain.model.Jeu;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

// [rôle de la classe] Interactor applicatif des cas d'usage de gestion des jeux.
public class JeuInteractor implements JeuUseCase {

    private final JeuRepositoryPort jeuRepository;

    public JeuInteractor(JeuRepositoryPort jeuRepository) {
        this.jeuRepository = jeuRepository;
    }

    @Override
    @Transactional
    public Jeu ajouter(Jeu jeu) {
        return jeuRepository.save(jeu);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Jeu> listerTous() {
        return jeuRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Jeu trouverParId(Long id) {
        return jeuRepository.findById(id)
                .orElseThrow(() -> new JeuNotFoundException("Jeu introuvable avec l'id: " + id));
    }
}

