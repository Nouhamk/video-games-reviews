package fr.esgi.avis.adapters.out.persistence.repository;
import fr.esgi.avis.adapters.out.persistence.entity.JeuJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface JeuSpringDataRepository extends JpaRepository<JeuJpaEntity, Long> {}
