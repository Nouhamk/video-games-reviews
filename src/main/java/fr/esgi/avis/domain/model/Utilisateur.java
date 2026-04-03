package fr.esgi.avis.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Utilisateur {
    private Long id;
    private String pseudo;
    private String email;

    @JsonIgnore
    private String motDePasse;

    public abstract boolean peutRedigerAvis();
    public abstract boolean peutModererAvis();
}
