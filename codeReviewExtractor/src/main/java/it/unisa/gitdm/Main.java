package it.unisa.gitdm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class Main {

	public static void main(String args[])
	{
		
		String command="";
			
			HttpRequest request=new HttpRequest();
			System.out.println("Benvenuti nel servizio Code Review Extraction!!!");
			System.out.println("Assicurarsi di essere connessi ad Internet ed eseguire l'accesso su Gerrit.");
	
			while(true)
			{
				
				try
				{

					System.out.println("Scegliere l'azione da eseguire digitando il corrispettivo carattere:");
					System.out.println("- 'a' per getAccount");
					System.out.println("- 'b' per getProject");
					System.out.println("- 'c' per getChange");
					System.out.println("- 'd' per getComments");
					System.out.println("- 'e' per gerReviewers");
					System.out.println("- 'f' per getCommit");
					System.out.println("- 'g' per getBranches");
					System.out.println("- 'z' per uscire sal Sistema Code Review Extraction");
					System.out.print(">>");
				
					InputStreamReader reader = new InputStreamReader(System.in);
					BufferedReader myInput = new BufferedReader(reader);
			
					
					try {
						command = myInput.readLine();
					} catch (IOException e) {
						System.out.println ("Si � verificato un errore: " + e);
						System.exit(-1);
					}
							
						
					if(command.equals("a")){
						System.out.println("GET ACCOUNT");
						System.out.println("Inserisci l'account id:");
						command=myInput.readLine();
						request.writeFile(request.getAccount(command));
					}
					else if(command.equals("b")){
						System.out.println("GET PROJECT");
						System.out.println("Inserisci il nome del progetto:");
						System.out.println("(Se presente il carattere '/' sostituirlo con '%2f')");
						command=myInput.readLine();
						request.writeFile(request.getProject(command));
					}
					else if(command.equals("c")){
						System.out.println("GET CHANGE");
						System.out.println("Inserisci il change id");
						command=myInput.readLine();
						request.writeFile(request.getChange(command));
					}
					else if(command.equals("d")){
						System.out.println("GET COMMENTS");
						System.out.println("Inserisci il change id");
						command=myInput.readLine();
						request.writeFile(request.getComments(command));
					}
					else if(command.equals("e")){
						System.out.println("GET REVIEWERS");
						System.out.println("Inserisci il change id");
						command=myInput.readLine();
						request.writeFile(request.getReviewers(command));

					}
					else if(command.equals("f")){
						System.out.println("GET COMMIT");
						System.out.println("Inserisci il change id:");
						command=myInput.readLine();
						System.out.println("Inserisci il revision id:");
						String revision=myInput.readLine();
						request.writeFile(request.getCommit(command,revision));
						//request.writeFile(request.getCommit("https://review.gerrithub.io/changes/"+command+"/revisions/"+revision+"/commit"));
						//request.writeFile(request.getCommit("https://review.gerrithub.io/changes/"+303621+"/revisions/f813b2d3f57f97f0fb57a8b78eb69e609c58263e/commit"));
					}
					else if(command.equals("g")){
						System.out.println("GET BRANCHES");
						System.out.println("Inserisci il nome del progetto:");
						System.out.println("(Se presente il carattere '/' sostituirlo con '%2f')");
						command=myInput.readLine();
						request.writeFile(request.getBranches(command));
					}
					else if(command.equals("z")){
						System.out.println("Ciao");
						System.exit(0);
					}
					
					
					else if(command.equals("h")){
					
						System.out.println(request.getBranches("midonet%2fmidonet"));
						//System.out.println(request.query("https://review.gerrithub.io/projects/midonet%2fmidonet/branches"));
					}
					/*else if(command.equals("i")){
						System.out.println("GET ASSIGNEE");
						System.out.println("Get assignee restituisce sempre una stringa vuota");
						//request.writeFile(request.getAssignee("https://review.gerrithub.io/changes/303138/assignee"));
					}
					*/
					else{
						System.out.println("Comando non valido");
		
					}
								
				}
				catch(Exception e)
				{
					System.out.println("Input errato. Riprova...\n\n");
				}
			}
	}
}

				//request.writeFile(request.getChange("https://review.gerrithub.io/changes/303071/detail"));
				
				//System.out.println(request.query("https://review.gerrithub.io/changes/303138/comments"));
				//System.out.println(request.query("https://review.gerrithub.io/changes/303071/topic"));
		
				//	System.out.println(request.query("https://review.gerrithub.io/projects/Mirantis/stepler"));
		
			    //	System.out.println(request.query("https://review.gerrithub.io/changes/303621/revisions/f813b2d3f57f97f0fb57a8b78eb69e609c58263e/files"));
		
		//		System.out.println(request.query("https://review.gerrithub.io/changes/303621/revisions/f813b2d3f57f97f0fb57a8b78eb69e609c58263e/review"));
			//	System.out.println(request.query("https://review.gerrithub.io/changes/303621/revisions/f813b2d3f57f97f0fb57a8b78eb69e609c58263e/related"));
		
				