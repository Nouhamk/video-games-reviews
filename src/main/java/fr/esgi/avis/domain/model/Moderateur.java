package fr.esgi.avis.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Moderateur extends Utilisateur {

    private String numeroDeTelephone;

    public Moderateur(Long id, String pseudo, String email, String motDePasse,
                      String numeroDeTelephone) {
        super(id, pseudo, email, motDePasse);
        this.numeroDeTelephone = numeroDeTelephone;
    }

    @Override public boolean peutRedigerAvis() { return false; }
    @Override public boolean peutModererAvis() { return true; }
}
