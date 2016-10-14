/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.tracking.BugZillaRepository;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class BugZillaInitTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        BugZillaRepository repository = new BugZillaRepository();

        repository.setPath("https://issues.apache.org/bugzilla/");
        repository.setProduct("Ant");

        repository.initialize(false);
        System.out.println("Fine init");
        System.out.println("Size: " + repository.getBugs().size());
        // repository.printAllBugs();

    }

}
