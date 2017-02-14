package it.unisa.gitdm;


public class Account {
	private String registered;
	private int id;
	private String name;
	private String email;
	private String username;
	
	public Account(String registered, int id, String name, String email, String username){
		this.registered=registered;
		this.id=id;
		this.name=name;
		this.email=email;
		this.username=username;
	}
	
	public Account(int id, String name, String email, String username){
		this.id=id;
		this.name=name;
		this.email=email;
		this.username=username;
	}


	public String getRegistred(){
		return registered;
	}
	
	public void setRegistred(String registred){
		this.registered=registred;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toString(){
		return name+";"+id+";"+email+";"+username+";"+registered;
	}

	
}
