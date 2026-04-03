package fr.esgi.avis.infrastructure.config;

import fr.esgi.avis.adapters.out.persistence.entity.*;
import fr.esgi.avis.adapters.out.persistence.repository.*;
import fr.esgi.avis.domain.model.StatutAvis;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            ClassificationSpringDataRepository classifRepo,
            GenreSpringDataRepository genreRepo,
            EditeurSpringDataRepository editeurRepo,
            PlateformeSpringDataRepository platRepo,
            JoueurSpringDataRepository joueurRepo,
            ModerateurSpringDataRepository modRepo,
            JeuSpringDataRepository jeuRepo,
            AvisSpringDataRepository avisRepo) {

        return args -> {
            if (jeuRepo.count() > 0) return;

            ClassificationJpaEntity pegi12 = classifRepo.save(new ClassificationJpaEntity(null, "PEGI 12", "#FF9900", List.of()));
            ClassificationJpaEntity pegi18 = classifRepo.save(new ClassificationJpaEntity(null, "PEGI 18", "#CC0000", List.of()));

            GenreJpaEntity action = genreRepo.save(new GenreJpaEntity(null, "Action", List.of()));
            GenreJpaEntity rpg    = genreRepo.save(new GenreJpaEntity(null, "RPG",    List.of()));

            EditeurJpaEntity nintendo     = editeurRepo.save(new EditeurJpaEntity(null, "Nintendo",     List.of()));
            EditeurJpaEntity fromsoftware = editeurRepo.save(new EditeurJpaEntity(null, "FromSoftware", List.of()));

            PlateformeJpaEntity pc      = platRepo.save(new PlateformeJpaEntity(null, "PC",              LocalDate.of(2000,  1,  1), List.of()));
            PlateformeJpaEntity ps5     = platRepo.save(new PlateformeJpaEntity(null, "PlayStation 5",   LocalDate.of(2020, 11, 12), List.of()));
            PlateformeJpaEntity switchN = platRepo.save(new PlateformeJpaEntity(null, "Nintendo Switch", LocalDate.of(2017,  3,  3), List.of()));

            JoueurJpaEntity alice = joueurRepo.save(new JoueurJpaEntity(
                    null, "Alice", "alice@example.com",
                    "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy",
                    LocalDate.of(1995, 6, 15), null));

            ModerateurJpaEntity bob = modRepo.save(new ModerateurJpaEntity(
                    null, "ModBob", "bob@example.com",
                    "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy",
                    "0612345678"));

            JeuJpaEntity eldenRing = jeuRepo.save(new JeuJpaEntity(
                    null, "Elden Ring",
                    "Un RPG en monde ouvert par FromSoftware.",
                    LocalDate.of(2022, 2, 25), 59.99f, "elden_ring.jpg",
                    rpg, fromsoftware, List.of(pc, ps5), pegi18));

            JeuJpaEntity zelda = jeuRepo.save(new JeuJpaEntity(
                    null, "Zelda: Tears of the Kingdom",
                    "Suite de Breath of the Wild.",
                    LocalDate.of(2023, 5, 12), 69.99f, "zelda.jpg",
                    action, nintendo, List.of(switchN), pegi12));

            AvisJpaEntity avis1 = new AvisJpaEntity();
            avis1.setDescription("Chef-d'oeuvre absolu !");
            avis1.setNote(9.5f);
            avis1.setDateDEnvoi(LocalDateTime.of(2024, 1, 10, 14, 30));
            avis1.setStatut(StatutAvis.APPROUVE);
            avis1.setJeu(eldenRing);
            avis1.setJoueur(alice);
            avis1.setModerateur(bob);
            avisRepo.save(avis1);

            AvisJpaEntity avis2 = new AvisJpaEntity();
            avis2.setDescription("Exploration incroyable !");
            avis2.setNote(8.0f);
            avis2.setDateDEnvoi(LocalDateTime.of(2024, 3, 5, 9, 0));
            avis2.setStatut(StatutAvis.EN_ATTENTE);
            avis2.setJeu(zelda);
            avis2.setJoueur(alice);
            avisRepo.save(avis2);

            System.out.println("✅ Données de test insérées avec succès !");
        };
    }
}
