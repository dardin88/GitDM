package it.unisa.gitdm.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Period {

    private final int id;
    private final List<Change> changes;
    private List<Commit> commits;

    public Period(int id, List<Change> changes, List<Commit> commits) {
        super();
        this.id = id;
        this.changes = changes;
        this.commits = commits;
    }

    public int getId() {
        return this.id;
    }

    public List<Change> getChangedClasses() {
        return this.changes;
    }

    public List<Commit> getCommits() {
        return this.commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public List<Commit> getCommits(Developer dev) {
        List<Commit> filteredCommits = new ArrayList<>();
        for (Commit commit : this.commits) {
            if (commit.getAuthor().getName().equals(dev.getName())) {
                filteredCommits.add(commit);
            }
        }
        return filteredCommits;
    }

    public List<Developer> getDevelopers() {
        List<Developer> developers = new ArrayList<>();
        
        for(Commit commit : this.commits) {
            if(! developers.contains(commit.getAuthor())) {
                developers.add(commit.getAuthor());
            }
        }
        
        return developers;
    }
    
    @Override
    public String toString() {
        return this.id
                + ","
                + new Date(this.commits.get(0).getAuthorTime() * 1000)
                + ","
                + new Date(this.commits.get(this.commits.size() - 1)
                .getAuthorTime() * 1000) + "," + this.changes.size();
    }
}
