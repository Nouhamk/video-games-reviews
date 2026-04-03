package fr.esgi.avis.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Joueur extends Utilisateur {

    private LocalDate dateDeNaissance;
    private Avatar avatar;

    public Joueur(Long id, String pseudo, String email, String motDePasse,
                  LocalDate dateDeNaissance, Avatar avatar) {
        super(id, pseudo, email, motDePasse);
        this.dateDeNaissance = dateDeNaissance;
        this.avatar = avatar;
    }

    @Override public boolean peutRedigerAvis() { return true; }
    @Override public boolean peutModererAvis() { return false; }
}
