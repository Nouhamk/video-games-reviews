package fr.esgi.avis.domain.exception;

// [rôle de la classe] Exception metier levee quand un avis est incoherent ou dans un mauvais etat.
public class AvisInvalideException extends RuntimeException {

    public AvisInvalideException(String message) {
        super(message);
    }

    public AvisInvalideException(String message, Throwable cause) {
        super(message, cause);
    }
}

