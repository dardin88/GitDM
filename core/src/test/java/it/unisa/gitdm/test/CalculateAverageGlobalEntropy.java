/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Entropy;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class CalculateAverageGlobalEntropy {

    public static void main(String[] args) {

        Entropy.calculateAverageGlobalEntropy("/home/sesa/Scrivania/averageGlobalEntropy.csv", "/home/sesa/Scrivania/globalEntropy_all.csv");

    }

}
