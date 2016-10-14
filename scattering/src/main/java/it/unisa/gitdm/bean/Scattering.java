package it.unisa.gitdm.bean;

public class Scattering {

    private final String developerName;
    private final int periodId;
    private final ScatteringType Type;
    private final int changes;
    private final int changedFile;
    private final double value;

    public Scattering(String developerName, int periodId,
                      ScatteringType type, int changes, int changedFiles, double value) {
        super();
        this.developerName = developerName;
        this.periodId = periodId;
        Type = type;
        this.changes = changes;
        this.changedFile = changedFiles;
        this.value = value;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public int getPeriodId() {
        return periodId;
    }

    public ScatteringType getType() {
        return Type;
    }

    public int getNumOfChanges() {
        return changes;
    }

    public int getNumOfChangedFiles() {
        return changedFile;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ScatteringMetric [developerName=" + developerName + ", periodId="
                + periodId + ", Type=" + Type + ", numOfChangedFiles="
                + changes + ", value=" + value + "]";
    }

}
