package it.unisa.gitdm.bean;

import java.io.Serializable;
import java.util.HashMap;

public class DeveloperTree implements Serializable {

    private final HashMap<String, ChangedClass> changedClassSet;
    private Developer dev;
    private int period;

    public DeveloperTree(Developer developer, int period) {
        super();
        this.dev = developer;
        this.period = period;
        this.changedClassSet = new HashMap<>();
    }

    public Developer getDeveloper() {
        return this.dev;
    }

    public void setDeveloper(Developer dev) {
        this.dev = dev;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void addChange(Change c) {
        String filePath = c.getFile().getPath();
        int numOfChanges = 1;
        if (this.changedClassSet.containsKey(filePath)) {
            numOfChanges = this.changedClassSet.get(filePath).getNumOfChanges() + 1;
        }
        ChangedClass changedClass = new ChangedClass(c.getFile(), numOfChanges);
        this.changedClassSet.put(filePath, changedClass);
    }

    public HashMap<String, ChangedClass> getChangeSet() {
        return this.changedClassSet;
    }

}
