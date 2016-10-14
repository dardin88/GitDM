/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Metrics;
import it.unisa.gitdm.algorithm.StructuralFocus;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class TestStructuralFocus {

    public static void main(String[] args) {
        int structuralFocus = 0;

        String pathFile1 = "/ciccio/pluto/pippo/Feola/Sab/Napo";
        String pathFile2 = "/ciccio/pluto/pippo/Annafe/Annibal/Hobbit";

        int index = StructuralFocus.greatestCommonPrefixIndex(pathFile1, pathFile2);

        System.out.println("Index: " + index);

        String differentPathFile1 = pathFile1.substring(index);
        String differentPathFile2 = pathFile2.substring(index);

        System.out.println("Path1: " + differentPathFile1);
        System.out.println("Path2: " + differentPathFile2);

        structuralFocus = structuralFocus + Metrics.numOfOccurrence(differentPathFile1, "/");
        structuralFocus = structuralFocus + Metrics.numOfOccurrence(differentPathFile2, "/");

        System.out.println("StructuralFocus: " + structuralFocus);
    }
}
