package fr.esgi.avis.adapters.out.persistence.repository;

import fr.esgi.avis.adapters.out.persistence.entity.JeuJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// [rôle de la classe] Repository Spring Data pour les jeux.
public interface JeuSpringDataRepository extends JpaRepository<JeuJpaEntity, Long> {
}

