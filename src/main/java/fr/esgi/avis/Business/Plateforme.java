package fr.esgi.avis.Business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plateforme {

    private Long id;
    private String nom;
    private LocalDate dateDeSortie;
}