package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.in.JeuUseCase;
import fr.esgi.avis.application.ports.out.JeuRepositoryPort;
import fr.esgi.avis.domain.exception.JeuNotFoundException;
import fr.esgi.avis.domain.model.Jeu;

import java.util.List;

public class JeuInteractor implements JeuUseCase {

    private final JeuRepositoryPort jeuRepository;

    public JeuInteractor(JeuRepositoryPort jeuRepository) {
        this.jeuRepository = jeuRepository;
    }

    @Override
    public Jeu ajouter(Jeu jeu) {
        return jeuRepository.save(jeu);
    }

    @Override
    public List<Jeu> listerTous() {
        return jeuRepository.findAll();
    }

    @Override
    public Jeu trouverParId(Long id) {
        return jeuRepository.findById(id)
                .orElseThrow(() -> new JeuNotFoundException("Jeu introuvable avec l'id : " + id));
    }

    @Override
    public Jeu modifier(Long id, Jeu jeu) {
        trouverParId(id); // vérifie existence
        jeu.setId(id);
        return jeuRepository.save(jeu);
    }

    @Override
    public void supprimer(Long id) {
        trouverParId(id); // vérifie existence
        jeuRepository.deleteById(id);
    }
}
