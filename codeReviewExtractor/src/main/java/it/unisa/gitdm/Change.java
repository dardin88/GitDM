package it.unisa.gitdm;

import java.util.ArrayList;

public class Change {
	private String id;
	private String project;
	private String branch;
	private String change_id;
	private String subject;
	private String status;
	private String created;
	private String updated;
	private int insertions;
	private int deletions;
	private int _number;
	private Account owner;
	private ArrayList<Account> reviewers;
	private ArrayList<Message> messages;
	
	
	public Change(String id, String project, String branch, String change_id, String subject, String status,
			String created, String updated, int insertions, int deletions, int _number,
			Account owner, ArrayList<Account> reviewers, ArrayList<Message> messages) {
		super();
		this.id = id;
		this.project = project;
		this.branch = branch;
		this.change_id = change_id;
		this.subject = subject;
		this.status = status;
		this.created = created;
		this.updated = updated;
		this.insertions = insertions;
		this.deletions = deletions;
		this._number = _number;
		this.owner = owner;
		this.reviewers=reviewers;
		this.messages=messages;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getProject() {
		return project;
	}


	public void setProject(String project) {
		this.project = project;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getChange_id() {
		return change_id;
	}


	public void setChange_id(String change_id) {
		this.change_id = change_id;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCreated() {
		return created;
	}


	public void setCreated(String created) {
		this.created = created;
	}


	public String getUpdated() {
		return updated;
	}


	public void setUpdated(String updated) {
		this.updated = updated;
	}

	
	public int getInsertions() {
		return insertions;
	}


	public void setInsertions(int insertions) {
		this.insertions = insertions;
	}


	public int getDeletions() {
		return deletions;
	}


	public void setDeletions(int deletions) {
		this.deletions = deletions;
	}


	public int get_number() {
		return _number;
	}


	public void set_number(int _number) {
		this._number = _number;
	}


	public Account getOwner() {
		return owner;
	}


	public void setOwner(Account owner) {
		this.owner = owner;
	}
	
	public ArrayList<Account> getReviewers(){
		return reviewers;
	}
	
	public void setReviewers(ArrayList<Account> reviewers){
		this.reviewers=reviewers;
	}
	
	public ArrayList<Message>getMessages(){
		return messages;
	}
	
	public void setMessages(ArrayList<Message> messages){
		this.messages=messages;
	}
	
	public String toString(){
		return id+";"+project+";"+branch+";"+change_id+";"+subject+";"+status+";"+created+";"+updated+";"+insertions+
				";"+deletions+";"+_number+";"+owner+";"+reviewers+";"+messages;
	}

}
