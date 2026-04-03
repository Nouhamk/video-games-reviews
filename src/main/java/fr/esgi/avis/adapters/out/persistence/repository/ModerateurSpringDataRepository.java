package fr.esgi.avis.adapters.out.persistence.repository;
import fr.esgi.avis.adapters.out.persistence.entity.ModerateurJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ModerateurSpringDataRepository extends JpaRepository<ModerateurJpaEntity, Long> {
    Optional<ModerateurJpaEntity> findByEmail(String email);
}
