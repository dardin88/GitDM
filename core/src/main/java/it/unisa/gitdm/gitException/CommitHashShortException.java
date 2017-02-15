package it.unisa.gitdm.gitException;

/**
 *
 * @author Vincenzo
 */
public class CommitHashShortException extends RuntimeException {

    public CommitHashShortException(String err) {
        super(err);
    }
}
