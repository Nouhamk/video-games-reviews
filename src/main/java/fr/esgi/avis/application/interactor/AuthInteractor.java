package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.in.AuthUseCase;
import fr.esgi.avis.application.ports.out.JoueurRepositoryPort;
import fr.esgi.avis.application.ports.out.ModerateurRepositoryPort;
import fr.esgi.avis.domain.exception.AccesInterditException;
import fr.esgi.avis.domain.model.Joueur;
import fr.esgi.avis.infrastructure.config.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthInteractor implements AuthUseCase {

    private final JoueurRepositoryPort     joueurRepository;
    private final ModerateurRepositoryPort moderateurRepository;
    private final PasswordEncoder          passwordEncoder;
    private final JwtTokenProvider         jwtTokenProvider;

    public AuthInteractor(JoueurRepositoryPort joueurRepository,
                          ModerateurRepositoryPort moderateurRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.joueurRepository     = joueurRepository;
        this.moderateurRepository = moderateurRepository;
        this.passwordEncoder      = passwordEncoder;
        this.jwtTokenProvider     = jwtTokenProvider;
    }

    @Override
    public Joueur inscrire(Joueur joueur) {
        String hash = passwordEncoder.encode(joueur.getMotDePasse());
        Joueur avecHash = new Joueur(
                joueur.getId(), joueur.getPseudo(), joueur.getEmail(),
                hash, joueur.getDateDeNaissance(), joueur.getAvatar());
        return joueurRepository.save(avecHash);
    }

    @Override
    public String connecter(String email, String motDePasse) {
        // Cherche dans les joueurs
        var joueurOpt = joueurRepository.findByEmail(email);
        if (joueurOpt.isPresent()) {
            Joueur j = joueurOpt.get();
            if (!passwordEncoder.matches(motDePasse, j.getMotDePasse())) {
                throw new AccesInterditException("Identifiants invalides.");
            }
            return jwtTokenProvider.generateToken(j.getEmail(), "JOUEUR");
        }
        // Cherche dans les modérateurs
        var modOpt = moderateurRepository.findByEmail(email);
        if (modOpt.isPresent()) {
            var m = modOpt.get();
            if (!passwordEncoder.matches(motDePasse, m.getMotDePasse())) {
                throw new AccesInterditException("Identifiants invalides.");
            }
            return jwtTokenProvider.generateToken(m.getEmail(), "MODERATEUR");
        }
        throw new AccesInterditException("Identifiants invalides.");
    }
}
