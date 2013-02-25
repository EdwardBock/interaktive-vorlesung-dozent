package de.bockstallmann.interaktive.vorlesung.dozent.model;

public class User {
	private String user;
	private String pw;
	
	public User(String u, String p){
		this.user = u;
		this.pw = p;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
}
