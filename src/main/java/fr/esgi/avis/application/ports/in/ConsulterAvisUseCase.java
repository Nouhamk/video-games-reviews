package fr.esgi.avis.application.ports.in;

import java.util.List;

import fr.esgi.avis.domain.entities.Avis;

public interface ConsulterAvisUseCase {

	Avis recuperer(Long id);

	List<Avis> recupererTous();
}

