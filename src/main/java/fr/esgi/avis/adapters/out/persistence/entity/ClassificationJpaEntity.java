package fr.esgi.avis.adapters.out.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Entite JPA de classification d'un jeu.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classifications")
public class ClassificationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String couleurRGB;

    @OneToMany(mappedBy = "classification", fetch = FetchType.LAZY)
    private List<JeuJpaEntity> jeux = new ArrayList<>();
}

