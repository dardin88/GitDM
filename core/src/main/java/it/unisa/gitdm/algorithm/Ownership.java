/**
 *
 */
package it.unisa.gitdm.algorithm;

import it.unisa.gitdm.bean.Author;
import it.unisa.gitdm.bean.Change;
import it.unisa.gitdm.bean.Commit;
import it.unisa.gitdm.versioning.GitRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tufano Michele - tufanomichele89@gmail.com
 *         <p/>
 *         GitDM - Git Data Mining
 */
public class Ownership {

    private final GitRepository gitRepository;

    public Ownership(GitRepository gitRepository) {
        this.gitRepository = gitRepository;
    }

    public void filterStats(String path, int numOfAuthors, String out, int percentage) {
        List<Commit> commitsToDelete = calculateOwnership(percentage);

        System.out.println("Commit to delete: " + commitsToDelete.size());

        String line;
        try (
                FileWriter fw = new FileWriter(out);
                PrintWriter pw = new PrintWriter(fw)) {

            boolean firstFirsLine = true;
            for (int i = 0; i < numOfAuthors; i++) {
                System.out.println(i);
                boolean firstLine = true;
                String csvFile = path + i + ".csv";
                BufferedReader br;
                br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        if (firstFirsLine) {
                            pw.println(line);
                            firstFirsLine = false;
                        }
                        firstLine = false;
                        continue;
                    }
                    if (!line.contains("NaN")) {
                        // Filtering
                        boolean found = false;
                        for (Commit commit : commitsToDelete) {
                            if (line.contains(commit.getCommitHash())) {
                                System.out.println("Filtered!");
                                found = true;
                            }
                        }
                        if (!found) {
                            pw.println(line);
                        }
                    }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Ownership.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private List<Commit> calculateOwnership(int percentage) {

        HashMap<String, List<Commit>> files = getFiles();
        List<Commit> commitsToDelete = new ArrayList<>();

        for (Entry<String, List<Commit>> file : files.entrySet()) {
            // calcolo gli autori che hanno committato il file (quante volte
            // ogni autore)
            HashMap<Author, Integer> authors = calculateAuthorsOnFile(file);

            // Se c'è un autore che ha un numero >= di commit di percentage lui
            // diventa owner
            Author owner = findOwner(authors, percentage);

            // Se esiste un owner metto i suoi commit nella lista dei commit da
            // eliminare
            if (owner != null) {
                List<Commit> commitsOfAuthorToDelete = getCommitsByAuthor(
                        file.getValue(), owner);
                for (Commit commitToDel : commitsOfAuthorToDelete) {
                    if (!commitsToDelete.contains(commitToDel)) {
                        commitsToDelete.add(commitToDel);
                    }
                }
                // commitsToDelete.addAll(commitsOfAuthorToDelete);
            }
        }

        return commitsToDelete;
    }

    public Author findOwner(HashMap<Author, Integer> authors, int percentage) {
        int numOfTotalCommit = 0;
        for (Integer i : authors.values()) {
            numOfTotalCommit += i;
        }

        // numTotalCommit : 100 = X : percentage
        int valueOfPercentage = numOfTotalCommit * percentage / 100;

        Author owner = null;
        for (Entry<Author, Integer> entry : authors.entrySet()) {
            if (entry.getValue() >= valueOfPercentage) {
                owner = entry.getKey();
            }
        }

        return owner;
    }

    private List<Commit> getCommitsByAuthor(List<Commit> commits, Author author) {
        List<Commit> commitsOfAuthor = new ArrayList<>();

        for (Commit commit : commits) {
            if (commit.getAuthor().getEmail()
                    .equalsIgnoreCase(author.getEmail())) {
                commitsOfAuthor.add(commit);
            }
        }

        return commitsOfAuthor;
    }

    public HashMap<Author, Integer> calculateAuthorsOnFile(
            Entry<String, List<Commit>> file) {
        HashMap<Author, Integer> authors = new HashMap<>();
        for (Commit commit : file.getValue()) {
            if (authors.containsKey(commit.getAuthor())) {
                int previousNumOfCommit = authors.get(commit.getAuthor());
                authors.put(commit.getAuthor(), previousNumOfCommit + 1);
            } else {
                authors.put(commit.getAuthor(), 1);
            }
        }

        return authors;
    }

    public HashMap<String, List<Commit>> getFiles() {
        HashMap<String, List<Commit>> files = new HashMap<>();

        for (Commit commit : gitRepository.getCommits()) {
            for (Change change : commit.getChanges()) {
                String path = change.getFile().getPath();
                List<Commit> commitsOnFile;
                if (!files.containsKey(path)) {
                    commitsOnFile = new ArrayList<>();
                } else {
                    commitsOnFile = files.get(path);
                }
                commitsOnFile.add(commit);
                files.put(path, commitsOnFile);
            }
        }

        return files;
    }
    
    public HashMap<Author, Integer> getAuthorsOnFile(String file, int lastCommitIndex){
		HashMap<Author, Integer> authorsOnFile = new  HashMap<Author, Integer>();
		for(int i=0; i<=lastCommitIndex; i++){
			Commit commit = gitRepository.getCommits().get(i);
			for(Change change : commit.getChanges()){
				String path = change.getFile().getPath();
				if(path.equalsIgnoreCase(file)){
					if(authorsOnFile.containsKey(commit.getAuthor())){
						int previousNumOfCommit = authorsOnFile.get(commit.getAuthor());
						authorsOnFile.put(commit.getAuthor(), previousNumOfCommit+1);
					}else{
						authorsOnFile.put(commit.getAuthor(), 1);
					}
				}
			}
		}
		return authorsOnFile;
	}

}
