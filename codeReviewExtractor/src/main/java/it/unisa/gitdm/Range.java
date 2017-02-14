package it.unisa.gitdm;


public class Range {
	private int start_line;
	private int start_character;
	private int end_line;
	private int end_character;
	
	public Range(int start_line, int start_character, int end_line, int end_character) {
		super();
		this.start_line = start_line;
		this.start_character = start_character;
		this.end_line = end_line;
		this.end_character = end_character;
	}

	public int getStart_line() {
		return start_line;
	}

	public void setStart_line(int start_line) {
		this.start_line = start_line;
	}

	public int getStart_character() {
		return start_character;
	}

	public void setStart_character(int start_character) {
		this.start_character = start_character;
	}

	public int getEnd_line() {
		return end_line;
	}

	public void setEnd_line(int end_line) {
		this.end_line = end_line;
	}

	public int getEnd_character() {
		return end_character;
	}

	public void setEnd_character(int end_character) {
		this.end_character = end_character;
	}

	@Override
	public String toString() {
		return start_line+";"+start_character+";"+end_line+";"+end_character;
	}
	
	

}
