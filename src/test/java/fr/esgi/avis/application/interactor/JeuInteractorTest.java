package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.out.JeuRepositoryPort;
import fr.esgi.avis.domain.exception.JeuNotFoundException;
import fr.esgi.avis.domain.model.Jeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JeuInteractorTest {

    @Mock JeuRepositoryPort jeuRepository;
    @InjectMocks JeuInteractor jeuInteractor;

    private Jeu jeu;

    @BeforeEach
    void setUp() {
        jeu = Jeu.builder().id(1L).nom("Elden Ring")
                .dateSortie(LocalDate.of(2022, 2, 25)).prix(59.99f).build();
    }

    @Test @DisplayName("ajouter() sauvegarde et retourne le jeu")
    void ajouter() {
        given(jeuRepository.save(any())).willReturn(jeu);
        Jeu result = jeuInteractor.ajouter(jeu);
        assertThat(result.getNom()).isEqualTo("Elden Ring");
        verify(jeuRepository).save(jeu);
    }

    @Test @DisplayName("listerTous() retourne la liste complète")
    void listerTous() {
        given(jeuRepository.findAll()).willReturn(List.of(jeu));
        assertThat(jeuInteractor.listerTous()).hasSize(1);
    }

    @Test @DisplayName("trouverParId() retourne le jeu existant")
    void trouverParIdExiste() {
        given(jeuRepository.findById(1L)).willReturn(Optional.of(jeu));
        assertThat(jeuInteractor.trouverParId(1L).getId()).isEqualTo(1L);
    }

    @Test @DisplayName("trouverParId() lève JeuNotFoundException si absent")
    void trouverParIdAbsent() {
        given(jeuRepository.findById(99L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> jeuInteractor.trouverParId(99L))
                .isInstanceOf(JeuNotFoundException.class);
    }
}
