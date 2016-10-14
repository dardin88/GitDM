/**
 *
 */
package it.unisa.gitdm.tracking;

import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.source.Jira;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dario Di Nucci - dario.dinucci@hotmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public final class JiraRepository implements BugTrackingSystemRepository, Serializable {

    private String path;
    private List<Bug> bugs;
    private String product = "";

    public JiraRepository() {
        this.bugs = new ArrayList<>();
    }

    private JiraRepository(String path, String product) {
        this();
        this.setPath(path);
        this.setProduct(product);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.unisa.gitdm.tracking.BugTrackingSystemRepository#initialize()
     */
    @Override
    public boolean initialize(boolean isSVN) {
        if (this.path != null) {
            try {
                this.bugs = Jira.extractBug(this.path, this.product, isSVN);
            } catch (MalformedURLException ex) {
                Logger.getLogger(JiraRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        return false;
    }

    /**
     * Save the Jira Repository
     *
     * @param filePath Path of file where save the repository
     */
    @Override
    public void save(String filePath) {
        File dir = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
        dir.mkdirs();
        try (
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        } catch (IOException ex) {
            Logger.getLogger(JiraRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("Serialized data is saved in " + filePath);
    }

    @Override
    public void printAllBugs() {
        for (Bug bug : this.bugs) {
            System.out.println(bug);
        }
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public List<Bug> getBugs() {
        return this.bugs;
    }

    @Override
    public void setProduct(String product) {
        this.product = product;
    }
}
