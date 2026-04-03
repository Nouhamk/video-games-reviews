package fr.esgi.avis.adapters.in.web.ui;

import fr.esgi.avis.application.ports.in.AvisUseCase;
import fr.esgi.avis.application.ports.in.JeuUseCase;
import fr.esgi.avis.application.ports.out.AvisRepositoryPort;
import fr.esgi.avis.application.ports.out.ClassificationRepositoryPort;
import fr.esgi.avis.application.ports.out.EditeurRepositoryPort;
import fr.esgi.avis.application.ports.out.GenreRepositoryPort;
import fr.esgi.avis.application.ports.out.JoueurRepositoryPort;
import fr.esgi.avis.application.ports.out.ModerateurRepositoryPort;
import fr.esgi.avis.domain.model.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class JeuUiController {

    private final JeuUseCase                jeuUseCase;
    private final AvisUseCase               avisUseCase;
    private final AvisRepositoryPort        avisRepository;
    private final JoueurRepositoryPort      joueurRepository;
    private final ModerateurRepositoryPort  moderateurRepository;
    private final GenreRepositoryPort       genreRepository;
    private final EditeurRepositoryPort     editeurRepository;
    private final ClassificationRepositoryPort classificationRepository;
    private final PasswordEncoder           passwordEncoder;

    public JeuUiController(JeuUseCase jeuUseCase,
                           AvisUseCase avisUseCase,
                           AvisRepositoryPort avisRepository,
                           JoueurRepositoryPort joueurRepository,
                           ModerateurRepositoryPort moderateurRepository,
                           GenreRepositoryPort genreRepository,
                           EditeurRepositoryPort editeurRepository,
                           ClassificationRepositoryPort classificationRepository,
                           PasswordEncoder passwordEncoder) {
        this.jeuUseCase               = jeuUseCase;
        this.avisUseCase              = avisUseCase;
        this.avisRepository           = avisRepository;
        this.joueurRepository         = joueurRepository;
        this.moderateurRepository     = moderateurRepository;
        this.genreRepository          = genreRepository;
        this.editeurRepository        = editeurRepository;
        this.classificationRepository = classificationRepository;
        this.passwordEncoder          = passwordEncoder;
    }

    // ══════════════════════════════════════════════════════════
    // CONNEXION UNIQUE — redirige selon le rôle
    // ══════════════════════════════════════════════════════════

    @GetMapping("/")
    public String root() {
        return "redirect:/connexion";
    }

    @GetMapping("/connexion")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/connexion")
    public String login(@RequestParam String email,
                        @RequestParam String motDePasse,
                        HttpSession session,
                        RedirectAttributes ra) {

        // Vérifier si c'est un joueur
        var joueurOpt = joueurRepository.findByEmail(email);
        if (joueurOpt.isPresent()) {
            Joueur j = joueurOpt.get();
            if (passwordEncoder.matches(motDePasse, j.getMotDePasse())) {
                session.setAttribute("joueurId",    j.getId());
                session.setAttribute("joueurPseudo", j.getPseudo());
                session.setAttribute("joueurEmail",  j.getEmail());
                session.setAttribute("role",         "JOUEUR");
                return "redirect:/joueur/jeux";
            }
        }

        // Vérifier si c'est un modérateur
        var modOpt = moderateurRepository.findByEmail(email);
        if (modOpt.isPresent()) {
            Moderateur m = modOpt.get();
            if (passwordEncoder.matches(motDePasse, m.getMotDePasse())) {
                session.setAttribute("modId",     m.getId());
                session.setAttribute("modPseudo",  m.getPseudo());
                session.setAttribute("modEmail",   m.getEmail());
                session.setAttribute("role",       "MODERATEUR");
                return "redirect:/moderateur/dashboard";
            }
        }

        ra.addFlashAttribute("erreur", "Email ou mot de passe incorrect.");
        return "redirect:/connexion";
    }

    @GetMapping("/deconnexion")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/connexion";
    }

    // ══════════════════════════════════════════════════════════
    // ESPACE JOUEUR
    // ══════════════════════════════════════════════════════════

    @GetMapping("/joueur/jeux")
    public String joueurJeux(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isJoueur(session)) {
            ra.addFlashAttribute("erreur", "Veuillez vous connecter.");
            return "redirect:/connexion";
        }
        model.addAttribute("jeux", jeuUseCase.listerTous());
        model.addAttribute("joueurConnecte", joueurSession(session));
        return "joueur-jeux";
    }

    @GetMapping("/joueur/jeux/{id}")
    public String joueurDetailJeu(@PathVariable Long id,
                                  HttpSession session,
                                  Model model,
                                  RedirectAttributes ra) {
        if (!isJoueur(session)) {
            return "redirect:/connexion";
        }
        Jeu jeu = jeuUseCase.trouverParId(id);
        List<Avis> approuves = avisUseCase.listerParJeu(id).stream()
                .filter(a -> a.getStatut() == StatutAvis.APPROUVE).toList();
        double moyenne = approuves.stream()
                .mapToDouble(a -> a.getNote() != null ? a.getNote() : 0)
                .average().orElse(0.0);
        model.addAttribute("jeu",          jeu);
        model.addAttribute("avis",         approuves);
        model.addAttribute("moyenneNote",  Math.round(moyenne * 10.0) / 10.0);
        model.addAttribute("nombreAvis",   approuves.size());
        model.addAttribute("avisForm",     new AvisForm());
        model.addAttribute("joueurConnecte", joueurSession(session));
        return "joueur-jeu-detail";
    }

    @PostMapping("/joueur/jeux/{id}/avis")
    public String joueurSoumettreAvis(@PathVariable Long id,
                                      @Valid @ModelAttribute("avisForm") AvisForm form,
                                      BindingResult binding,
                                      HttpSession session,
                                      RedirectAttributes ra,
                                      Model model) {
        if (!isJoueur(session)) return "redirect:/connexion";

        if (binding.hasErrors()) {
            Jeu jeu = jeuUseCase.trouverParId(id);
            List<Avis> approuves = avisUseCase.listerParJeu(id).stream()
                    .filter(a -> a.getStatut() == StatutAvis.APPROUVE).toList();
            model.addAttribute("jeu",           jeu);
            model.addAttribute("avis",          approuves);
            model.addAttribute("moyenneNote",   0.0);
            model.addAttribute("nombreAvis",    approuves.size());
            model.addAttribute("joueurConnecte", joueurSession(session));
            return "joueur-jeu-detail";
        }

        Joueur joueur = joueurSession(session);
        Jeu jeu = Jeu.builder().id(id).build();
        Avis avis = new Avis(null, form.getDescription(), form.getNote(),
                LocalDateTime.now(), StatutAvis.EN_ATTENTE, jeu, joueur, null, null);
        avisUseCase.rediger(avis);
        ra.addFlashAttribute("succes", "Avis soumis ! Il sera visible après modération.");
        return "redirect:/joueur/jeux/" + id;
    }

    @GetMapping("/joueur/mes-avis")
    public String joueurMesAvis(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isJoueur(session)) {
            return "redirect:/connexion";
        }
        Long joueurId = (Long) session.getAttribute("joueurId");
        List<Avis> mesAvis = jeuUseCase.listerTous().stream()
                .flatMap(j -> avisUseCase.listerParJeu(j.getId()).stream())
                .filter(a -> a.getJoueur() != null
                        && a.getJoueur().getId() != null
                        && a.getJoueur().getId().equals(joueurId))
                .toList();
        model.addAttribute("mesAvis",       mesAvis);
        model.addAttribute("joueurConnecte", joueurSession(session));
        return "joueur-mes-avis";
    }

    @PostMapping("/joueur/avis/{id}/supprimer")
    public String joueurSupprimerAvis(@PathVariable Long id,
                                      HttpSession session,
                                      RedirectAttributes ra) {
        if (!isJoueur(session)) return "redirect:/connexion";

        Long joueurId = (Long) session.getAttribute("joueurId");
        var avisOpt = avisRepository.findById(id);
        if (avisOpt.isPresent()) {
            Avis avis = avisOpt.get();
            // Vérifier que l'avis appartient bien au joueur connecté
            if (avis.getJoueur() != null && avis.getJoueur().getId().equals(joueurId)) {
                avis.supprimer();
                avisRepository.save(avis);
                ra.addFlashAttribute("succes", "Avis supprimé.");
            } else {
                ra.addFlashAttribute("erreur", "Vous ne pouvez pas supprimer cet avis.");
            }
        }
        return "redirect:/joueur/mes-avis";
    }

    // ══════════════════════════════════════════════════════════
    // ESPACE MODÉRATEUR
    // ══════════════════════════════════════════════════════════

    @GetMapping("/moderateur/dashboard")
    public String modDashboard(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isMod(session)) {
            ra.addFlashAttribute("erreur", "Accès réservé aux modérateurs.");
            return "redirect:/connexion";
        }
        List<Avis> tous = jeuUseCase.listerTous().stream()
                .flatMap(j -> avisUseCase.listerParJeu(j.getId()).stream())
                .toList();
        model.addAttribute("enAttente",      tous.stream().filter(a -> a.getStatut() == StatutAvis.EN_ATTENTE).toList());
        model.addAttribute("tousLesAvis",    tous);
        model.addAttribute("nbEnAttente",    tous.stream().filter(a -> a.getStatut() == StatutAvis.EN_ATTENTE).count());
        model.addAttribute("nbEnModeration", tous.stream().filter(a -> a.getStatut() == StatutAvis.EN_MODERATION).count());
        model.addAttribute("nbApprouves",    tous.stream().filter(a -> a.getStatut() == StatutAvis.APPROUVE).count());
        model.addAttribute("nbRejetes",      tous.stream().filter(a -> a.getStatut() == StatutAvis.REJETE).count());
        model.addAttribute("moderateurConnecte", modSession(session));
        return "moderateur-dashboard";
    }

    @GetMapping("/moderateur/jeux")
    public String modJeux(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isMod(session)) return "redirect:/connexion";
        model.addAttribute("jeux",           jeuUseCase.listerTous());
        model.addAttribute("genres",          genreRepository.findAll());
        model.addAttribute("editeurs",        editeurRepository.findAll());
        model.addAttribute("classifications", classificationRepository.findAll());
        model.addAttribute("jeuForm",         new JeuForm());
        model.addAttribute("moderateurConnecte", modSession(session));
        return "moderateur-jeux";
    }

    @PostMapping("/moderateur/jeux/ajouter")
    public String modAjouterJeu(@Valid @ModelAttribute("jeuForm") JeuForm form,
                                BindingResult binding,
                                HttpSession session,
                                RedirectAttributes ra,
                                Model model) {
        if (!isMod(session)) return "redirect:/connexion";
        if (binding.hasErrors()) {
            model.addAttribute("jeux",           jeuUseCase.listerTous());
            model.addAttribute("genres",          genreRepository.findAll());
            model.addAttribute("editeurs",        editeurRepository.findAll());
            model.addAttribute("classifications", classificationRepository.findAll());
            model.addAttribute("moderateurConnecte", modSession(session));
            return "moderateur-jeux";
        }
        Jeu jeu = Jeu.builder()
                .nom(form.getNom())
                .description(form.getDescription())
                .dateSortie(form.getDateSortie())
                .prix(form.getPrix())
                .image(form.getImage() != null ? form.getImage() : "default.jpg")
                .genre(Genre.builder().id(form.getGenreId()).build())
                .editeur(Editeur.builder().id(form.getEditeurId()).build())
                .classification(Classification.builder().id(form.getClassificationId()).build())
                .build();
        jeuUseCase.ajouter(jeu);
        ra.addFlashAttribute("succes", "Jeu \"" + form.getNom() + "\" ajouté !");
        return "redirect:/moderateur/jeux";
    }

    @GetMapping("/moderateur/avis")
    public String modAvis(HttpSession session, Model model, RedirectAttributes ra) {
        if (!isMod(session)) return "redirect:/connexion";
        return "redirect:/moderateur/dashboard";
    }

    @PostMapping("/moderateur/avis/{id}/approuver")
    public String modApprouver(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        if (!isMod(session)) return "redirect:/connexion";
        avisUseCase.moderer(id, true, null);
        ra.addFlashAttribute("succes", "Avis approuvé.");
        return "redirect:/moderateur/dashboard";
    }

    @PostMapping("/moderateur/avis/{id}/rejeter")
    public String modRejeter(@PathVariable Long id,
                             @RequestParam(required = false, defaultValue = "Non conforme à la charte.") String raison,
                             HttpSession session,
                             RedirectAttributes ra) {
        if (!isMod(session)) return "redirect:/connexion";
        avisUseCase.moderer(id, false, raison);
        ra.addFlashAttribute("erreur", "Avis rejeté.");
        return "redirect:/moderateur/dashboard";
    }

    // ══════════════════════════════════════════════════════════
    // HELPERS
    // ══════════════════════════════════════════════════════════

    private boolean isJoueur(HttpSession session) {
        return "JOUEUR".equals(session.getAttribute("role"));
    }

    private boolean isMod(HttpSession session) {
        return "MODERATEUR".equals(session.getAttribute("role"));
    }

    private Joueur joueurSession(HttpSession session) {
        return new Joueur(
                (Long)   session.getAttribute("joueurId"),
                (String) session.getAttribute("joueurPseudo"),
                (String) session.getAttribute("joueurEmail"),
                "", null, null
        );
    }

    private Moderateur modSession(HttpSession session) {
        return new Moderateur(
                (Long)   session.getAttribute("modId"),
                (String) session.getAttribute("modPseudo"),
                (String) session.getAttribute("modEmail"),
                "", ""
        );
    }

    // ══════════════════════════════════════════════════════════
    // FORM BEANS
    // ══════════════════════════════════════════════════════════

    @Getter @Setter
    public static class AvisForm {
        @NotBlank(message = "La description ne peut pas être vide.")
        private String description;
        @NotNull(message = "La note est obligatoire.")
        @DecimalMin(value = "0.0", message = "Note min : 0")
        @DecimalMax(value = "10.0", message = "Note max : 10")
        private Float note;
    }

    @Getter @Setter
    public static class JeuForm {
        @NotBlank(message = "Le nom est obligatoire.")
        private String nom;
        @NotBlank(message = "La description est obligatoire.")
        private String description;
        @NotNull(message = "La date de sortie est obligatoire.")
        private LocalDate dateSortie;
        @NotNull(message = "Le prix est obligatoire.")
        private Float prix;
        private String image;
        @NotNull(message = "Le genre est obligatoire.")
        private Long genreId;
        @NotNull(message = "L'éditeur est obligatoire.")
        private Long editeurId;
        @NotNull(message = "La classification est obligatoire.")
        private Long classificationId;
    }
}