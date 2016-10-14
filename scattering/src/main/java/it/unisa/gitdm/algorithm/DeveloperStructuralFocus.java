package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.ChangedClass;
import it.unisa.gitdm.bean.DeveloperTree;
import it.unisa.gitdm.bean.FileBean;
import it.unisa.gitdm.versioning.GitRepository;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dario on 30/09/2015.
 */
public class DeveloperStructuralFocus extends StructuralFocus {

    public DeveloperStructuralFocus(GitRepository gitRepository) {
        super(gitRepository);
    }

    //Could be depracated
    public double calculateMaxDistanceStructuralFocusInDeveloperTree(DeveloperTree devTree) {
        ArrayList<Integer> distribution = new ArrayList<>();
        int totalStructuralFocus = 0;

        for (ChangedClass change1 : devTree.getChangeSet().values()) {
            FileBean f1 = change1.getClassFile();
            for (ChangedClass change2 : devTree.getChangeSet().values()) {
                if (!change1.equals(change2)) {
                    FileBean f2 = change2.getClassFile();
                    distribution.add(calculateStructuralFocusBetweenFiles(f1, f2));

                    totalStructuralFocus = totalStructuralFocus
                            + calculateStructuralFocusBetweenFiles(f1, f2);
                }
            }
        }

        Collections.sort(distribution);

        if (distribution.size() > 0) {
            return distribution.get(distribution.size() - 1) * devTree.getChangeSet().size();
        }

        return 0;

    }

    //Could be depracated
    public double calculateStDevStructuralFocusInDeveloperTree(DeveloperTree devTree) {
        ArrayList<Integer> distribution = new ArrayList<>();
        int totalStructuralFocus = 0;
        double mean = 0.0;
        int num = 0;

        for (ChangedClass change1 : devTree.getChangeSet().values()) {
            FileBean f1 = change1.getClassFile();
            for (ChangedClass change2 : devTree.getChangeSet().values()) {
                if (!change1.equals(change2)) {
                    FileBean f2 = change2.getClassFile();
                    distribution.add(calculateStructuralFocusBetweenFiles(f1, f2));

                    totalStructuralFocus = totalStructuralFocus
                            + calculateStructuralFocusBetweenFiles(f1, f2);
                    num++;
                }
            }
        }

        Collections.sort(distribution);

        if (num > 0) {
            mean = (double) totalStructuralFocus / num;
        }

        double sd = 0;

        for (Integer value : distribution) {
            sd = sd + Math.pow(value - mean, 2);
        }

        return Math.sqrt(sd / (distribution.size() - 1)) * devTree.getChangeSet().size();

    }

    //Could be depracated
    public double calculateMedianStructuralFocusInDeveloperTree(DeveloperTree devTree) {
        ArrayList<Integer> distribution = new ArrayList<>();

        for (ChangedClass change1 : devTree.getChangeSet().values()) {
            FileBean f1 = change1.getClassFile();
            for (ChangedClass change2 : devTree.getChangeSet().values()) {
                if (!change1.equals(change2)) {
                    FileBean f2 = change2.getClassFile();
                    // Median: Non fare la somma, ma metti i valori in un array e calcola la mediana
                    // St. Dev: Prendi la deviazione standard della distribuzione
                    // Max Distance: Di questa distribuzione, prendo il max
                    distribution.add(calculateStructuralFocusBetweenFiles(f1, f2));
                }
            }
        }

        Collections.sort(distribution);

        if (distribution.size() > 0) {

            if ((distribution.size() / 2) % 2 == 0) {
                return distribution.get(distribution.size() / 2) * devTree.getChangeSet().size();
            } else {
                return distribution.get(((distribution.size() / 2) + ((distribution.size() + 1) / 2)) / 2) * devTree.getChangeSet().size();
            }
        }

        return 0;

    }

    //Could be depracated
    public double calculateDeveloperStructuralFocus(DeveloperTree devTree) {
        int totalStructuralFocus = 0;
        int num = 0;

        for (ChangedClass change1 : devTree.getChangeSet().values()) {
            FileBean f1 = change1.getClassFile();
            for (ChangedClass change2 : devTree.getChangeSet().values()) {
                if (!change1.equals(change2)) {
                    FileBean f2 = change2.getClassFile();
                    totalStructuralFocus = totalStructuralFocus
                            + calculateStructuralFocusBetweenFiles(f1, f2);
                    num++;
                }
            }
        }

        double structuralFocus = 0;
        if (num > 0) {
            structuralFocus = (double) totalStructuralFocus / num;
        }

        structuralFocus *= devTree.getChangeSet().size();

        return structuralFocus;
    }
}
