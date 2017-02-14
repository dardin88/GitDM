package it.unisa.gitdm;


public class Project {
	private String id;
	private String name;
	private String parent;
	private String description;
	private String state;
	
	public Project(String id, String name, String parent, String description, String state) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
		this.description = description;
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return id+";"+name+";"+parent+";"+description+";" + state;
	}
	
	

	
}
