package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.ChangedClass;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.bean.DeveloperTree;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.scattering.PeriodManager;
import it.unisa.gitdm.scattering.PeriodsListNonInitialized;
import it.unisa.gitdm.versioning.GitRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dario on 30/09/2015.
 */
public class ScatteringEntropy extends Entropy {

    public ScatteringEntropy(GitRepository repository) {
        super(repository);
    }

    //Could be depracated
    public double calculateMinSimilarityDeveloperTreeEntropy(DeveloperTree devTree)
            throws PeriodsListNonInitialized {
        List<Double> devtreeEntropy = new ArrayList<>();
        int period = devTree.getPeriod();
        List<Commit> periodCommits = PeriodManager.getPeriod(period)
                .getCommits();
        Commit lastDevTreeCommit = periodCommits.get(periodCommits.size() - 1);
        if (PeriodManager.getList() == null) {
            throw new PeriodsListNonInitialized();
        }
        List<FileBean> files = new ArrayList<>();
        for (ChangedClass c : devTree.getChangeSet().values()) {
            if (c.getClassFile().getPath().contains(".java")) {
                files.add(c.getClassFile());
            }
        }
        double[][] entropyMatrix = this.calculateDirectoryEntropy(files,
                lastDevTreeCommit);

        int startj = 1;
        for (double[] entropyMatrix1 : entropyMatrix) {
            for (int j = startj; j < entropyMatrix[0].length; j++) {
                devtreeEntropy.add(entropyMatrix1[j]);
            }
            startj++;
        }

        if (devtreeEntropy.size() > 0) {
            return (1 / min(devtreeEntropy)) * devTree.getChangeSet().size();
        }

        return 0;
    }

    //Could be depracated
    public double calculateStDevDeveloperTreeEntropy(DeveloperTree devTree)
            throws PeriodsListNonInitialized {
        List<Double> devtreeEntropy = new ArrayList<>();
        int period = devTree.getPeriod();
        List<Commit> periodCommits = PeriodManager.getPeriod(period)
                .getCommits();
        Commit lastDevTreeCommit = periodCommits.get(periodCommits.size() - 1);
        if (PeriodManager.getList() == null) {
            throw new PeriodsListNonInitialized();
        }
        List<FileBean> files = new ArrayList<>();
        for (ChangedClass c : devTree.getChangeSet().values()) {
            if (c.getClassFile().getPath().contains(".java")) {
                files.add(c.getClassFile());
            }
        }
        double[][] entropyMatrix = this.calculateDirectoryEntropy(files,
                lastDevTreeCommit);

        int startj = 1;
        for (double[] entropyMatrix1 : entropyMatrix) {
            for (int j = startj; j < entropyMatrix[0].length; j++) {
                devtreeEntropy.add(entropyMatrix1[j]);
            }
            startj++;
        }

        if (devtreeEntropy.size() > 0) {
            return (stDev(devtreeEntropy)) * devTree.getChangeSet().size();
        }

        return 0;
    }

    //Could be depracated
    public double calculateMedianDeveloperTreeEntropy(DeveloperTree devTree)
            throws PeriodsListNonInitialized {
        List<Double> devtreeEntropy = new ArrayList<>();
        int period = devTree.getPeriod();
        List<Commit> periodCommits = PeriodManager.getPeriod(period)
                .getCommits();
        Commit lastDevTreeCommit = periodCommits.get(periodCommits.size() - 1);
        if (PeriodManager.getList() == null) {
            throw new PeriodsListNonInitialized();
        }
        List<FileBean> files = new ArrayList<>();
        for (ChangedClass c : devTree.getChangeSet().values()) {
            if (c.getClassFile().getPath().contains(".java")) {
                files.add(c.getClassFile());
            }
        }
        double[][] entropyMatrix = this.calculateDirectoryEntropy(files,
                lastDevTreeCommit);

        int startj = 1;
        for (int i = 0; i < entropyMatrix.length; i++) {
            for (int j = startj; j < entropyMatrix[0].length; j++) {
                devtreeEntropy.add(entropyMatrix[i][j]);
            }
            startj++;
        }

        if (devtreeEntropy.size() > 0) {
            return (1 / median(devtreeEntropy)) * devTree.getChangeSet().size();
        }

        return 0;
    }

    public double calculateDeveloperSemanticScattering(DeveloperTree devTree)
            throws PeriodsListNonInitialized {
        List<Double> devtreeEntropy = new ArrayList<>();
        int period = devTree.getPeriod();
        List<Commit> periodCommits = PeriodManager.getPeriod(period)
                .getCommits();
        Commit lastDevTreeCommit = periodCommits.get(periodCommits.size() - 1);
        if (PeriodManager.getList() == null) {
            throw new PeriodsListNonInitialized();
        }
        List<FileBean> files = new ArrayList<>();
        for (ChangedClass c : devTree.getChangeSet().values()) {
            if (c.getClassFile().getPath().contains(".java")) {
                files.add(c.getClassFile());
            }
        }
        double[][] entropyMatrix = this.calculateDirectoryEntropy(files,
                lastDevTreeCommit);

        int startj = 1;
        for (int i = 0; i < entropyMatrix.length; i++) {
            for (int j = startj; j < entropyMatrix[0].length; j++) {
                devtreeEntropy.add(entropyMatrix[i][j]);
            }
            startj++;
        }

        if (devtreeEntropy.size() > 0) {
            return (1 / mean(devtreeEntropy)) * devTree.getChangeSet().size();
        }

        return 0;
    }

}
