package fr.esgi.avis.adapters.out.persistence.repository;
import fr.esgi.avis.adapters.out.persistence.entity.PlateformeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PlateformeSpringDataRepository extends JpaRepository<PlateformeJpaEntity, Long> {}
