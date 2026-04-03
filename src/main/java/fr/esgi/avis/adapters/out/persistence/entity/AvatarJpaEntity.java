package fr.esgi.avis.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "avatars")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AvatarJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String nom;
}
