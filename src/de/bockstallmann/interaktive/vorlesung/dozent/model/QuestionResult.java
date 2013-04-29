package de.bockstallmann.interaktive.vorlesung.dozent.model;

public class QuestionResult {
	private int _id, count_a, count_b, count_c, count_d; 
	private String question, correct, answer;
	
	public QuestionResult(int id, int a, int b, int c, int d, String Question, String Corr, String Answer){
		_id = id;
		question = Question;
		answer = Answer;
		correct = Corr;
		count_a = a;
		count_b = b;
		count_c = c;
		count_d = d;

	}

	public int getCount_a() {
		return count_a;
	}

	public int getCount_b() {
		return count_b;
	}

	public int getCount_c() {
		return count_c;
	}

	public int getCount_d() {
		return count_d;
	}

	public String getQuestion() {
		return question;
	}

	public String getCorrect() {
		return correct;
	}
	public String getAnswer() {
		return answer;
	}
	
}
