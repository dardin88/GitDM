/**
 *
 */
package it.unisa.gitdm.bean;

import java.util.List;
import java.util.Objects;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Bug implements java.io.Serializable {

    private String ID;
    private String subject;
    private long reportedTime;
    private long lastChangedTime;
    private String body;
    private Commit fix;
    private String product;
    private String component;
    private String assignedTo;
    private String status;
    private String resolution;
    private List<Commit> fixInducingChanges;

    @Override
    public String toString() {
        String tostring = "";
        tostring += "\nBug ID: " + this.ID;
        tostring += "\nProduct: " + this.product;
        tostring += "\nComponent: " + this.component;
        tostring += "\nReported Time: " + this.reportedTime;
        tostring += "\nLast Changed Time: " + this.lastChangedTime;
        tostring += "\nStatus: " + this.status;
        tostring += "\nResolution: " + this.resolution;
        tostring += "\nSubject: " + this.subject;
        // tostring += "\nFix Commit: "+fix.getCommitHash();

        return tostring;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String iD) {
        this.ID = iD;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Commit getFix() {
        return this.fix;
    }

    public void setFix(Commit fix) {
        this.fix = fix;
    }

    public List<Commit> getFixInducingChanges() {
        return this.fixInducingChanges;
    }

    public void setFixInducingChanges(List<Commit> inducingFixChanges) {
        this.fixInducingChanges = inducingFixChanges;
    }

    public long getReportedTime() {
        return this.reportedTime;
    }

    public void setReportedTime(long reportedTime) {
        this.reportedTime = reportedTime;
    }

    public long getLastChangedTime() {
        return this.lastChangedTime;
    }

    public void setLastChangedTime(long lastChangedTime) {
        this.lastChangedTime = lastChangedTime;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getComponent() {
        return this.component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getAssignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Bug) {
            Bug toCompare = (Bug) obj;
            return this.ID.equals(toCompare.getID());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.ID);
        return hash;
    }

}
