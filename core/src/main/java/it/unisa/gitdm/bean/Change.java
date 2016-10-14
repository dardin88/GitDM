/**
 *
 */
package it.unisa.gitdm.bean;

import java.util.List;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Change implements java.io.Serializable {

    private FileBean file;
    private List<Integer> addedlines;
    private List<Integer> removedlines;
    private List<Integer> modifiedlines;

    public FileBean getFile() {
        return this.file;
    }

    public void setFile(FileBean file) {
        this.file = file;
    }

    public List<Integer> getAddedlines() {
        return this.addedlines;
    }

    public void setAddedlines(List<Integer> addedlines) {
        this.addedlines = addedlines;
    }

    public List<Integer> getRemovedlines() {
        return this.removedlines;
    }

    public void setRemovedlines(List<Integer> removedlines) {
        this.removedlines = removedlines;
    }

    public List<Integer> getModifiedlines() {
        return this.modifiedlines;
    }

    public void setModifiedlines(List<Integer> modifiedlines) {
        this.modifiedlines = modifiedlines;
    }

}
