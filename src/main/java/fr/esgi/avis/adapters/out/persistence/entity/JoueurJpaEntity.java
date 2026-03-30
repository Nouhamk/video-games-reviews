package fr.esgi.avis.adapters.out.persistence.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Entite JPA representant un joueur.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "joueurs")
public class JoueurJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private String email;
    private String motDePasse;
    private LocalDate dateDeNaissance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private AvatarJpaEntity avatar;

    @OneToMany(mappedBy = "joueur", fetch = FetchType.LAZY)
    private List<AvisJpaEntity> avis = new ArrayList<>();
}




