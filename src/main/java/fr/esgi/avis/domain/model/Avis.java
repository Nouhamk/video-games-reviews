package fr.esgi.avis.domain.model;

import fr.esgi.avis.domain.exception.AvisInvalideException;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Modele metier representant un avis et ses transitions d'etat.
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

    public Avis(
            Long id,
            String description,
            Float note,
            LocalDateTime dateDEnvoi,
            StatutAvis statut,
            Jeu jeu,
            Joueur joueur,
            Moderateur moderateur,
            String raisonRejet
    ) {
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
        if (note == null || note < 0.0f || note > 10.0f) {
            throw new AvisInvalideException("La note doit etre comprise entre 0 et 10.");
        }
        this.note = note;
    }

    public void prendreEnCharge() {
        verifierStatut(StatutAvis.EN_ATTENTE, "Un avis ne peut etre pris en charge que depuis EN_ATTENTE.");
        this.statut = StatutAvis.EN_MODERATION;
    }

    public void approuver() {
        verifierStatut(StatutAvis.EN_MODERATION, "Un avis ne peut etre approuve que depuis EN_MODERATION.");
        this.statut = StatutAvis.APPROUVE;
        this.raisonRejet = null;
    }

    public void rejeter(String raison) {
        verifierStatut(StatutAvis.EN_MODERATION, "Un avis ne peut etre rejete que depuis EN_MODERATION.");
        if (raison == null || raison.isBlank()) {
            throw new AvisInvalideException("La raison du rejet est obligatoire.");
        }
        this.statut = StatutAvis.REJETE;
        this.raisonRejet = raison;
    }

    public void corriger() {
        verifierStatut(StatutAvis.REJETE, "Un avis ne peut etre corrige que depuis REJETE.");
        this.statut = StatutAvis.EN_ATTENTE;
        this.raisonRejet = null;
        this.moderateur = null;
    }

    public void supprimer() {
        if (this.statut != StatutAvis.APPROUVE && this.statut != StatutAvis.EN_ATTENTE) {
            throw new AvisInvalideException("Un avis ne peut etre supprime que depuis APPROUVE ou EN_ATTENTE.");
        }
        this.statut = StatutAvis.SUPPRIME;
    }

    private void verifierStatut(StatutAvis statutAttendu, String message) {
        if (this.statut != statutAttendu) {
            throw new AvisInvalideException(message);
        }
    }
}


