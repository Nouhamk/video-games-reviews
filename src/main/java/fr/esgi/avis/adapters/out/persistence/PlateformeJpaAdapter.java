package fr.esgi.avis.adapters.out.persistence;
import fr.esgi.avis.adapters.out.persistence.entity.PlateformeJpaEntity;
import fr.esgi.avis.adapters.out.persistence.repository.PlateformeSpringDataRepository;
import fr.esgi.avis.application.ports.out.PlateformeRepositoryPort;
import fr.esgi.avis.domain.model.Plateforme;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class PlateformeJpaAdapter implements PlateformeRepositoryPort {
    private final PlateformeSpringDataRepository repo;
    public PlateformeJpaAdapter(PlateformeSpringDataRepository repo) { this.repo = repo; }
    @Override public List<Plateforme> findAll() {
        return repo.findAll().stream().map(this::toDomain).toList();
    }
    private Plateforme toDomain(PlateformeJpaEntity e) {
        if (e == null) return null;
        return Plateforme.builder().id(e.getId()).nom(e.getNom())
                .dateDeSortie(e.getDateDeSortie()).build();
    }
}
