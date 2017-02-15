package it.unisa.gitdm.gitException;

/**
 *
 * @author Vincenzo
 */
public class CommitNotFound extends RuntimeException {

    public CommitNotFound(String err) {
        super(err);
    }

}
