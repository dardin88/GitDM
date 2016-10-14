/**
 *
 */
package it.unisa.gitdm.tracking;

import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.source.BugZilla;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public final class BugZillaRepository implements BugTrackingSystemRepository, Serializable {

    private String path;
    private List<Bug> bugs;
    private String product = "";

    public BugZillaRepository() {
        bugs = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     *
     * @see it.unisa.gitdm.tracking.BugTrackingSystemRepository#initialize()
     */
    @Override
    public boolean initialize(boolean isSVN) {
        if (path != null) {
            bugs = BugZilla.extractBug(path, product);
            return true;
        }
        return false;
    }

    /**
     * Save the BugZilla Repository
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
            Logger.getLogger(BugZillaRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Serialized data is saved in " + filePath);
    }

    @Override
    public void printAllBugs() {
        for (Bug bug : bugs) {
            System.out.println(bug);
        }
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public List<Bug> getBugs() {
        return bugs;
    }

    @Override
    public void setProduct(String product) {
        this.product = product;
    }
}
