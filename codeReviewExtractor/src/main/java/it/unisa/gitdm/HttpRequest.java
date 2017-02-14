package it.unisa.gitdm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;


public class HttpRequest<E> {
	private ArrayList<Comment> comments=new ArrayList<>();

	public String query(String indirizzo) throws IOException{
		String toReturn="";
		String current="";
		
		try{
			URL url = new URL(indirizzo);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection connection = null;
			if (urlConnection instanceof HttpURLConnection) {
				connection = (HttpURLConnection) urlConnection;
			} else {
				System.out.println("Please enter an HTTP URL.");
				return null;
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			current=in.readLine();
			while ((current = in.readLine()) != null ) {
				toReturn += current+"\n";
			}
		}catch (Exception e) {
			System.out.println("I dati che sono stati inseriti non sono validi.");
		}
		return toReturn;
	}

	public void writeFile(String s){
		 try {
		        File file = new File("C:/Users/fdeci/Desktop/dati.csv");
		        FileWriter fw = new FileWriter(file);
		        BufferedWriter bw = new BufferedWriter(fw);
		        bw.write(s);
		        bw.flush();
		        bw.close();
		    }
		    catch(IOException e) {
		        e.printStackTrace();
		    }
	}
	
	public String formatText(String s){
		String result="";
		
		result=s.replace('\n', ' ');
		result=result.replace(';', '-');
		return result;
	}

	public String getAccount(String indirizzo) throws IOException, JSONException, ParseException {
		Account account=null;
		String response="";
		String registered="";
		int id=0;
		String nome="";
		String email="";
		String username="";
		String result="NAME;ID;EMAIL;USERNAME;REGISTERED\n";
		
		if(!(indirizzo.length()<1 || indirizzo.length()>30)){
			response=query("https://review.gerrithub.io/accounts/"+indirizzo+"/detail");
			if(response!=""){
			JSONObject obj= new JSONObject(response);
			registered=(String)obj.get("registered_on");
			id=(int) obj.get("_account_id");
			nome=(String)obj.get("name");
			email=(String)obj.get("email");
			username=(String)obj.get("username");
			account=new Account(registered, id, nome, email, username);
			System.out.println("Operazione eseguita correttamente!!!");
			return(result+=account.toString());
			}
		}
		else{
			System.out.println("Account id non valido.\nInserisci un account id valido che sia di una lunghezza di caratteri compresa tra 1 e 30.");
		}
		return "";
	}
	
	public String getChange(String indirizzo) throws IOException, JSONException, ParseException{
		Change change=null;
		int account_id=0;
		String name="";
		String email="";
		String username="";
		String response="";
		String date="";
		String message_="";
		int revision_number=0;
		String id="";
		String project="";
		String branch="";
		String change_id="";
		String subject="";
		String status="";
		String created="";
		String updated="";
		int insertions=0;
		int deletions=0;
		int _number=0;
		Account owner=null;
		Account reviewer=null;
		Account author=null;
		ArrayList<Account> reviewers=new ArrayList<>();
		ArrayList<Message> messages=new ArrayList<>();
		String result="ID;PROJECT;BRANCH;CHANGE ID;SUBJECT;STATUS;CREATED;UPDATED;INSERTIONS;DELETIONS;NUMBER\n";
		
		if(!(indirizzo.length()<1 || indirizzo.length()>30)){
			response=query("https://review.gerrithub.io/changes/"+indirizzo+"/detail");
			if(response!=""){
				JSONObject obj= new JSONObject(response);
				id=(String)obj.get("id");
				project=(String)obj.get("project");
				branch=(String)obj.get("branch");
				change_id=(String)obj.get("change_id");
				subject=(String)obj.get("subject");
				status=(String)obj.get("status");
				created=(String)obj.get("created");
				updated=(String)obj.get("updated");
				insertions=(int)obj.get("insertions");
				deletions=(int)obj.get("deletions");
				_number=(int)obj.get("_number");
				result+=id+";"+project+";"+branch+";"+change_id+";"+subject+";"+status+";"+created+";"+updated+";"+insertions+";"+deletions+";"+_number+"\n";
				result+="\n\nOWNER NAME;OWNER ID;OWNER EMAIL;OWNER USERNAME\n";
				JSONObject objOwner = obj.getJSONObject("owner");
				account_id=(int)objOwner.get("_account_id");
				name=(String) objOwner.get("name");
				email=(String) objOwner.get("email");
				username=(String)objOwner.get("username");
				owner=new Account(account_id, name, email, username);
				result+=name+";"+account_id+";"+email+";"+username+";"+"\n\n\n";
				result+="REVIEWERS ACCOUNT NAME;REVIEWERS ACCOUNT ID;REVIEWERS ACCOUNT EMAIL;REVIEWERS ACCOUNT USERNAME\n";
				JSONObject objReviewers = obj.getJSONObject("reviewers");
				JSONArray arr = objReviewers.getJSONArray("REVIEWER");
				for (int i = 0; i < arr.length(); i++){
					JSONObject objReviewer =arr.getJSONObject(i);
					account_id=(int)objReviewer.get("_account_id");
					name=(String) objReviewer.get("name");
					email=(String) objReviewer.get("email");
					username=(String)objReviewer.get("username");
					reviewer=new Account(account_id, name, email, username);
					reviewers.add(reviewer);
					result+=name+";"+account_id+";"+email+";"+username+";"+"\n";
				}
				arr = objReviewers.getJSONArray("REVIEWER");
				for (int i = 0; i < arr.length(); i++){
					JSONObject objReviewer =arr.getJSONObject(i);
					account_id=(int)objReviewer.get("_account_id");
					name=(String) objReviewer.get("name");
					email=(String) objReviewer.get("email");
					username=(String)objReviewer.get("username");
					reviewer=new Account(account_id, name, email, username);
					reviewers.add(reviewer);
					result+=name+";"+account_id+";"+email+";"+username+"\n";
				}
				result+="\n\nID MESSAGES;AUTHOR NAME MESSAGES;AUTHOR ID MESSAGES;AUTHOR EMAIL MESSAGES; AUTHOR USERNAME MESSAGES;DATE MESSAGES;REVISION NUMBER MESSAGES;TEXT MESSAGES\n";
				arr = obj.getJSONArray("messages");
				for (int i = 0; i < arr.length(); i++){
					JSONObject objMessage =arr.getJSONObject(i);
					id=(String) objMessage.get("id");
					JSONObject objAuthor = objMessage.getJSONObject("author");
					account_id=(int)objAuthor.get("_account_id");
					name=(String) objAuthor.get("name");
					email=(String) objAuthor.get("email");
					username=(String)objAuthor.get("username");
					author=new Account(account_id, name, email, username);
					date=(String)objMessage.get("date"); 
					message_=(String)objMessage.get("message");
					message_=formatText(message_);
					revision_number=(int)objMessage.get("_revision_number");
					Message message=new Message(id, author, date, message_, revision_number);
					messages.add(message);
					result+=id+";"+name+";"+account_id+";"+email+";"+username+";"+date+";"+revision_number+";"+message_+"\n";
				}	
				change=new Change(id,project,branch,change_id,subject,status,created,updated,insertions,deletions,_number,owner,reviewers,messages);
				System.out.println("Operazione eseguita correttamente!!!");
				return(result);
			}
		}else{
			System.out.println("Change id non valido.\nInserisci un change id valido che sia di una lunghezza di caratteri compresa tra 1 e 30.");
		}
		return "";
	}

	public String getComments(String indirizzo)throws IOException, JSONException, ParseException{
		Comment comment;		
		String response="";
		Account author;
		int account_id=0;
		String name="";
		String email="";
		String username="";
		int patch_set=0;
		String id="";
		int line=0;
		Range range;
		int start_line=0;
		int start_character=0;
		int end_line=0;
		int end_character=0;
		String updated="";
		String message="";	
		String nomeArray="";
		int primoApice=0;
		int secondoApice=0;
		String result="AUTHOR NAME;AUTHOR ID;AUTHOR EMAIL;AUTHOR USERNAME;PATCH_SET;ID;LINE;RANGE START LINE;RANGE START CHARACTER;RANGE END LINE;RANGE END CHARACTER;UPDATED;MESSAGE\n";
		
		if(!(indirizzo.length()<1 || indirizzo.length()>30)){
			response=query("https://review.gerrithub.io/changes/"+indirizzo+"/comments");
			if(response!=""){
				//Trovo il nome dell'array per leggere i commenti
				if(response.length()>10){
					primoApice=response.indexOf("\"");
					secondoApice=response.indexOf("\"",primoApice+1);
					nomeArray=response.substring(primoApice+1, secondoApice);
				}
				else{
					System.out.println("Non sono presenti commenti");
					return result;
				}	
				JSONObject obj= new JSONObject(response);
				JSONArray arr = obj.getJSONArray(nomeArray);
				for (int i = 0; i < arr.length(); i++){
					JSONObject objComment =arr.getJSONObject(i);
					JSONObject objAuthor = objComment.getJSONObject("author");
					account_id=(int)objAuthor.get("_account_id");
					name=(String) objAuthor.get("name");
					email=(String) objAuthor.get("email");
					username=(String)objAuthor.get("username");
					author=new Account(account_id, name, email, username);
					patch_set=(int)objComment.getInt("patch_set");
					id=(String)objComment.get("id");
					line=(int)objComment.get("line");
					JSONObject objRange = objComment.getJSONObject("range");
					start_line=(int)objRange.get("start_line");
					start_character=(int)objRange.get("start_character");
					end_line=(int)objRange.get("end_line");
					end_character=(int)objRange.getInt("end_character");
					range=new Range(start_line, start_character, end_line, end_character);
					updated=(String)objComment.get("updated");
					message=(String)objComment.get("message");
					message=formatText(message);	
					comment=new Comment(author, patch_set, id,line, range, updated, message);
					comments.add(comment);
					result+=name+";"+account_id+";"+email+";"+username+";"+patch_set+";"+id+";"+line+";"+start_line+";"+start_character+";"+end_line+";"+end_character+";"+updated+";"+message+"\n";
				}
				System.out.println("Operazione eseguita correttamente!!!");
				return result;
			}
		}else{
			System.out.println("Change id non valido.\nInserisci un change id valido che sia di una lunghezza di caratteri compresa tra 1 e 30.");
		}
		return "";
	}

	public String getProject(String indirizzo)throws IOException, JSONException, ParseException{
		Project project=null;
		String response="";
		String id="";
		String name="";
		String parent="";
		String description="";
		String state="";
		String result="PROJECT ID;NAME;PARENT;DESCRIPTION;STATE\n";
		
		if(!(indirizzo.length()<1 || indirizzo.length()>30)){
			response=query("https://review.gerrithub.io/projects/"+indirizzo);	
			if(response!=""){
				JSONObject obj= new JSONObject(response);
				id=(String)obj.get("id");
				name=(String) obj.get("name");
				parent=(String)obj.get("parent");
				if(!obj.isNull("description"))
					description=(String)obj.get("description");
				state=(String)obj.get("state");
				project=new Project(id, name, parent, description, state);
				System.out.println("Operazione eseguita correttamente!!!");
				return(result+=project.toString());
				}
		}else{
			System.out.println("Project id non valido.\nInserisci un project id valido che sia di una lunghezza di caratteri compresa tra 1 e 30.");
		}
		return "";
	}

	public String getReviewers(String indirizzo)throws IOException, JSONException, ParseException{
		ArrayList<Account> reviewers=new ArrayList<>();
		Account account;
		String response="{\"reviewers\":";
		int id=0;
		String nome="";
		String email="";
		String username="";
		String result="NAME ACCOUNT;ACCOUNT ID;ACCOUNT EMAIL;ACCOUNT USERNAME\n";
		
		if(!(indirizzo.length()<1 || indirizzo.length()>30)){
			response+=query("https://review.gerrithub.io/changes/"+indirizzo+"/reviewers");	
			if(response!="{\"reviewers\":\n"){
				response+="}";
				JSONObject obj= new JSONObject(response);
				JSONArray arr = obj.getJSONArray("reviewers");
				for (int i = 0; i < arr.length(); i++){
					JSONObject objReview =arr.getJSONObject(i);
					id=(int) objReview.get("_account_id");
					nome=(String)objReview.get("name");
					email=(String)objReview.get("email");
					username=(String)objReview.get("username");
					account=new Account(null, id, nome, email, username);
					reviewers.add(account);			
					result+=nome+";"+id+";"+email+";"+username+"\n";
				}
				System.out.println("Operazione eseguita correttamente!!!");
				return(result);
			}
		}else{
			System.out.println("Change id non valido.\nInserisci un change id valido che sia di una lunghezza di caratteri compresa tra 1 e 30.");
		}
		return "";
	}
	
	public String getCommit(String indirizzo, String revision)throws IOException, JSONException, ParseException{
		Commit commit;		
		String response="";
		Account author;
		Account committer;
		String commitId="";
		String commitParent="";
		String subjectParent="";
		String name="";
		String email="";
		String message="";		
		String subject="";
		String result="COMMIT ID;COMMIT PARENT;SUBJECT PARENT;AUTHOR NAME;AUTHOR EMAIL;COMMITTER NAME;COMMITTER EMAIL;SUBJECT;MESSAGE\n";
		
		if(!((indirizzo.length()<1 || indirizzo.length()>30)||(revision.length()<1||revision.length()>60))){
			response=query("https://review.gerrithub.io/changes/"+indirizzo+"/revisions/"+revision+"/commit");
			if(response!=""){
				JSONObject obj= new JSONObject(response);
				commitId=(String)obj.get("commit");
				JSONArray arr = obj.getJSONArray("parents");
				for (int i = 0; i < arr.length(); i++){
					JSONObject objParent =arr.getJSONObject(i);
					commitParent=(String)objParent.get("commit");
					subjectParent=(String)objParent.get("subject");
				}
					
				JSONObject objAuthor = obj.getJSONObject("author");
				name=(String) objAuthor.get("name");
				email=(String) objAuthor.get("email");
				author=new Account(0, name, email, null);
		
				JSONObject objCommitter = obj.getJSONObject("committer");
				name=(String) objCommitter.get("name");
				email=(String) objCommitter.get("email");
				committer=new Account(0, name, email, null);
		
				subject=(String)obj.get("subject");
				message=(String)obj.get("message");
				message=formatText(message);	
				commit=new Commit(commitId, commitParent, subjectParent, author, committer, subject, message);
				System.out.println("Operazione eseguita correttamente!!!");
				return result+=commitParent+";"+subjectParent+";"+author.getName()+";"+author.getEmail()+";"+committer.getName()+";"+committer.getEmail()+";"+subject+";"+message+"\n";
				}
			}else{
			System.out.println("Change id non valido.\nInserisci un change id di una lunghezza di caratteri compresa tra 1 e 30 e un revision id tra 1 e 60.");
		}
		return "";
	}

	public String getBranches(String indirizzo) throws IOException, JSONException, ParseException {
		ArrayList<Branch> branches=new ArrayList<>();
		Branch branch=null;
		String response="{\"reviewers\":";
		String ref="";
		String revision="";
		String result="REF;REVISION\n";
		
		if(!(indirizzo.length()<1 || indirizzo.length()>30)){
			response+=query("https://review.gerrithub.io/projects/"+indirizzo+"/branches");
			System.out.println(response);
			if(response!="{\"reviewers\":"){
				response+="}";
				JSONObject obj= new JSONObject(response);
				
				JSONArray arr = obj.getJSONArray("reviewers");
				for (int i = 0; i < arr.length(); i++){
					JSONObject objBranch =arr.getJSONObject(i);
					ref=(String)objBranch.get("ref");
					revision=(String)objBranch.get("revision");
					branch=new Branch(ref, revision);
					branches.add(branch);
				}
				for(int i=0;i<branches.size();i++){
					result+=branches.get(i).toString()+"\n";
				}
				System.out.println("Operazione eseguita correttamente!!!");
				return(result);
			}
		}else{
			System.out.println("Project id non valido.\nInserisci un project id valido che sia di una lunghezza di caratteri compresa tra 1 e 30.");
		}
		return "";
	}
}