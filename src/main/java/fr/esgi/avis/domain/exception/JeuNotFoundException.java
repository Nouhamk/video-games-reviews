package fr.esgi.avis.domain.exception;

// [rôle de la classe] Exception metier levee lorsqu'un jeu est introuvable.
public class JeuNotFoundException extends RuntimeException {

    public JeuNotFoundException(String message) {
        super(message);
    }

    public JeuNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

