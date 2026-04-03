package fr.esgi.avis.domain.model;

import fr.esgi.avis.domain.exception.AvisInvalideException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Avis {

    private Long id;
    private String description;
    private Float note;
    private LocalDateTime dateDEnvoi;
    private StatutAvis statut = StatutAvis.EN_ATTENTE;
    private Jeu jeu;
    private Joueur joueur;
    private Moderateur moderateur;
    private String raisonRejet;

    public Avis(Long id, String description, Float note, LocalDateTime dateDEnvoi,
                StatutAvis statut, Jeu jeu, Joueur joueur,
                Moderateur moderateur, String raisonRejet) {
        this.id = id;
        this.description = description;
        setNote(note);
        this.dateDEnvoi = dateDEnvoi;
        this.statut = statut == null ? StatutAvis.EN_ATTENTE : statut;
        this.jeu = jeu;
        this.joueur = joueur;
        this.moderateur = moderateur;
        this.raisonRejet = raisonRejet;
    }

    public void setNote(Float note) {
        if (note == null || note < 0f || note > 10f) {
            throw new AvisInvalideException("La note doit être comprise entre 0 et 10.");
        }
        this.note = note;
    }

    public void prendreEnCharge() {
        verifierStatut(StatutAvis.EN_ATTENTE,
                "Un avis ne peut être pris en charge que depuis EN_ATTENTE.");
        this.statut = StatutAvis.EN_MODERATION;
        this.raisonRejet = null;
    }

    public void approuver() {
        verifierStatut(StatutAvis.EN_MODERATION,
                "Un avis ne peut être approuvé que depuis EN_MODERATION.");
        this.statut = StatutAvis.APPROUVE;
        this.raisonRejet = null;
    }

    public void rejeter(String raison) {
        verifierStatut(StatutAvis.EN_MODERATION,
                "Un avis ne peut être rejeté que depuis EN_MODERATION.");
        if (raison == null || raison.isBlank()) {
            throw new AvisInvalideException("La raison du rejet est obligatoire.");
        }
        this.statut = StatutAvis.REJETE;
        this.raisonRejet = raison;
    }

    public void corriger() {
        verifierStatut(StatutAvis.REJETE,
                "Un avis ne peut être corrigé que depuis REJETE.");
        this.statut = StatutAvis.EN_ATTENTE;
        this.raisonRejet = null;
        this.moderateur = null;
    }

    public void supprimer() {
        if (this.statut == StatutAvis.SUPPRIME) {
            throw new AvisInvalideException("L'avis est déjà supprimé.");
        }
        this.statut = StatutAvis.SUPPRIME;
    }

    private void verifierStatut(StatutAvis attendu, String message) {
        if (this.statut != attendu) {
            throw new AvisInvalideException(message);
        }
    }
}
