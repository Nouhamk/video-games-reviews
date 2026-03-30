package fr.esgi.avis.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Classe metier abstraite commune a tous les utilisateurs.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Utilisateur {
    private Long id;
    private String pseudo;
    private String email;
    private String motDePasse;

    public abstract boolean peutRedigerAvis();

    public abstract boolean peutModererAvis();
}

