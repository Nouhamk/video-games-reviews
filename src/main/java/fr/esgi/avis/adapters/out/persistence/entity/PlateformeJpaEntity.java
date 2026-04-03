package fr.esgi.avis.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "plateformes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PlateformeJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String nom;
    private LocalDate dateDeSortie;
    @ManyToMany(mappedBy = "plateformes") private List<JeuJpaEntity> jeux = new ArrayList<>();
}
