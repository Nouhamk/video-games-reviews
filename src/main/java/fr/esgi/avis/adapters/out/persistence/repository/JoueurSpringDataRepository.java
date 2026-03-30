package fr.esgi.avis.adapters.out.persistence.repository;

import fr.esgi.avis.adapters.out.persistence.entity.JoueurJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// [rôle de la classe] Repository Spring Data pour les joueurs.
public interface JoueurSpringDataRepository extends JpaRepository<JoueurJpaEntity, Long> {

    Optional<JoueurJpaEntity> findByEmail(String email);
}

