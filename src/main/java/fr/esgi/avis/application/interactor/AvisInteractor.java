package fr.esgi.avis.application.interactor;

import fr.esgi.avis.application.ports.in.AvisUseCase;
import fr.esgi.avis.application.ports.out.AvisRepositoryPort;
import fr.esgi.avis.application.ports.out.JeuRepositoryPort;
import fr.esgi.avis.domain.exception.AvisInvalideException;
import fr.esgi.avis.domain.exception.JeuNotFoundException;
import fr.esgi.avis.domain.model.Avis;
import fr.esgi.avis.domain.model.StatutAvis;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

// [rôle de la classe] Interactor applicatif des cas d'usage de redaction et moderation des avis.
public class AvisInteractor implements AvisUseCase {

    private final AvisRepositoryPort avisRepository;
    private final JeuRepositoryPort jeuRepository;

    public AvisInteractor(AvisRepositoryPort avisRepository, JeuRepositoryPort jeuRepository) {
        this.avisRepository = avisRepository;
        this.jeuRepository = jeuRepository;
    }

    @Override
    @Transactional
    public Avis rediger(Avis avis) {
        if (avis == null || avis.getJeu() == null || avis.getJeu().getId() == null) {
            throw new AvisInvalideException("Un avis doit cibler un jeu existant.");
        }
        if (avis.getJoueur() == null || avis.getJoueur().getId() == null) {
            throw new AvisInvalideException("Un avis doit etre associe a un joueur authentifie.");
        }
        Long jeuId = avis.getJeu().getId();
        jeuRepository.findById(jeuId)
                .orElseThrow(() -> new JeuNotFoundException("Jeu introuvable avec l'id: " + jeuId));
        return avisRepository.save(avis);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Avis> listerParJeu(Long jeuId) {
        return avisRepository.findByJeuId(jeuId);
    }

    @Override
    @Transactional
    public Avis moderer(Long avisId, boolean approuver, String raisonRejet) {
        Avis avis = avisRepository.findById(avisId)
                .orElseThrow(() -> new AvisInvalideException("Avis introuvable avec l'id: " + avisId));

        if (avis.getStatut() == null || avis.getStatut() == StatutAvis.EN_ATTENTE) {
            avis.prendreEnCharge();
        }

        if (approuver) {
            avis.approuver();
        } else {
            avis.rejeter(raisonRejet);
        }
        return avisRepository.save(avis);
    }
}

