package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.in.AvisUseCase;
import fr.esgi.avis.application.ports.out.AvisRepositoryPort;
import fr.esgi.avis.application.ports.out.JeuRepositoryPort;
import fr.esgi.avis.domain.exception.AvisInvalideException;
import fr.esgi.avis.domain.exception.JeuNotFoundException;
import fr.esgi.avis.domain.model.Avis;
import fr.esgi.avis.domain.model.StatutAvis;

import java.util.List;

public class AvisInteractor implements AvisUseCase {

    private final AvisRepositoryPort avisRepository;
    private final JeuRepositoryPort  jeuRepository;

    public AvisInteractor(AvisRepositoryPort avisRepository, JeuRepositoryPort jeuRepository) {
        this.avisRepository = avisRepository;
        this.jeuRepository  = jeuRepository;
    }

    @Override
    public Avis rediger(Avis avis) {
        if (avis.getJoueur() == null || avis.getJoueur().getId() == null) {
            throw new AvisInvalideException("Un avis doit être associé à un joueur.");
        }
        if (avis.getJeu() == null || avis.getJeu().getId() == null) {
            throw new AvisInvalideException("Un avis doit cibler un jeu existant.");
        }
        jeuRepository.findById(avis.getJeu().getId())
                .orElseThrow(() -> new JeuNotFoundException(
                        "Jeu introuvable avec l'id : " + avis.getJeu().getId()));
        return avisRepository.save(avis);
    }

    @Override
    public List<Avis> listerParJeu(Long jeuId) {
        return avisRepository.findByJeuId(jeuId);
    }

    @Override
    public Avis moderer(Long avisId, boolean approuver, String raisonRejet) {
        Avis avis = avisRepository.findById(avisId)
                .orElseThrow(() -> new AvisInvalideException(
                        "Avis introuvable avec l'id : " + avisId));

        if (avis.getStatut() == StatutAvis.EN_ATTENTE) {
            avis.prendreEnCharge();
        }
        if (approuver) {
            avis.approuver();
        } else {
            avis.rejeter(raisonRejet != null && !raisonRejet.isBlank()
                    ? raisonRejet : "Non conforme à la charte.");
        }
        return avisRepository.save(avis);
    }
}
