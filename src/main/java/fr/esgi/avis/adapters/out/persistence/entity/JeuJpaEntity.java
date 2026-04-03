package fr.esgi.avis.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jeux")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JeuJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private LocalDate dateSortie;
    private Float prix;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private GenreJpaEntity genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editeur_id")
    private EditeurJpaEntity editeur;

    @ManyToMany
    @JoinTable(
            name = "jeu_plateformes",
            joinColumns = @JoinColumn(name = "jeu_id"),
            inverseJoinColumns = @JoinColumn(name = "plateforme_id")
    )
    private List<PlateformeJpaEntity> plateformes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classification_id")
    private ClassificationJpaEntity classification;
}
