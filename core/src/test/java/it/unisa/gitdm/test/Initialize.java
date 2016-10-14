package it.unisa.gitdm.test;

import it.unisa.gitdm.algorithm.Process;

class Initialize {

    public static void main(String[] args) {
        String projectName = "ant";
        String baseFolderPath = "/media/Dati/Repo/";
        Process process = new Process();
        process.initGitRepository(baseFolderPath + projectName + "/");
        process.saveGitRepository(baseFolderPath + projectName
                + "/gitRepository.data");
    }
}
