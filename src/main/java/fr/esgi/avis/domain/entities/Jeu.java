package fr.esgi.avis.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Jeu {

	private Long id;
	private String nom;
	private String description;
	private LocalDate dateSortie;
	private float prix;
	private String image;
	private Genre genre;
	private Editeur editeur;
	@Builder.Default
	private List<Plateforme> plateformes = new ArrayList<>();
	private Classification classification;
}

