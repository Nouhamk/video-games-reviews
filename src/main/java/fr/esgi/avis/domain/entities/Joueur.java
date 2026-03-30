
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Joueur extends Utilisateur {

	private LocalDate dateDeNaissance;
	private Avatar avatar;
	private List<Avis> avis = new ArrayList<>();

	@Builder
	public Joueur(Long id, String pseudo, String email, String motDePasse, LocalDate dateDeNaissance, Avatar avatar,
			List<Avis> avis) {
		super(id, pseudo, email, motDePasse);
		this.dateDeNaissance = dateDeNaissance;
		this.avatar = avatar;
		this.avis = avis != null ? avis : new ArrayList<>();
	}

	@Override
	public boolean peutRedigerAvis() {
		return true;
	}

	@Override
	public boolean peutModererAvis() {
		return false;
	}
}

