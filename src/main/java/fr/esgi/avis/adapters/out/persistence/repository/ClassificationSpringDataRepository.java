package fr.esgi.avis.adapters.out.persistence.repository;

import fr.esgi.avis.adapters.out.persistence.entity.ClassificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// [rôle de la classe] Repository Spring Data pour les classifications.
public interface ClassificationSpringDataRepository extends JpaRepository<ClassificationJpaEntity, Long> {
}

