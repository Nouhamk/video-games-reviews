package fr.esgi.avis.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.esgi.avis.domain.exception.AvisInvalideException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

// [rôle de la classe] Tests unitaires du comportement métier d'un avis.
class AvisTest {

    @Test
    void prendreEnChargeDoitPasserEnModerationDepuisEnAttente() {
        Avis avis = creerAvis(StatutAvis.EN_ATTENTE);

        avis.prendreEnCharge();

        assertEquals(StatutAvis.EN_MODERATION, avis.getStatut());
        assertNull(avis.getRaisonRejet());
    }

    @Test
    void approuverDoitEchouerSiAvisPasEnModeration() {
        Avis avis = creerAvis(StatutAvis.EN_ATTENTE);

        assertThrows(AvisInvalideException.class, avis::approuver);
    }

    @Test
    void rejeterDoitExigerUneRaison() {
        Avis avis = creerAvis(StatutAvis.EN_MODERATION);

        assertThrows(AvisInvalideException.class, () -> avis.rejeter(" "));
    }

    @Test
    void corrigerDoitRevenirEnAttenteEtNettoyerInfosModeration() {
        Moderateur moderateur = new Moderateur(2L, "mod", "mod@example.com", "secret", "0600000000");
        Avis avis = new Avis(
                1L,
                "A corriger",
                7.0F,
                LocalDateTime.now(),
                StatutAvis.REJETE,
                null,
                null,
                moderateur,
                "Trop court"
        );

        avis.corriger();

        assertEquals(StatutAvis.EN_ATTENTE, avis.getStatut());
        assertNull(avis.getRaisonRejet());
        assertNull(avis.getModerateur());
    }

    @Test
    void noteHorsBornesDoitLeverException() {
        LocalDateTime dateDEnvoi = LocalDateTime.now();

        assertThrows(
                AvisInvalideException.class,
                () -> new Avis(1L, "Bad note", 10.5F, dateDEnvoi, StatutAvis.EN_ATTENTE, null, null, null, null)
        );
    }

    private Avis creerAvis(StatutAvis statut) {
        return new Avis(1L, "Tres bon jeu", 8.5F, LocalDateTime.now(), statut, null, null, null, null);
    }
}

