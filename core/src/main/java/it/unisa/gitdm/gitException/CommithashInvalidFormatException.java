package it.unisa.gitdm.gitException;

/**
 *
 * @author Vincenzo
 */
public class CommithashInvalidFormatException extends RuntimeException {

    public CommithashInvalidFormatException(String err) {
        super(err);
    }
}
