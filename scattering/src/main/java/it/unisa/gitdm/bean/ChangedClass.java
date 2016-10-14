package it.unisa.gitdm.bean;

import java.io.Serializable;
import java.util.Objects;

public class ChangedClass implements Serializable {

    private final FileBean classFile;
    private int numOfChanges;

    public ChangedClass(FileBean classFile, int numOfChanges) {
        super();
        this.classFile = classFile;
        this.numOfChanges = numOfChanges;
    }

    public FileBean getClassFile() {
        return this.classFile;
    }

    public int getNumOfChanges() {
        return this.numOfChanges;
    }

    public void setNumOfChanges(int numOfChanges) {
        this.numOfChanges = numOfChanges;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChangedClass) {
            ChangedClass compareTo = (ChangedClass) obj;
            return this.getClassFile().getPath()
                    .equals(compareTo.getClassFile().getPath());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.classFile);
        return hash;
    }

}
