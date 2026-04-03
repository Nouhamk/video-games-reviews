package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.in.UtilisateurUseCase;
import fr.esgi.avis.application.ports.out.JoueurRepositoryPort;
import fr.esgi.avis.application.ports.out.ModerateurRepositoryPort;
import fr.esgi.avis.domain.exception.AccesInterditException;
import fr.esgi.avis.domain.model.Joueur;
import fr.esgi.avis.domain.model.Moderateur;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UtilisateurInteractor implements UtilisateurUseCase {

    private final JoueurRepositoryPort     joueurRepository;
    private final ModerateurRepositoryPort moderateurRepository;
    private final PasswordEncoder          passwordEncoder;

    public UtilisateurInteractor(JoueurRepositoryPort joueurRepository,
                                 ModerateurRepositoryPort moderateurRepository,
                                 PasswordEncoder passwordEncoder) {
        this.joueurRepository     = joueurRepository;
        this.moderateurRepository = moderateurRepository;
        this.passwordEncoder      = passwordEncoder;
    }

    @Override
    public List<Joueur> listerJoueurs() {
        return joueurRepository.findAll();
    }

    @Override
    public Joueur trouverJoueurParId(Long id) {
        return joueurRepository.findById(id)
                .orElseThrow(() -> new AccesInterditException("Joueur introuvable avec l'id : " + id));
    }

    @Override
    public Joueur modifierJoueur(Long id, Joueur joueur) {
        trouverJoueurParId(id); // vérifie existence
        joueur.setId(id);
        if (joueur.getMotDePasse() != null && !joueur.getMotDePasse().isBlank()) {
            joueur.setMotDePasse(passwordEncoder.encode(joueur.getMotDePasse()));
        } else {
            // conserver le mot de passe existant
            String mdpExistant = trouverJoueurParId(id).getMotDePasse();
            joueur.setMotDePasse(mdpExistant);
        }
        return joueurRepository.save(joueur);
    }

    @Override
    public void supprimerJoueur(Long id) {
        trouverJoueurParId(id); // vérifie existence
        joueurRepository.deleteById(id);
    }

    @Override
    public List<Moderateur> listerModerateurs() {
        return moderateurRepository.findAll();
    }

    @Override
    public Moderateur trouverModerateurParId(Long id) {
        return moderateurRepository.findById(id)
                .orElseThrow(() -> new AccesInterditException("Modérateur introuvable avec l'id : " + id));
    }
}

