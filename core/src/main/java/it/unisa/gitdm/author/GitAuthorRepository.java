/**
 *
 */
package it.unisa.gitdm.author;

import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.versioning.CodeVersioningSystemRepository;
import it.unisa.gitdm.versioning.GitRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class GitAuthorRepository implements AuthorRepository,
        java.io.Serializable {

    private List<Author> authors;

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.unisa.gitdm.author.AuthorRepository#init(it.unisa.gitdm.versioning
     * .CodeVersioningSystemRepository)
     */
    @Override
    public void init(CodeVersioningSystemRepository repository) {
        authors = new ArrayList<>();
        GitRepository gitRepository = (GitRepository) repository;
        List<Commit> commits = gitRepository.getCommits();
        for (Commit c : commits) {
            Author authorInRepository = findAuthor(c.getAuthor());
            if (authorInRepository == null) {
                authors.add(c.getAuthor());
            } else {
                c.setAuthor(authorInRepository);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.unisa.gitdm.author.AuthorRepository#getListOfAuthors()
     */
    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.unisa.gitdm.author.AuthorRepository#getAuthorByName(java.lang.String)
     */
    @Override
    public Author getAuthorByName(String name) {
        for (Author a : authors) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.unisa.gitdm.author.AuthorRepository#getAuthorByEmail(java.lang.String)
     */
    @Override
    public Author getAuthorByEmail(String email) {
        for (Author a : authors) {
            if (a.getEmail().equalsIgnoreCase(email)) {
                return a;
            }
        }
        return null;
    }

    private Author findAuthor(Author author) {
        for (Author a : authors) {
            if (author.getEmail().equalsIgnoreCase(a.getEmail())) {
                return a;
            }
        }
        return null;
    }
}
