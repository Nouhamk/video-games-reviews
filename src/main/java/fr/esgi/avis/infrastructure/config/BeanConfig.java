package fr.esgi.avis.infrastructure.config;

import fr.esgi.avis.application.interactor.AuthInteractor;
import fr.esgi.avis.application.interactor.AvisInteractor;
import fr.esgi.avis.application.interactor.JeuInteractor;
import fr.esgi.avis.application.interactor.UtilisateurInteractor;
import fr.esgi.avis.application.ports.in.AuthUseCase;
import fr.esgi.avis.application.ports.in.AvisUseCase;
import fr.esgi.avis.application.ports.in.JeuUseCase;
import fr.esgi.avis.application.ports.in.UtilisateurUseCase;
import fr.esgi.avis.application.ports.out.AvisRepositoryPort;
import fr.esgi.avis.application.ports.out.JeuRepositoryPort;
import fr.esgi.avis.application.ports.out.JoueurRepositoryPort;
import fr.esgi.avis.application.ports.out.ModerateurRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {    @Bean
    public JeuUseCase jeuUseCase(JeuRepositoryPort jeuRepositoryPort) {
        return new JeuInteractor(jeuRepositoryPort);
    }

    @Bean
    public AvisUseCase avisUseCase(AvisRepositoryPort avisRepositoryPort,
                                    JeuRepositoryPort jeuRepositoryPort) {
        return new AvisInteractor(avisRepositoryPort, jeuRepositoryPort);
    }

    @Bean
    public AuthUseCase authUseCase(JoueurRepositoryPort joueurRepositoryPort,
                                    ModerateurRepositoryPort moderateurRepositoryPort,
                                    PasswordEncoder passwordEncoder,
                                    JwtTokenProvider jwtTokenProvider) {
        return new AuthInteractor(joueurRepositoryPort, moderateurRepositoryPort,
                passwordEncoder, jwtTokenProvider);
    }

    @Bean
    public UtilisateurUseCase utilisateurUseCase(JoueurRepositoryPort joueurRepositoryPort,
                                                  ModerateurRepositoryPort moderateurRepositoryPort,
                                                  PasswordEncoder passwordEncoder) {
        return new UtilisateurInteractor(joueurRepositoryPort, moderateurRepositoryPort, passwordEncoder);
    }
}
