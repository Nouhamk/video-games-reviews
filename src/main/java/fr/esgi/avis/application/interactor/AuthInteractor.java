package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.in.AuthUseCase;
import fr.esgi.avis.application.ports.out.JoueurRepositoryPort;
import fr.esgi.avis.domain.exception.AccesInterditException;
import fr.esgi.avis.domain.model.Joueur;
import fr.esgi.avis.infrastructure.config.JwtTokenProvider;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;

// [rôle de la classe] Interactor applicatif des cas d'usage d'inscription et de connexion.
public class AuthInteractor implements AuthUseCase {

    private final JoueurRepositoryPort joueurRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthInteractor(JoueurRepositoryPort joueurRepository, JwtTokenProvider jwtTokenProvider) {
        this.joueurRepository = joueurRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public Joueur inscrire(Joueur joueur) {
        return joueurRepository.save(joueur);
    }

    @Override
    @Transactional(readOnly = true)
    public String connecter(String email, String motDePasse) {
        Joueur joueur = joueurRepository.findByEmail(email)
                .orElseThrow(() -> new AccesInterditException("Identifiants invalides."));

        if (!Objects.equals(joueur.getMotDePasse(), motDePasse)) {
            throw new AccesInterditException("Identifiants invalides.");
        }

        return jwtTokenProvider.generateToken(joueur.getEmail(), "JOUEUR");
    }
}

