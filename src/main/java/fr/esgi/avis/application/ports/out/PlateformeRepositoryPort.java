package fr.esgi.avis.application.ports.out;
import fr.esgi.avis.domain.model.Plateforme;
import java.util.List;
public interface PlateformeRepositoryPort {
    List<Plateforme> findAll();
}
