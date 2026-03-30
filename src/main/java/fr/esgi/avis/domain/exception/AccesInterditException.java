package fr.esgi.avis.domain.exception;

// [rôle de la classe] Exception metier levee pour une tentative d'acces non autorisee.
public class AccesInterditException extends RuntimeException {

    public AccesInterditException(String message) {
        super(message);
    }

    public AccesInterditException(String message, Throwable cause) {
        super(message, cause);
    }
}

