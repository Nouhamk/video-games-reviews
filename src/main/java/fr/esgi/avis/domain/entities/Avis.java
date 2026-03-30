package fr.esgi.avis.domain.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Avis {

	private Long id;
	private String description;
	private Float note;
	private LocalDateTime dateDEnvoi;
	@Builder.Default
	private StatutAvis statut = StatutAvis.EN_ATTENTE;
	private Jeu jeu;
	private Joueur joueur;
	private Moderateur moderateur;
	private String raisonRejet;

	public void prendreEnCharge() {
		verifierNote();
		verifierStatutCourant(StatutAvis.EN_ATTENTE, "Un avis ne peut être pris en charge que s'il est en attente.");
		this.statut = StatutAvis.EN_MODERATION;
		this.raisonRejet = null;
	}

	public void approuver() {
		verifierNote();
		verifierStatutCourant(StatutAvis.EN_MODERATION,
				"Un avis ne peut être approuvé que s'il est en cours de modération.");
		this.statut = StatutAvis.APPROUVE;
		this.raisonRejet = null;
	}

	public void rejeter(String raison) {
		verifierNote();
		verifierStatutCourant(StatutAvis.EN_MODERATION,
				"Un avis ne peut être rejeté que s'il est en cours de modération.");
		if (raison == null || raison.isBlank()) {
			throw new AvisInvalideException("La raison du rejet est obligatoire.");
		}
		this.statut = StatutAvis.REJETE;
		this.raisonRejet = raison;
	}

	public void corriger() {
		verifierNote();
		verifierStatutCourant(StatutAvis.REJETE, "Seul un avis rejeté peut être corrigé.");
		this.statut = StatutAvis.EN_ATTENTE;
		this.raisonRejet = null;
		this.moderateur = null;
	}

	public void supprimer() {
		verifierNote();
		if (this.statut == StatutAvis.SUPPRIME) {
			throw new AvisInvalideException("Un avis déjà supprimé ne peut pas être supprimé à nouveau.");
		}
		this.statut = StatutAvis.SUPPRIME;
	}

	private void verifierStatutCourant(StatutAvis statutAttendu, String message) {
		if (this.statut != statutAttendu) {
			throw new AvisInvalideException(message);
		}
	}

	private void verifierNote() {
		if (this.note == null || this.note < 0.0F || this.note > 10.0F) {
			throw new AvisInvalideException("La note doit être comprise entre 0.0 et 10.0.");
		}
	}
}

