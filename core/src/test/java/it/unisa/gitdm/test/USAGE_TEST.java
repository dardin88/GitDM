/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.ProcessAverage;
import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.tracking.BugZillaRepository;
import it.unisa.gitdm.versioning.GitRepository;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class USAGE_TEST {

    public static void main(String[] args) {
        ProcessAverage process = new ProcessAverage();

        //Carica i repository (specifica i path)
        process.initBugZillaRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/bugZillaRepository.data");
        process.initGitRepositoryFromFile("/home/sesa/Scrivania/Michele_Tufano/files/gitRepositoryFixBug.data");

        // ----------------- COMMIT -----------------------------------------
        //Prende il repository Git
        GitRepository gitRepository = process.getGitRepository();

        //Scorre la lista dei commit
        for (Commit c : gitRepository.getCommits()) {
            //Puoi prendere molte altre info dal commit...
            c.getBody();

            //Puoi vedere se un commit è un fix per un bug o se un commit ha introdotto un bug
            c.isBug();
            c.isFix();

            //Puoi prendere il bug fixato (se è un commit-fix)
            c.getFixedBug();
            //o il bug introdotto (se è un commit-bug)
            c.getIntroducedBug();
        }

        //Puoi prendere tutti i commit di un autore
        gitRepository.getCommitsByAuthor(new Author());
        //o uno specifico commit
        gitRepository.getCommitByID("ID", false);

        // ----------------- BUG -----------------------------------------
        //Prende il repository BugZilla
        BugZillaRepository bugRepository = process.getBugZillaRepository();

        for (Bug b : bugRepository.getBugs()) {
            //Tutti i commit che hanno potuto introdurre il bug (commit-bug) SE sono stati trovati
            b.getFixInducingChanges();

            //Commit che ha fixato il bug (commit-fix) SE è stato trovato
            b.getFix();

            //puoi prendere molte altre informazioni...
            b.getID();
        }
    }

}
