package it.unisa.gitdm;


public class Commit {
	private String commitId;
	private String commitParent;
	private String subjectParent;
	private Account author;
	private Account committer;
	private String subject;
	private String message;
	
	public Commit(String commitId, String commitParent, String subjectParent, Account author, Account committer,String subject, String message) {
		this.commitId = commitId;
		this.commitParent = commitParent;
		this.subjectParent = subjectParent;
		this.author = author;
		this.committer = committer;
		this.subject = subject;
		this.message = message;
	}

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public String getCommitParent() {
		return commitParent;
	}

	public void setCommitParent(String commitParent) {
		this.commitParent = commitParent;
	}

	public String getSubjectParent() {
		return subjectParent;
	}

	public void setSubjectParent(String subjectParent) {
		this.subjectParent = subjectParent;
	}

	public Account getAuthor() {
		return author;
	}

	public void setAuthor(Account author) {
		this.author = author;
	}

	public Account getCommitter() {
		return committer;
	}

	public void setCommitter(Account committer) {
		this.committer = committer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String toString() {
		return commitId+";"+commitParent+";"+subjectParent+";"+author+";"+committer+";"+subject+";"+message;
	}

}
