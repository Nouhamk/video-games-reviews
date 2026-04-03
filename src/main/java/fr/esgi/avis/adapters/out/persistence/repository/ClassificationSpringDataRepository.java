package fr.esgi.avis.adapters.out.persistence.repository;
import fr.esgi.avis.adapters.out.persistence.entity.ClassificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClassificationSpringDataRepository extends JpaRepository<ClassificationJpaEntity, Long> {}
