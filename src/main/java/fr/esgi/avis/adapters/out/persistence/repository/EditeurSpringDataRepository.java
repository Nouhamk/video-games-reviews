package fr.esgi.avis.adapters.out.persistence.repository;
import fr.esgi.avis.adapters.out.persistence.entity.EditeurJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EditeurSpringDataRepository extends JpaRepository<EditeurJpaEntity, Long> {}
