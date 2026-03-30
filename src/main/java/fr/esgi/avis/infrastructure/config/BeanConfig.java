package fr.esgi.avis.infrastructure.config;

import fr.esgi.avis.application.interactor.AvisInteractor;
import fr.esgi.avis.application.interactor.AuthInteractor;
import fr.esgi.avis.application.interactor.JeuInteractor;
import fr.esgi.avis.application.ports.in.AvisUseCase;
import fr.esgi.avis.application.ports.in.AuthUseCase;
import fr.esgi.avis.application.ports.in.JeuUseCase;
import fr.esgi.avis.application.ports.out.AvisRepositoryPort;
import fr.esgi.avis.application.ports.out.JeuRepositoryPort;
import fr.esgi.avis.application.ports.out.JoueurRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// [rôle de la classe] Configuration de cablage manuel des interactors applicatifs.
@Configuration
public class BeanConfig {

    @Bean
    public JeuUseCase jeuUseCase(JeuRepositoryPort jeuRepositoryPort) {
        return new JeuInteractor(jeuRepositoryPort);
    }

    @Bean
    public AvisUseCase avisUseCase(AvisRepositoryPort avisRepositoryPort, JeuRepositoryPort jeuRepositoryPort) {
        return new AvisInteractor(avisRepositoryPort, jeuRepositoryPort);
    }

    @Bean
    public AuthUseCase authUseCase(JoueurRepositoryPort joueurRepositoryPort, JwtTokenProvider jwtTokenProvider) {
        return new AuthInteractor(joueurRepositoryPort, jwtTokenProvider);
    }
}

