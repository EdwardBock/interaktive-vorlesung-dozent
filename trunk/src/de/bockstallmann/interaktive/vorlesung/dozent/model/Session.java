package de.bockstallmann.interaktive.vorlesung.dozent.model;

public class Session {

	private String room;
	private String title;
//asdad
	private String begin;
	private String end;
	private String src;
	private int id;
	
	
	public Session(int ID,String Room, String Title, String Begin, String End){
		this.id = ID;
		this.room = Room;
		this.title = Title;
		this.begin = Begin.substring(0, 16);
		this.end = End.substring(11, 16); 
	}
	
	public int getID(){
		return id;
	}
	
	public String getRoom() {
		return room;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBegin() {
		return begin;
	}
	
	public void setBegin(String begin) {
		this.begin = begin;
	}
	
	public String getEnd() {
		return end;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	

}
