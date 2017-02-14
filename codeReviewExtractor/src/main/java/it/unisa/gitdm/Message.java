package it.unisa.gitdm;


public class Message {
	private String id;
	private Account author;
	private String date;
	private String message;
	private int _revision_number;
	
	public Message(String id, Account author, String date, String message, int _revision_number) {
		super();
		this.id = id;
		this.author = author;
		this.date = date;
		this.message = message;
		this._revision_number = _revision_number;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Account getAuthor() {
		return author;
	}

	public void setAuthor(Account author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int get_revision_number() {
		return _revision_number;
	}

	public void set_revision_number(int _revision_number) {
		this._revision_number = _revision_number;
	}

	@Override
	public String toString() {		
		 return author+";"+date+";"+_revision_number+";"+id+";"+message;
	}
	
	
	

}
