package it.unisa.gitdm;


public class Comment {
	private Account author;
	private int patch_set;
	private String id;
	private int line;
	private Range range;
	private String updated;
	private String message;
	
	public Comment(Account author, int patch_set, String id, int line, Range range, String updated, String message) {
		super();
		this.author = author;
		this.patch_set = patch_set;
		this.id = id;
		this.line=line;
		this.range = range;
		this.updated = updated;
		this.message = message;
	}

	public Account getAuthor() {
		return author;
	}

	public void setAuthor(Account author) {
		this.author = author;
	}

	public int getPatch_set() {
		return patch_set;
	}

	public void setPatch_set(int patch_set) {
		this.patch_set = patch_set;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getLine(){
		return line;
	}
	
	public void setLine(int line){
		this.line=line;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return author+";"+patch_set+";"+id+";"+range+";"+updated+";"+message;
	}
	
	
	
}
