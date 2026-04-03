package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.out.JoueurRepositoryPort;
import fr.esgi.avis.application.ports.out.ModerateurRepositoryPort;
import fr.esgi.avis.domain.exception.AccesInterditException;
import fr.esgi.avis.domain.model.Joueur;
import fr.esgi.avis.domain.model.Moderateur;
import fr.esgi.avis.infrastructure.config.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthInteractorTest {

    @Mock JoueurRepositoryPort     joueurRepository;
    @Mock ModerateurRepositoryPort moderateurRepository;
    @Mock PasswordEncoder          passwordEncoder;
    @Mock JwtTokenProvider         jwtTokenProvider;
    @InjectMocks AuthInteractor    authInteractor;

    @Test @DisplayName("inscrire() hache le mot de passe")
    void inscrireHasheMotDePasse() {
        Joueur j = new Joueur(null, "Alice", "alice@test.com", "plain", null, null);
        given(passwordEncoder.encode("plain")).willReturn("$hashed");
        given(joueurRepository.save(any())).willAnswer(inv -> inv.getArgument(0));
        assertThat(authInteractor.inscrire(j).getMotDePasse()).isEqualTo("$hashed");
    }

    @Test @DisplayName("connecter() retourne un token pour joueur valide")
    void connecterJoueurValide() {
        Joueur j = new Joueur(1L, "Alice", "alice@test.com", "$hashed", null, null);
        given(joueurRepository.findByEmail("alice@test.com")).willReturn(Optional.of(j));
        given(passwordEncoder.matches("plain", "$hashed")).willReturn(true);
        given(jwtTokenProvider.generateToken("alice@test.com", "JOUEUR")).willReturn("jwt");
        assertThat(authInteractor.connecter("alice@test.com", "plain")).isEqualTo("jwt");
    }

    @Test @DisplayName("connecter() retourne un token pour modérateur valide")
    void connecterModerateurValide() {
        Moderateur m = new Moderateur(1L, "Bob", "bob@test.com", "$hashed", "0600000000");
        given(joueurRepository.findByEmail("bob@test.com")).willReturn(Optional.empty());
        given(moderateurRepository.findByEmail("bob@test.com")).willReturn(Optional.of(m));
        given(passwordEncoder.matches("plain", "$hashed")).willReturn(true);
        given(jwtTokenProvider.generateToken("bob@test.com", "MODERATEUR")).willReturn("jwt_mod");
        assertThat(authInteractor.connecter("bob@test.com", "plain")).isEqualTo("jwt_mod");
    }

    @Test @DisplayName("connecter() lève AccesInterditException si email inconnu")
    void connecterEmailInconnu() {
        given(joueurRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(moderateurRepository.findByEmail(anyString())).willReturn(Optional.empty());
        assertThatThrownBy(() -> authInteractor.connecter("x@x.com", "x"))
                .isInstanceOf(AccesInterditException.class);
    }

    @Test @DisplayName("connecter() lève AccesInterditException si mauvais mot de passe")
    void connecterMauvaisMotDePasse() {
        Joueur j = new Joueur(1L, "Alice", "alice@test.com", "$hashed", null, null);
        given(joueurRepository.findByEmail("alice@test.com")).willReturn(Optional.of(j));
        given(passwordEncoder.matches("wrong", "$hashed")).willReturn(false);
        assertThatThrownBy(() -> authInteractor.connecter("alice@test.com", "wrong"))
                .isInstanceOf(AccesInterditException.class);
    }
}
