package fr.esgi.avis.adapters.out.persistence.repository;
import fr.esgi.avis.adapters.out.persistence.entity.GenreJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface GenreSpringDataRepository extends JpaRepository<GenreJpaEntity, Long> {}
