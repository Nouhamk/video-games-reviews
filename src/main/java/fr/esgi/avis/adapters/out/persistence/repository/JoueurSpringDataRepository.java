package fr.esgi.avis.adapters.out.persistence.repository;
import fr.esgi.avis.adapters.out.persistence.entity.JoueurJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface JoueurSpringDataRepository extends JpaRepository<JoueurJpaEntity, Long> {
    Optional<JoueurJpaEntity> findByEmail(String email);
}
