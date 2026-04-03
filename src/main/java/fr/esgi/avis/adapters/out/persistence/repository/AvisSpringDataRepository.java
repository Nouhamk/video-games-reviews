package fr.esgi.avis.adapters.out.persistence.repository;
import fr.esgi.avis.adapters.out.persistence.entity.AvisJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AvisSpringDataRepository extends JpaRepository<AvisJpaEntity, Long> {
    List<AvisJpaEntity> findByJeuId(Long jeuId);
}
