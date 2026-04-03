package fr.esgi.avis.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "editeurs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EditeurJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String nom;
    @OneToMany(mappedBy = "editeur") private List<JeuJpaEntity> jeux = new ArrayList<>();
}
