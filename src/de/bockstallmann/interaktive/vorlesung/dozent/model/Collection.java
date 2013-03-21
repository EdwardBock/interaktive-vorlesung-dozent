package de.bockstallmann.interaktive.vorlesung.dozent.model;

public class Collection {
	
	int id, session_id;
	String title;
	
	public Collection(int i, String t){
		id = i;
		title = t;
	}
	public Collection(int i, int session, String t){
		id = i;
		session_id = session;
		title = t;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public int getSession_id() {
		return session_id;
	}
	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}
	
	
}
