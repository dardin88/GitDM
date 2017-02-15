package it.unisa.gitdm.gitException;

/**
 *
 * @author Vincenzo
 */
public class DirectoryNotFound extends RuntimeException {

    public DirectoryNotFound(String err) {
        super(err);
    }
}
