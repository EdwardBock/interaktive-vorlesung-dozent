package de.bockstallmann.interaktive.vorlesung.dozent.model;

public class Collection {
	
	int id;
	String title;
	
	public Collection(int i, String t){
		id = i;
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
	
	
}
