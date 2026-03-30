package fr.esgi.avis.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Modele metier representant un joueur.
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Joueur extends Utilisateur {
    private LocalDate dateDeNaissance;
    private Avatar avatar;
    private List<Avis> avis = new ArrayList<>();

    public Joueur(
            Long id,
            String pseudo,
            String email,
            String motDePasse,
            LocalDate dateDeNaissance,
            Avatar avatar
    ) {
        super(id, pseudo, email, motDePasse);
        this.dateDeNaissance = dateDeNaissance;
        this.avatar = avatar;
    }

    @Override
    public boolean peutRedigerAvis() {
        return true;
    }

    @Override
    public boolean peutModererAvis() {
        return false;
    }
}


