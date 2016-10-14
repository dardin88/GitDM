/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Process;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class AverageTest {

    public static void main(String[] args) {

        String path = "/home/sesa/Scrivania/stats/statistics_";
        String out = "/home/sesa/Scrivania/out.csv";

        Process process = new Process();

        process.calculateAverageLight(24, path, out);
    }

}
