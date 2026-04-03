package fr.esgi.avis.domain.model;
import lombok.*;
import java.time.LocalDate;
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Plateforme { private Long id; private String nom; private LocalDate dateDeSortie; }
