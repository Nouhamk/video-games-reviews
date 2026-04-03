package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.out.AvisRepositoryPort;
import fr.esgi.avis.application.ports.out.JeuRepositoryPort;
import fr.esgi.avis.domain.exception.AvisInvalideException;
import fr.esgi.avis.domain.exception.JeuNotFoundException;
import fr.esgi.avis.domain.model.Avis;
import fr.esgi.avis.domain.model.Jeu;
import fr.esgi.avis.domain.model.Joueur;
import fr.esgi.avis.domain.model.StatutAvis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AvisInteractorTest {

    @Mock AvisRepositoryPort avisRepository;
    @Mock JeuRepositoryPort  jeuRepository;
    @InjectMocks AvisInteractor avisInteractor;

    private Jeu jeu;
    private Joueur joueur;

    @BeforeEach
    void setUp() {
        jeu    = Jeu.builder().id(1L).nom("Elden Ring").build();
        joueur = new Joueur(2L, "Alice", "alice@test.com", "hash", null, null);
    }

    private Avis avisValide() {
        return new Avis(null, "Super jeu", 9.0f,
                LocalDateTime.now(), StatutAvis.EN_ATTENTE, jeu, joueur, null, null);
    }

    @Test @DisplayName("rediger() sauvegarde un avis valide")
    void redigerValide() {
        given(jeuRepository.findById(1L)).willReturn(Optional.of(jeu));
        given(avisRepository.save(any())).willAnswer(inv -> inv.getArgument(0));
        Avis result = avisInteractor.rediger(avisValide());
        assertThat(result.getNote()).isEqualTo(9.0f);
    }

    @Test @DisplayName("rediger() lève exception si jeu inconnu")
    void redigerJeuInconnu() {
        given(jeuRepository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> avisInteractor.rediger(avisValide()))
                .isInstanceOf(JeuNotFoundException.class);
    }

    @Test @DisplayName("rediger() lève exception si pas de joueur")
    void redigerSansJoueur() {
        Avis sansJoueur = new Avis(null, "...", 5.0f, LocalDateTime.now(),
                StatutAvis.EN_ATTENTE, jeu, null, null, null);
        assertThatThrownBy(() -> avisInteractor.rediger(sansJoueur))
                .isInstanceOf(AvisInvalideException.class);
    }

    @Test @DisplayName("moderer() avec approuver=true passe à APPROUVE")
    void modererApprouve() {
        Avis enModeration = new Avis(1L, "Bien", 7.0f, LocalDateTime.now(),
                StatutAvis.EN_MODERATION, jeu, joueur, null, null);
        given(avisRepository.findById(1L)).willReturn(Optional.of(enModeration));
        given(avisRepository.save(any())).willAnswer(inv -> inv.getArgument(0));
        Avis result = avisInteractor.moderer(1L, true, null);
        assertThat(result.getStatut()).isEqualTo(StatutAvis.APPROUVE);
    }

    @Test @DisplayName("moderer() avec approuver=false passe à REJETE")
    void modererRejete() {
        Avis enModeration = new Avis(1L, "Bien", 7.0f, LocalDateTime.now(),
                StatutAvis.EN_MODERATION, jeu, joueur, null, null);
        given(avisRepository.findById(1L)).willReturn(Optional.of(enModeration));
        given(avisRepository.save(any())).willAnswer(inv -> inv.getArgument(0));
        Avis result = avisInteractor.moderer(1L, false, "Contenu inapproprié");
        assertThat(result.getStatut()).isEqualTo(StatutAvis.REJETE);
        assertThat(result.getRaisonRejet()).isEqualTo("Contenu inapproprié");
    }
}
