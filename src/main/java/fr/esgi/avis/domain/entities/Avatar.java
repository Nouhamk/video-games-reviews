package fr.esgi.avis.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Avatar {

	private Long id;
	private String nom;
	private Joueur joueur;
}

