package fr.esgi.avis.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// [rôle de la classe] Modele metier representant une classification d'age/contenu.
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Classification {
    private Long id;
    private String nom;
    private String couleurRGB;
}

