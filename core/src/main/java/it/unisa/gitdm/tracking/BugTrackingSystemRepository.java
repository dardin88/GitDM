/**
 *
 */
package it.unisa.gitdm.tracking;

import it.unisa.gitdm.bean.Bug;

import java.util.List;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public interface BugTrackingSystemRepository {

    boolean initialize(boolean isSVN);

    List<Bug> getBugs();

    String getPath();

    void setPath(String path);

    void setProduct(String product);

    void save(String filePath);

    void printAllBugs();
}
