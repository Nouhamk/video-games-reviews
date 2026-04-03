package fr.esgi.avis.application.ports.out;
import fr.esgi.avis.domain.model.Editeur;
import java.util.List;
public interface EditeurRepositoryPort {
    List<Editeur> findAll();
}
