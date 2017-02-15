package it.unisa.gitdm.source;

import it.unisa.gitdm.algorithm.CommitGoalTagger;
import it.unisa.gitdm.bean.*;
import it.unisa.gitdm.gitException.CommitHashShortException;
import it.unisa.gitdm.gitException.CommitNotFound;
import it.unisa.gitdm.gitException.CommithashInvalidFormatException;
import it.unisa.gitdm.gitException.CommithashLongException;
import it.unisa.gitdm.gitException.FileNameInvalidFormatException;
import it.unisa.gitdm.gitException.FileNameShortException;
import it.unisa.gitdm.gitException.InvalidNumberLineException;
import it.unisa.gitdm.gitException.ProjectNameInvalidFormatExceptions;
import it.unisa.gitdm.gitException.ProjectNameShortException;
import it.unisa.gitdm.gitException.UrlInvalidFormatExceptions;
import it.unisa.gitdm.gitException.UrlNullException;
import it.unisa.gitdm.gitException.UrlshortException;
import it.unisa.gitdm.gitException.ValueNumberLineOutOfRangeException;
import it.unisa.gitdm.gitException.DirectoryInvalidFormatException;
import it.unisa.gitdm.gitException.DirectoryNotFound;
import it.unisa.gitdm.gitException.DirectoryshortException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import org.eclipse.jgit.api.BlameCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 *
 * @author Vincenzo
 */
public class NewGit {

    public static void clone(String url, boolean isSVN, String projectName, String dir) throws IOException, InterruptedException {
        if (dir == null || dir.isEmpty()) {
            throw new DirectoryNotFound("specificare nome directory");
        }
        if (url == null) {
            throw new UrlNullException("url non valido");
        }
        url = url.replaceAll(" ", "");
        projectName = projectName.replaceAll(" ", "");
        dir = dir.replaceAll(" ", "");
        if (url.length() < 18) {
            throw new UrlshortException("url non copre la distanza minima");
        }
        String formatoUrl = url.substring(0, 18);
        if (!formatoUrl.equalsIgnoreCase("https://github.com")) {
            throw new UrlInvalidFormatExceptions("formato url non valido");
        }
        if (projectName == null) {
            throw new IIOException("input non valido");
        }
        if (projectName.isEmpty() || projectName.length() < 2) {
            throw new ProjectNameShortException("la lunghezza del project name non copre la distanza minima");
        }
        if (letterOrDigit(projectName) == false) {
            throw new ProjectNameInvalidFormatExceptions("formato project name non valido");
        }

        if (dir.length() < 2) {
            throw new DirectoryshortException("la lunghezza di directory  non copre la distanza minima");
        }
        if (formatFile(dir) == false) {
            throw new DirectoryInvalidFormatException("formato directory non valido");
        }

        System.out.println("Start cloning into " + dir);
        File file = new File(dir);
        Git clone;

        if (isSVN) {
            String[] cmd;
            cmd = new String[]{"git", "svn", "clone", url, projectName};
            Process p = Runtime.getRuntime().exec(cmd, null, file);
            p.waitFor();

        } else {
            dir += "\\" + projectName;
            File filename = new File(dir);

            try {

                clone = Git.cloneRepository()
                        .setURI(url)
                        .setDirectory(filename)
                        .call();
            } catch (GitAPIException ex) {

                Logger.getLogger(NewGit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("End cloning");
    }

    public static List<Commit> extractCommit(File directory, boolean masterOnly) throws GitAPIException, IOException {
        if (directory == null || !directory.exists()) {
            throw new FileNotFoundException("File non esistente");
        }
        Git git;
        String log = null;
        if (masterOnly) {
            try {
                log = generateLogFileMasterOnly(directory);
            } catch (IOException ex) {

                Logger.getLogger(it.unisa.gitdm.source.Git.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            log = generateLogFile(directory);
        }

        return generateCommits(directory, log);
    }

    public static String generateLogFileMasterOnly(File directory) throws IOException, GitAPIException {
        if (directory == null || !directory.exists()) {
            throw new FileNotFoundException("File non esistente");
        }

        Git git = Git.init().setDirectory(directory).call();
        Repository repository = FileRepositoryBuilder.create((new File(directory, ".git")));

        Iterable<RevCommit> logs;

        logs = git.log()
                .add(repository.resolve("refs/heads/master"))
                .call();
        ArrayList<String> reverse = new ArrayList<>();

        String hash;

        String abbreviate;
        int k = 0;
        String log;

        for (RevCommit rev : logs) {

            hash = "" + rev.getId().getName();

            abbreviate = hash.substring(0, 7);

            log = rev.getId().getName() + ";" + abbreviate + ";" + rev.getAuthorIdent().getName() + ";"
                    + rev.getAuthorIdent().getEmailAddress() + ";" + rev.getCommitTime() + ";"
                    + rev.getCommitterIdent().getName() + ";" + rev.getCommitterIdent().getEmailAddress() + ";"
                    + rev.getCommitTime() + ";" + rev.getShortMessage() + ";" + rev.getFullMessage() + "-?end?";

            reverse.add(k++, log);

        }

        int lunghezza = reverse.size() - 1;

        log = "";

        for (int j = lunghezza; j >= 0; j--) {

            log += reverse.get(j);

        }

        log = formatLogContent(log);

        return log;

    }

    private static String generateLogFile(File directory) throws GitAPIException, IOException {

        Git git = Git.init().setDirectory(directory).call();
        Iterable<RevCommit> logs;

        logs = git.log()
                .all()
                .call();
        String log;

        ArrayList<String> reverse = new ArrayList<>();
        int k = 0;
        String hash;

        String abbreviate;

        for (RevCommit rev : logs) {

            hash = "" + rev;
            abbreviate = hash.substring(7, 14);
            hash = hash.substring(7, 47);

            log = (hash + ";" + abbreviate + ";" + rev.getAuthorIdent().getName() + ";"
                    + rev.getAuthorIdent().getEmailAddress() + ";" + rev.getCommitTime() + ";"
                    + rev.getCommitterIdent().getName() + ";" + rev.getCommitterIdent().getEmailAddress() + ";" + rev.getCommitTime()
                    + ";" + rev.getShortMessage() + ";" + rev.getFullMessage() + "-?end?");

            reverse.add(k++, log);

        }
        int j;
        int lunghezza = reverse.size() - 1;
        log = "";
        for (j = lunghezza; j >= 0; j--) {

            log += reverse.get(j);

        }

        log = formatLogContent(log);

        return log;
    }

    private static List<Commit> generateCommits(File directory, String log) throws GitAPIException, IOException {

        List<Commit> listOfCommits = new ArrayList<>();

        try (Scanner scanner = new Scanner(log)) {
            while (scanner.hasNextLine()) {
                //   System.out.println("commit: " + index++);
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

    private static List<Change> generateChanges(File directory, Commit commit) throws GitAPIException, IOException {
        Git git = Git.init().setDirectory(directory).call();
        String line;
        List<Change> listOfChanges = new ArrayList<>();
        Repository repository = FileRepositoryBuilder.create((new File(directory, ".git")));

        ObjectId commitId = repository.resolve(commit.getCommitHash());

        RevWalk revWalk = new RevWalk(repository);
        RevCommit commits = revWalk.parseCommit(commitId);

        RevTree tree = commits.getTree();

        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(false);

        while (treeWalk.next()) {
            line = treeWalk.getPathString();
            System.out.println(line);
            Change change = new Change();
            FileBean file = new FileBean();
            file.setPath(line);
            change.setFile(file);

            // calculateTouchedLines
            calculateTouchedLines(directory, commit, change);

            listOfChanges.add(change);

        }

        return listOfChanges;

    }

    private static void calculateTouchedLines(File directory, Commit commit, Change change) throws IOException, GitAPIException {

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
            Logger.getLogger(it.unisa.gitdm.source.Git.class.getName()).log(Level.SEVERE, null, ex);
        }

        change.setModifiedlines(modifiedlines);
        change.setAddedlines(addedlines);
        change.setRemovedlines(removedlines);

        /*   Git git = Git.init().setDirectory(directory).call();
        Repository repository = FileRepositoryBuilder.create((new File(directory, ".git")));
         RevWalk walk = new RevWalk(repository);
         RevCommit commits = walk.parseCommit(ObjectId.fromString(commit.getCommitHash()));
        RevTree tree = walk.parseTree(commits.getTree().getId());

      
        String file1 =commit.getCommitHash() + "^:" + change.getFile().getPath();
        String file2 =commit.getCommitHash() + ":" + change.getFile().getPath();
     
       ObjectId oldHead = repository.resolve(file1);
       ObjectId head = repository.resolve(file2);

      //   ObjectId oldHead = repository.resolve("HEAD^^^^{tree}");
        //  ObjectId head = repository.resolve("HEAD^{tree}");
        System.out.println("Printing diff between tree: " + oldHead.getName() + " and " + head.getName());

        // prepare the two iterators to compute the diff between
        ObjectReader reader = repository.newObjectReader();
        CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
        oldTreeIter.reset(reader, oldHead);
        CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
        newTreeIter.reset(reader, head);

        // finally get the list of changed files
        List<DiffEntry> diffs = git.diff()
                .setNewTree(newTreeIter)
                .setOldTree(oldTreeIter)
                //                    .setPathFilter(PathFilter.create(change.getFile().getPath()))
                .call();

        for (DiffEntry entry : diffs) {
            //
            System.out.println("Entry: " + entry + "  old " + entry.getOldId() + "  NEW " + entry.getNewId());
            DiffFormatter formatter = new DiffFormatter(System.out);
            formatter.setRepository(repository);
            formatter.format(entry);
        }*/
    }

    public static void gitCheckout(File directory, Commit commit) throws GitAPIException, IOException {
        if (commit == null) {
            throw new CommitNotFound("commit non esiste");
        }
        if (directory == null || !directory.exists()) {
            throw new FileNotFoundException("File non esistente");
        }

        if (commit.getCommitHash().length() < 40) {
            throw new CommitHashShortException("commithash troppo breve, deve avere 40 caratteri");
        }
        if (commit.getCommitHash().length() > 40) {
            throw new CommithashLongException("commithash troppo lungo, deve avere 40 caratteri");
        }
        if (letterOrDigit(commit.getCommitHash()) == false) {
            throw new CommithashInvalidFormatException("Formato commitHash non valido");
        }

        Git git = Git.init().setDirectory(directory).call();

        CheckoutCommand checkout = git.checkout();
        checkout.setStartPoint(commit.getCommitHash())
                .setAllPaths(true)
                .call();

    }

    public static List<FileBean> gitList(File directory, Commit commit) throws IOException {
        if (commit == null) {
            throw new CommitNotFound("commit non esiste");
        }
        if (directory == null || !directory.exists()) {
            throw new FileNotFoundException("File non esistente");
        }

        if (commit.getCommitHash().length() < 40) {
            throw new CommitHashShortException("commithash troppo breve, deve avere 40 caratteri");
        }
        if (commit.getCommitHash().length() > 40) {
            throw new CommithashLongException("commithash troppo lungo, deve avere 40 caratteri");
        }
        if (letterOrDigit(commit.getCommitHash()) == false) {
            throw new CommithashInvalidFormatException("Formato commitHash non valido");
        }

        ArrayList<FileBean> files = new ArrayList<>();

        Repository repository = FileRepositoryBuilder.create((new File(directory, ".git")));
        ObjectId CommitId = repository.resolve(commit.getCommitHash());
        RevWalk revWalk = new RevWalk(repository);
        RevCommit commits = revWalk.parseCommit(CommitId);
        RevTree tree = commits.getTree();
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);

        while (treeWalk.next()) {
            files.add(new FileBean(treeWalk.getPathString()));
            System.out.println(treeWalk.getPathString());
        }

        return files;

    }

    public static List<FileBean> gitList(File directory) throws IOException {
        if (!directory.exists()) {
            throw new FileNotFoundException("il file non esiste");
        }

        ArrayList<FileBean> files = new ArrayList<>();
        String line;
        Repository repository = FileRepositoryBuilder.create((new File(directory, ".git")));
        Ref head = repository.exactRef("refs/heads/master");
        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(head.getObjectId());
        RevTree tree = commit.getTree();
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);

        while (treeWalk.next()) {
            files.add(new FileBean(treeWalk.getPathString()));
            System.out.println(treeWalk.getPathString());
        }

        return files;
    }

    public static void gitReset(File directory) throws GitAPIException, FileNotFoundException {
        if (!directory.exists()) {
            throw new FileNotFoundException("il file non esiste");
        }
        //String dir = directory.getAbsolutePath() + "/.git";
        Git git = Git.init().setDirectory(directory).call();
        git.reset().setMode(ResetType.HARD)
                .setRef("refs/heads/master")
                .call();

    }

    public static String gitBlame(File directory, Commit commit, int lineNumber, String fileName) throws IOException, GitAPIException {
        if (fileName == null) {
            throw new FileNotFoundException("il file non esiste");
        }

        if (commit == null) {
            throw new CommitNotFound("commit non esiste");
        }
        if (directory == null || !directory.exists()) {
            throw new FileNotFoundException("File non esistente");
        }

        if (commit.getCommitHash().length() < 40) {
            throw new CommitHashShortException("commithash troppo breve, deve avere 40 caratteri");
        }
        if (commit.getCommitHash().length() > 40) {
            throw new CommithashLongException("commithash troppo lungo, deve avere 40 caratteri");
        }
        if (letterOrDigit(commit.getCommitHash()) == false) {
            throw new CommithashInvalidFormatException("Formato commitHash non valido");
        }

        fileName = fileName.replaceAll(" ", "");

        if (lineNumber == 0) {
            throw new InvalidNumberLineException("inserire un valore maggiore di 0");
        }

        if (fileName.length() < 2) {
            throw new FileNameShortException("il file non copre la distanza minima");
        }

        if (formatFile(fileName) == false) {
            throw new FileNameInvalidFormatException("il file non rispecchia il formato");
        }
        Repository repository = FileRepositoryBuilder.create((new File(directory, ".git")));
        String content = "";
        String com = "";
        String commitToReturn = "";
        BlameCommand blamer = new BlameCommand(repository);
        ObjectId commiitID = repository.resolve(commit.getCommitHash());
        blamer.setStartCommit(commiitID);
        blamer.setFilePath(fileName);
        BlameResult result = blamer.call();
        if (result == null) {
            throw new FileNotFoundException("file non esite");
        }
        RawText rawText = result.getResultContents();
        if (lineNumber > rawText.size()) {
            throw new ValueNumberLineOutOfRangeException("valore maggiore lunghezza file");
        }
        if (lineNumber > 0) {
            String comlist = "";
            for (int i = 0; i < rawText.size(); i++) {

                comlist = "" + result.getSourceCommit(i);

            }
            int c = lineNumber;

            commitToReturn = "" + result.getSourceCommit(c - 1);

            String prova = rawText.getString(c - 1);
            commitToReturn = commitToReturn.substring(7, 47);

            System.out.println(commitToReturn + " " + c + ") " + prova);
        }

        return commitToReturn;
    }

    public static String gitShow(File directory, FileBean file, Commit commit) throws IOException, GitAPIException {

        if (commit == null) {
            throw new CommitNotFound("commit non valido");
        }
        if (file == null || file.getPath().length() < 2) {
            throw new FileNameShortException("la stringa non copre la distanza minima");
        }
        if (directory == null || !directory.exists()) {
            throw new FileNotFoundException("il file non esiste!!");
        }

        if (formatFile(file.getPath()) == false) {
            throw new FileNameInvalidFormatException("formato file non valido");
        }

        if (commit.getCommitHash().length() < 40) {
            throw new CommitHashShortException("commithash troppo breve, deve avere 40 caratteri");
        }
        if (commit.getCommitHash().length() > 40) {
            throw new CommithashLongException("commithash troppo lungo, deve avere 40 caratteri");
        }
        if (letterOrDigit(commit.getCommitHash()) == false) {
            throw new CommithashInvalidFormatException("Formato commitHash non valido");
        }

        String filevuoto = file.getPath().replaceAll(" ", "");
        if (filevuoto.length() < 1) {
            throw new FileNameShortException("la stringa non copre la distanza minima");
        }

        Repository repository = FileRepositoryBuilder.create((new File(directory, ".git")));
        String content = "";
        BlameCommand blamer = new BlameCommand(repository);
        ObjectId commitID = repository.resolve(commit.getCommitHash());
        blamer.setStartCommit(commitID);
        blamer.setFilePath(file.getPath());
        BlameResult result = blamer.call();
        if (result == null) {
            throw new CommitNotFound("commit non valido");
        }
        RawText rawText = result.getResultContents();

        for (int i = 0; i < rawText.size(); i++) {

            content += rawText.getString(i);

        }

        return content;
    }

    private static String formatLogContent(String content) {
        String step1 = content.replace("\n", " ");
        return step1.replace("-?end?", "\n");
    }

    public static void clean(File directory) throws NoWorkTreeException, GitAPIException, IOException {
        if (directory == null || !directory.exists()) {
            throw new FileNotFoundException("il file non esiste");
        }

        Git git = Git.init().setDirectory(directory).call();
        Set<String> removed = git.clean().setCleanDirectories(true).call();
        for (String item : removed) {
            System.out.println("Removed: " + item);
        }
        System.out.println("Removed " + removed.size() + " items");

    }

    private static boolean letterOrDigit(String word) {
        char c;
        int j = 0;
        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                j = 1;
            } else {
                //System.out.println("   "+c);
                return false;
            }
        }
        return true;
    }

    private static boolean formatFile(String word) {
        char c;
        int j = 0;
        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == ':' || c == '.'
                    || c == '/' || c == '\\') {
                j = 1;
            } else {
                //System.out.println("   "+c);
                return false;
            }
        }
        return true;
    }

}
