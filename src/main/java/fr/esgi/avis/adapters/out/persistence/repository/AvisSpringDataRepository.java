package fr.esgi.avis.adapters.out.persistence.repository;

import fr.esgi.avis.adapters.out.persistence.entity.AvisJpaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// [rôle de la classe] Repository Spring Data pour les avis.
public interface AvisSpringDataRepository extends JpaRepository<AvisJpaEntity, Long> {

    List<AvisJpaEntity> findByJeuId(Long jeuId);
}

