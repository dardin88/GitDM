/**
 *
 */
package it.unisa.gitdm.author;

import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.versioning.CodeVersioningSystemRepository;

import java.util.List;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
interface AuthorRepository {

    /**
     * Initialize the repository of authors reading a specific Code Versioning
     * System Repository
     *
     * @param repository a Code Versioning System Repository
     */
    void init(CodeVersioningSystemRepository repository);

    /**
     * Returns the list of all authors in the repository
     *
     * @return List of all authors
     */
    List<Author> getAuthors();

    /**
     * Returns the author by name
     *
     * @param name the name of author to find
     * @return Author with the specified name or null if it does not exist in
     * the repository
     */
    Author getAuthorByName(String name);

    /**
     * Returns the author by email
     *
     * @param email the email of author to find
     * @return Author with the specified email or null if it does not exist in
     * the repository
     */
    Author getAuthorByEmail(String email);
}
