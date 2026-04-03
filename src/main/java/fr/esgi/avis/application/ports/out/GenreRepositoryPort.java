package fr.esgi.avis.application.ports.out;
import fr.esgi.avis.domain.model.Genre;
import java.util.List;
import java.util.Optional;
public interface GenreRepositoryPort {
    List<Genre> findAll();
    Optional<Genre> findById(Long id);
}
