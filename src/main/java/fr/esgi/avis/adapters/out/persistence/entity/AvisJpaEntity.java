package fr.esgi.avis.adapters.out.persistence.entity;

import fr.esgi.avis.domain.model.StatutAvis;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Entite JPA representant un avis joueur sur un jeu.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avis")
public class AvisJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Float note;
    private LocalDateTime dateDEnvoi;

    @Enumerated(EnumType.STRING)
    private StatutAvis statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jeu_id")
    private JeuJpaEntity jeu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "joueur_id")
    private JoueurJpaEntity joueur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderateur_id")
    private ModerateurJpaEntity moderateur;

    private String raisonRejet;
}

