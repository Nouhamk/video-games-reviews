package fr.esgi.avis.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "joueurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoueurJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private String email;
    private String motDePasse;
    private LocalDate dateDeNaissance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private AvatarJpaEntity avatar;
}
