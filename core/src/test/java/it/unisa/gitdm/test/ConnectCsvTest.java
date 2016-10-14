/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.CSV;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class ConnectCsvTest {

    public static void main(String[] args) {
        String path = "/home/sesa/Scrivania/stats/statistics_";
        String out = "/home/sesa/Scrivania/all.csv";
        int numOfAuthors = 50;

        CSV.connectCsvFiltered(path, numOfAuthors, out);

    }

}
