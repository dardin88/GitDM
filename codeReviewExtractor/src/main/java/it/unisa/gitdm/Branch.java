package it.unisa.gitdm;


public class Branch {
	private String ref;
	private String revision;
	public Branch(String ref, String revision) {
		this.ref = ref;
		this.revision = revision;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String toString(){
		return ref+";"+revision;
	}
}
