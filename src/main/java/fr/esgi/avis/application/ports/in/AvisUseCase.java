package fr.esgi.avis.application.ports.in;

import fr.esgi.avis.domain.model.Avis;
import java.util.List;

// [rôle de la classe] Port entrant definissant les cas d'usage lies aux avis.
public interface AvisUseCase {

    Avis rediger(Avis avis);

    List<Avis> listerParJeu(Long jeuId);

    Avis moderer(Long avisId, boolean approuver, String raisonRejet);
}

