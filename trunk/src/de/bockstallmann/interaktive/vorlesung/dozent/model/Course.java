package de.bockstallmann.interaktive.vorlesung.dozent.model;

import java.sql.Date;
import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.model.Session;

public class Course {

	private int id;
	private String t,r, d, s, pw,y;
	private ArrayList<Session> sess;
	private Date changed;
	private boolean visible = true;

	
	public Course(final int _id, final String title, final String reader, final String semester, final String year, final String password) {
		id = _id;
		t = title;
		r = reader;
		s = semester;
		y = year;
		pw = password;
	}
	public Course(final int _id, final String title, final String description, final String reader, final String semester, final String year, final String password) {
		this(_id, title, reader, semester, year, password);
		d = description;
	}

	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public String getYear() {
		return y;
	}
	public void setYear(String y) {
		this.y = y;
	}
	public int getID(){
		return id;
	}
	public String getTitle() {
		return t;
	}
	public String getDescription(){
		return d;
	}
	public String getReader(){
		return r;
	}
	public String getSemester() {
		return s;
	}
	public boolean hasPassword(){
		if(pw.equals("")){ 
			return false;
		}else{return true;}
	}
	public String getPassword(){
		return pw;
	}
	public void setSessions(ArrayList<Session> sessions){
		sess = sessions;
	}
	public Date getChanged(){
		return changed;
	}
}
