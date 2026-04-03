package fr.esgi.avis.adapters.in.web.ui;

import fr.esgi.avis.application.ports.in.AvisUseCase;
import fr.esgi.avis.application.ports.in.JeuUseCase;
import fr.esgi.avis.domain.model.Avis;
import fr.esgi.avis.domain.model.Jeu;
import fr.esgi.avis.domain.model.Joueur;
import fr.esgi.avis.domain.model.StatutAvis;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class JeuUiController {

    private final JeuUseCase  jeuUseCase;
    private final AvisUseCase avisUseCase;

    public JeuUiController(JeuUseCase jeuUseCase, AvisUseCase avisUseCase) {
        this.jeuUseCase  = jeuUseCase;
        this.avisUseCase = avisUseCase;
    }

    // ── Accueil ────────────────────────────────────────────────────────────

    @GetMapping("/")
    public String accueil(Model model) {
        List<Jeu> jeux = jeuUseCase.listerTous();
        long totalAvis = jeux.stream()
                .flatMap(j -> avisUseCase.listerParJeu(j.getId()).stream())
                .filter(a -> a.getStatut() == StatutAvis.APPROUVE)
                .count();
        model.addAttribute("jeux", jeux);
        model.addAttribute("totalJeux", jeux.size());
        model.addAttribute("totalAvisApprouves", totalAvis);
        return "index";
    }

    // ── Détail jeu ─────────────────────────────────────────────────────────

    @GetMapping("/jeux/{id}")
    public String detailJeu(@PathVariable Long id, Model model) {
        Jeu jeu = jeuUseCase.trouverParId(id);
        List<Avis> avisApprouves = avisUseCase.listerParJeu(id).stream()
                .filter(a -> a.getStatut() == StatutAvis.APPROUVE)
                .toList();
        double moyenne = avisApprouves.stream()
                .mapToDouble(a -> a.getNote() != null ? a.getNote() : 0)
                .average().orElse(0.0);
        model.addAttribute("jeu", jeu);
        model.addAttribute("avis", avisApprouves);
        model.addAttribute("moyenneNote", Math.round(moyenne * 10.0) / 10.0);
        model.addAttribute("nombreAvis", avisApprouves.size());
        model.addAttribute("avisForm", new AvisForm());
        return "jeu-detail";
    }

    // ── Soumettre un avis ──────────────────────────────────────────────────

    @PostMapping("/jeux/{id}/avis")
    public String soumettreAvis(@PathVariable Long id,
                                 @Valid @ModelAttribute("avisForm") AvisForm form,
                                 BindingResult binding,
                                 RedirectAttributes ra,
                                 Model model) {
        if (binding.hasErrors()) {
            Jeu jeu = jeuUseCase.trouverParId(id);
            List<Avis> avisApprouves = avisUseCase.listerParJeu(id).stream()
                    .filter(a -> a.getStatut() == StatutAvis.APPROUVE).toList();
            model.addAttribute("jeu", jeu);
            model.addAttribute("avis", avisApprouves);
            model.addAttribute("moyenneNote", 0.0);
            model.addAttribute("nombreAvis", avisApprouves.size());
            return "jeu-detail";
        }
        // Joueur fictif id=1 (pas d'authentification dans l'UI)
        Joueur joueur = new Joueur(1L, "Visiteur", "visiteur@example.com", "", null, null);
        Jeu jeu = Jeu.builder().id(id).build();
        Avis avis = new Avis(null, form.getDescription(), form.getNote(),
                LocalDateTime.now(), StatutAvis.EN_ATTENTE, jeu, joueur, null, null);
        avisUseCase.rediger(avis);
        ra.addFlashAttribute("succes", "Ton avis a été soumis ! Il sera visible après modération.");
        return "redirect:/jeux/" + id;
    }

    // ── Dashboard modérateur ───────────────────────────────────────────────

    @GetMapping("/moderateur")
    public String dashboard(Model model) {
        List<Jeu> jeux = jeuUseCase.listerTous();
        List<Avis> tous = jeux.stream()
                .flatMap(j -> avisUseCase.listerParJeu(j.getId()).stream())
                .toList();
        model.addAttribute("enAttente",    tous.stream().filter(a -> a.getStatut() == StatutAvis.EN_ATTENTE).toList());
        model.addAttribute("enModeration", tous.stream().filter(a -> a.getStatut() == StatutAvis.EN_MODERATION).toList());
        model.addAttribute("approuves",    tous.stream().filter(a -> a.getStatut() == StatutAvis.APPROUVE).toList());
        model.addAttribute("rejetes",      tous.stream().filter(a -> a.getStatut() == StatutAvis.REJETE).toList());
        long nbEnAttente    = tous.stream().filter(a -> a.getStatut() == StatutAvis.EN_ATTENTE).count();
        long nbEnModeration = tous.stream().filter(a -> a.getStatut() == StatutAvis.EN_MODERATION).count();
        long nbApprouves    = tous.stream().filter(a -> a.getStatut() == StatutAvis.APPROUVE).count();
        long nbRejetes      = tous.stream().filter(a -> a.getStatut() == StatutAvis.REJETE).count();
        model.addAttribute("stats", new ModerationStats(nbEnAttente, nbEnModeration, nbApprouves, nbRejetes));
        return "moderateur";
    }

    @PostMapping("/moderateur/approuver/{avisId}")
    public String approuver(@PathVariable Long avisId, RedirectAttributes ra) {
        avisUseCase.moderer(avisId, true, null);
        ra.addFlashAttribute("succes", "Avis approuvé ✓");
        return "redirect:/moderateur";
    }

    @PostMapping("/moderateur/rejeter/{avisId}")
    public String rejeter(@PathVariable Long avisId,
                           @ModelAttribute("raison") String raison,
                           RedirectAttributes ra) {
        avisUseCase.moderer(avisId, false, raison);
        ra.addFlashAttribute("erreur", "Avis rejeté.");
        return "redirect:/moderateur";
    }

    // ── Form bean ──────────────────────────────────────────────────────────

    @Getter @Setter
    public static class AvisForm {
        @NotBlank(message = "La description ne peut pas être vide.")
        private String description;
        @NotNull(message = "La note est obligatoire.")
        @DecimalMin(value = "0.0", message = "Note min : 0")
        @DecimalMax(value = "10.0", message = "Note max : 10")
        private Float note;
    }

    public record ModerationStats(long enAttente, long enModeration, long approuves, long rejetes) {}
}
