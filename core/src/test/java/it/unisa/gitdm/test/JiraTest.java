package it.unisa.gitdm.test;

import it.unisa.gitdm.bean.Bug;
import it.unisa.gitdm.source.Jira;

import java.util.List;

class JiraTest {

    public static void main(String[] args) {
        try {
            System.out.println("Getting bugs from Jira");

            String address = "https://issues.apache.org/jira/";
            String project = "LUCENE";
            List<Bug> bugs = Jira.extractBug(address, project, true);

            for (Bug b : bugs) {
                System.out.println(b);
            }
            System.out.println("Number of bugs: " + bugs.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
