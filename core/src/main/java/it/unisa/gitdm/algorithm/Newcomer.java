/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.versioning.GitRepository;

/**
 *
 * @author fabiopalomba
 */
public class Newcomer {

    private final static int NUMBER_OF_COMMIT_NEWCOMER = 3;
    private GitRepository gitRepository;

    public Newcomer(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public boolean isNewComer(Author author, int lastCommitIndex) {
        int numberOfCommit = numberOfCommitForAuthor(author, lastCommitIndex);
        if (numberOfCommit > NUMBER_OF_COMMIT_NEWCOMER) {
            return false;
        } else {
            return true;
        }
    }

    public int numberOfCommitForAuthor(Author author, int lastCommitIndex) {
        int numberOfCommit = 0;

        for (int i = 0; i <= lastCommitIndex; i++) {
            Commit commit = gitRepository.getCommits().get(i);
            if (commit.getAuthor().equals(author)) {
                numberOfCommit++;
            }
        }

        return numberOfCommit;
    }
}
