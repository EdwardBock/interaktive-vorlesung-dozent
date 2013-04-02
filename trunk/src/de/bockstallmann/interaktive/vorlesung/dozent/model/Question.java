package de.bockstallmann.interaktive.vorlesung.dozent.model;

public class Question {

	private int _id, position, count_a, count_b, count_c, count_d, collection_id; 
	
	public Question(int id, int p, int a, int b, int c, int d, int cid){
		_id = id;
		position = p;
		count_a = a;
		count_b = b;
		count_c = c;
		count_d = d;
		collection_id = cid;
	}
	
	public String getPosition(){
		return position+"";
	}
	public int getCollectionID(){
		return collection_id;
	}	
	public String questionCount(){
		return count_a+count_b+count_c+count_d+"";
	}
	public int questionIntCount(){
		return count_a+count_b+count_c+count_d;
	}
}
