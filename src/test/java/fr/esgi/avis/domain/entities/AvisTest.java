package fr.esgi.avis.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

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
		Moderateur moderateur = Moderateur.builder()
				.id(2L)
				.pseudo("mod")
				.email("mod@example.com")
				.motDePasse("secret")
				.numeroDeTelephone("0600000000")
				.build();
		Avis avis = Avis.builder()
				.id(1L)
				.description("A corriger")
				.note(7.0F)
				.dateDEnvoi(LocalDateTime.now())
				.statut(StatutAvis.REJETE)
				.moderateur(moderateur)
				.raisonRejet("Trop court")
				.build();

		avis.corriger();

		assertEquals(StatutAvis.EN_ATTENTE, avis.getStatut());
		assertNull(avis.getRaisonRejet());
		assertNull(avis.getModerateur());
	}

	@Test
	void noteHorsBornesDoitLeverException() {
		Avis avis = Avis.builder()
				.id(1L)
				.description("Bad note")
				.note(10.5F)
				.dateDEnvoi(LocalDateTime.now())
				.statut(StatutAvis.EN_ATTENTE)
				.build();

		assertThrows(AvisInvalideException.class, avis::prendreEnCharge);
	}

	private Avis creerAvis(StatutAvis statut) {
		return Avis.builder()
				.id(1L)
				.description("Tres bon jeu")
				.note(8.5F)
				.dateDEnvoi(LocalDateTime.now())
				.statut(statut)
				.build();
	}
}

