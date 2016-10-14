/**
 *
 */
package it.unisa.gitdm.source;

import it.unisa.gitdm.algorithm.CommitGoalTagger;
import it.unisa.gitdm.bean.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Git {

    public static void clone(String url, boolean isSVN, String projectName, String dir) throws IOException, InterruptedException {
        System.out.println("Start cloning into " + dir);
        String[] cmd;
        File file = new File(dir);
        if (file.exists()) {
            if (isSVN) {
                cmd = new String[]{"git", "svn", "clone", url, projectName};
            } else {
                cmd = new String[]{"git", "clone", url, projectName};
            }
            Process p = Runtime.getRuntime().exec(cmd, null, file);
            p.waitFor();
        }
        System.out.println("End cloning");
    }

    public static List<Commit> extractCommit(File directory, boolean masterOnly) {
        String log = null;
        if (masterOnly) {
            try {
                log = generateLogFileMasterOnly(directory);
            } catch (IOException ex) {
                Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            log = generateLogFile(directory);
        }

        return generateCommits(directory, log);
    }

    public static String generateLogFileMasterOnly(File directory) throws IOException {
        // String cmd =
        // "git --git-dir "+directory.getAbsolutePath().replace(" ",
        // "\\ ")+"/.git log --format=%H;%h;%an;%ae;%at;%cn;%ce;%ct;%s;%b-?end?";

        String dir = directory.getAbsolutePath() + "/.git";
        String[] cmd = new String[]{"git", "--git-dir", dir, "log",
                "--first-parent", "--reverse",
                "--format=%H;%h;%an;%ae;%at;%cn;%ce;%ct;%s;%b-?end?"};
        String line;
        String log = "";
        Process p = Runtime.getRuntime().exec(cmd);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                p.getInputStream()))) {
            while ((line = in.readLine()) != null) {
                log += line;
            }
            log = formatLogContent(log);
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }

    private static String generateLogFile(File directory) {
        // String cmd =
        // "git --git-dir "+directory.getAbsolutePath().replace(" ",
        // "\\ ")+"/.git log --format=%H;%h;%an;%ae;%at;%cn;%ce;%ct;%s;%b-?end?";

        String dir = directory.getAbsolutePath() + "/.git";
        String[] cmd = new String[]{"git", "--git-dir", dir, "log",
                "--reverse",
                "--format=%H;%h;%an;%ae;%at;%cn;%ce;%ct;%s;%b-?end?"};
        String line;
        String log = "";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    log += line;
                }
                log = formatLogContent(log);
            }
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }

    private static List<Commit> generateCommits(File directory, String log) {
        List<Commit> listOfCommits = new ArrayList<>();
        int index = 0;
        try (Scanner scanner = new Scanner(log)) {
            while (scanner.hasNextLine()) {
                System.out.println("commit: " + index++);
                String line = scanner.nextLine();
                String[] commitInfo = line.split(";");
                if (commitInfo.length == 10) {
                    Commit commit = new Commit();
                    commit.setCommitHash(commitInfo[0]);
                    commit.setAbbreviateCommitHash(commitInfo[1]);
                    Author author = new Author();
                    author.setName(commitInfo[2]);
                    author.setEmail(commitInfo[3]);
                    commit.setAuthor(author);
                    commit.setAuthorTime(Integer.parseInt(commitInfo[4]));
                    Committer committer = new Committer();
                    committer.setName(commitInfo[5]);
                    committer.setEmail(commitInfo[6]);
                    commit.setCommitter(committer);
                    commit.setCommitterTime(Integer.parseInt(commitInfo[7]));
                    commit.setSubject(commitInfo[8]);
                    if (commitInfo[9] == null) {
                        commit.setBody("");
                    }
                    commit.setBody(commitInfo[9]);
                    if (CommitGoalTagger.isFeatureIntroduction(commit)) {
                        commit.setFeatureIntroduction(true);
                    } else {
                        commit.setFeatureIntroduction(false);
                    }

                    // generateChanges
                    commit.setChanges(generateChanges(directory, commit));

                    listOfCommits.add(commit);
                } else if (commitInfo.length == 9) {
                    Commit commit = new Commit();
                    commit.setCommitHash(commitInfo[0]);
                    commit.setAbbreviateCommitHash(commitInfo[1]);
                    Author author = new Author();
                    author.setName(commitInfo[2]);
                    author.setEmail(commitInfo[3]);
                    commit.setAuthor(author);
                    commit.setAuthorTime(Integer.parseInt(commitInfo[4]));
                    Committer committer = new Committer();
                    committer.setName(commitInfo[5]);
                    committer.setEmail(commitInfo[6]);
                    commit.setCommitter(committer);
                    commit.setCommitterTime(Integer.parseInt(commitInfo[7]));
                    commit.setSubject(commitInfo[8]);
                    commit.setBody(""); // 9 campi. Non ha il body
                    if (CommitGoalTagger.isFeatureIntroduction(commit)) {
                        commit.setFeatureIntroduction(true);
                    } else {
                        commit.setFeatureIntroduction(false);
                    }

                    // generateChanges
                    commit.setChanges(generateChanges(directory, commit));

                    listOfCommits.add(commit);
                } else if (commitInfo.length > 9) {
                    Commit commit = new Commit();
                    commit.setCommitHash(commitInfo[0]);
                    commit.setAbbreviateCommitHash(commitInfo[1]);
                    Author author = new Author();
                    author.setName(commitInfo[2]);
                    author.setEmail(commitInfo[3]);
                    commit.setAuthor(author);
                    commit.setAuthorTime(Integer.parseInt(commitInfo[4]));
                    Committer committer = new Committer();
                    committer.setName(commitInfo[5]);
                    committer.setEmail(commitInfo[6]);
                    commit.setCommitter(committer);
                    commit.setCommitterTime(Integer.parseInt(commitInfo[7]));
                    commit.setSubject(commitInfo[8]);

                    // Più di 10 campi
                    String body = "";
                    for (int i = 9; i < commitInfo.length; i++) {
                        body += commitInfo[i];
                    }
                    commit.setBody(body); // Modificato

                    if (CommitGoalTagger.isFeatureIntroduction(commit)) {
                        commit.setFeatureIntroduction(true);
                    } else {
                        commit.setFeatureIntroduction(false);
                    }

                    // generateChanges
                    commit.setChanges(generateChanges(directory, commit));

                    listOfCommits.add(commit);
                }
            }
        }
        return listOfCommits;
    }

    private static List<Change> generateChanges(File directory, Commit commit) {
        // String cmd =
        // "git --git-dir "+directory.getAbsolutePath()+"/.git show --pretty=format: --name-only "+commit.getCommitHash();

        List<Change> listOfChanges = new ArrayList<>();

        String line;
        boolean skip = true; // skip first \n line
        String dir = directory.getAbsolutePath() + "/.git";

        String[] cmd = new String[]{"git", "--git-dir", dir, "show",
                "--pretty=format:", "--name-only", commit.getCommitHash()};
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    if (skip) {
                        skip = false;
                        continue;
                    }

                    Change change = new Change();
                    FileBean file = new FileBean();
                    file.setPath(line);
                    change.setFile(file);

                    // calculateTouchedLines
                    calculateTouchedLines(directory, commit, change);

                    listOfChanges.add(change);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listOfChanges;
    }

    private static void calculateTouchedLines(File directory, Commit commit,
                                              Change change) {
        // String cmd =
        // "git --git-dir "+directory.getAbsolutePath()+"/.git diff --word-diff -w --unified="+Integer.MAX_VALUE+" "+commit.getCommitHash()+"^:"+change.getFile().getPath().replace(" ",
        // "\\ ")+" "+commit.getCommitHash()+":"+change.getFile().getPath().replace(" ",
        // "\\ ");

        String line;
        boolean start = false;
        int lineNumber = 0;
        List<Integer> addedlines = new ArrayList<>();
        List<Integer> removedlines = new ArrayList<>();
        List<Integer> modifiedlines = new ArrayList<>();
        String MOD_PATTERN = "^.+(\\[-|\\{\\+).*$";
        String ADD_PATTERN = "^\\{\\+.*\\+\\}$";
        String REM_PATTERN = "^\\[-.*-\\]$";

        String dir = directory.getAbsolutePath() + "/.git";
        String unified = "--unified=" + Integer.MAX_VALUE;
        String file1 = commit.getCommitHash() + "^:"
                + change.getFile().getPath();
        String file2 = commit.getCommitHash() + ":"
                + change.getFile().getPath();
        String[] cmd = new String[]{"git", "--git-dir", dir, "diff",
                "--word-diff", "-w", unified, file1, file2};

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    if (start) {
                        lineNumber++;
                        if (line.matches(MOD_PATTERN)) {
                            modifiedlines.add(lineNumber);
                        } else if (line.matches(ADD_PATTERN)) {
                            addedlines.add(lineNumber);
                        } else if (line.matches(REM_PATTERN)) {
                            removedlines.add(lineNumber);
                        }
                    } else if (line.startsWith("@")) {
                        start = true;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }

        change.setModifiedlines(modifiedlines);
        change.setAddedlines(addedlines);
        change.setRemovedlines(removedlines);

    }

    public static void gitCheckout(File directory, Commit commit,
                                   File workingDirectory) {
        String commitID = commit.getCommitHash();
        String dir = directory.getAbsolutePath() + "/.git";
        String workDir = workingDirectory.getAbsolutePath();

        String[] cmd = new String[]{"git", "--git-dir", dir, "--work-tree",
                workDir, "checkout", commitID};

        try {
            for(String s: cmd)
                System.out.println(s);
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<FileBean> gitList(File directory, Commit commit, File workingDirectory) {
        String commitID = commit.getCommitHash();
        String dir = directory.getAbsolutePath() + "/.git";
        String workDir = workingDirectory.getAbsolutePath();
        ArrayList<FileBean> files = new ArrayList<>();
        String line;

        String cmd = "git --git-dir " + dir + " --work-tree " + workDir + " ls-tree -r --name-only " + commitID;

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    files.add(new FileBean(line.split(" ")[0]));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        return files;
    }
    
    public static List<FileBean> gitList(File directory) {
        String dir = directory.getAbsolutePath() + "/.git";
        ArrayList<FileBean> files = new ArrayList<>();
        String line;

        String cmd = "git --git-dir " + dir + " ls-tree -r master --name-only";

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    files.add(new FileBean(line.split(" ")[0]));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        return files;
    }

    public static void gitReset(File directory) {
        String dir = directory.getAbsolutePath() + "/.git";

        String[] cmd = new String[]{"git", "--git-dir", dir, "reset --hard origin/HEAD"};

        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String gitBlame(File directory, Commit commit, int lineNumber, String fileName) {
        String line;
        String commitToReturn = "";

        String commitID = commit.getCommitHash() + "^";
        String dir = directory.getAbsolutePath() + "/.git";
        String optionLineNumber = lineNumber + ",+1";
        String[] cmd = new String[]{"git", "--git-dir", dir, "blame", "-l",
                "-s", "-w", "-L", optionLineNumber, commitID, "--", fileName};

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    commitToReturn = line.split(" ")[0];
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commitToReturn;
    }

    public static String gitShow(File directory, FileBean file, Commit commit) {
        // String cmd =
        // "git --git-dir "+directory.getAbsolutePath()+"/.git show "+commit.getCommitHash()+":"+file.getPath();

        String line;
        String content = "";

        String dir = directory.getAbsolutePath() + "/.git";
        String filePath = commit.getCommitHash() + ":" + file.getPath();

        String[] cmd = new String[]{"git", "--git-dir", dir, "show", filePath};

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()))) {
                while ((line = in.readLine()) != null) {
                    content += line;
                }
            }
            p.destroy();
        } catch (IOException ex) {
            Logger.getLogger(Git.class.getName()).log(Level.SEVERE, null, ex);
        }

        return content;
    }

    private static String formatLogContent(String content) {
        String step1 = content.replace("\n", " ");
        return step1.replace("-?end?", "\n");
    }

    public static void clean(File directory) {
        String dir = directory.getAbsolutePath() + "/.git";
        String[] cmd = new String[]{"git", "--git-dir", dir, "gc"};

        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        p.destroy();
    }
}
