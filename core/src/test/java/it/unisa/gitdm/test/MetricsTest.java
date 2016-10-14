/**
 *
 */
package it.unisa.gitdm.test;

import it.unisa.gitdm.metrics.CKMetrics;
import it.unisa.gitdm.metrics.parser.bean.ClassBean;

import java.util.ArrayList;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
class MetricsTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ArrayList<ClassBean> code = new ArrayList<>();

        for (ClassBean c : code) {
            System.out.println("Class: " + c.getName());
            System.out.println("LOC: " + CKMetrics.getLOC(c));
            System.out.println("CBO: " + CKMetrics.getCBO(c));
            System.out.println("LCOM: " + CKMetrics.getLCOM(c));
            System.out.println("NOM: " + CKMetrics.getNOM(c));
            System.out.println("RFC: " + CKMetrics.getRFC(c));
            System.out.println("WMC: " + CKMetrics.getWMC(c));
        }

    }

}
