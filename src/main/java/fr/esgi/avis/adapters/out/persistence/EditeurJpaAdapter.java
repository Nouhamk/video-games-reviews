package fr.esgi.avis.adapters.out.persistence;
import fr.esgi.avis.adapters.out.persistence.entity.EditeurJpaEntity;
import fr.esgi.avis.adapters.out.persistence.repository.EditeurSpringDataRepository;
import fr.esgi.avis.application.ports.out.EditeurRepositoryPort;
import fr.esgi.avis.domain.model.Editeur;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class EditeurJpaAdapter implements EditeurRepositoryPort {
    private final EditeurSpringDataRepository repo;
    public EditeurJpaAdapter(EditeurSpringDataRepository repo) { this.repo = repo; }
    @Override public List<Editeur> findAll() {
        return repo.findAll().stream().map(this::toDomain).toList();
    }
    private Editeur toDomain(EditeurJpaEntity e) {
        if (e == null) return null;
        return Editeur.builder().id(e.getId()).nom(e.getNom()).build();
    }
}
