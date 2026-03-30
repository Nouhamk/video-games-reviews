package fr.esgi.avis.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Utilisateur {

	private Long id;
	private String pseudo;
	private String email;
	private String motDePasse;

	public abstract boolean peutRedigerAvis();

	public abstract boolean peutModererAvis();
}

