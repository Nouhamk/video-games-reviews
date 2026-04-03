package fr.esgi.avis.adapters.out.persistence.entity;

import fr.esgi.avis.domain.model.StatutAvis;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "avis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvisJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
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

    @Column(columnDefinition = "TEXT")
    private String raisonRejet;
}
