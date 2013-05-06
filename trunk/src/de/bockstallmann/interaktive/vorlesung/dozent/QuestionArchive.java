package de.bockstallmann.interaktive.vorlesung.dozent;

import java.util.ArrayList;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.dozent.handler.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.dozent.handler.QuestionArchiveJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Question;
import de.bockstallmann.interaktive.vorlesung.dozent.model.QuestionResult;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;
import de.bockstallmann.inveraktive.vorlesung.dozent.R.layout;
import de.bockstallmann.inveraktive.vorlesung.dozent.R.menu;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class QuestionArchive extends Activity {

	private ArrayList<QuestionResult> questions;
	private TextView tx_empty_info;
	private ScrollView sv_content;
	private RelativeLayout nav_wrapper;
	private View question_view;
	private int activeQuestion = 0;
	private LayoutInflater li;
	private TextView tx_titel;
	private TextView tx_info;
	private String c_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_archive);
		questions = new ArrayList<QuestionResult>();
		int id = getIntent().getExtras().getInt(Constants.COLLECTION_ID);
		c_title = getIntent().getExtras().getString(Constants.COLLECTION_TITLE);
		String s_title = getIntent().getExtras().getString(Constants.SESSION_TITLE);
		tx_empty_info = (TextView) findViewById(R.id.tx_empty_info);
		sv_content = (ScrollView) findViewById(R.id.sv_content);
		nav_wrapper = (RelativeLayout) findViewById(R.id.navigation_wrapper);
		tx_titel = (TextView) findViewById(R.id.tx_archive_question_titel);
		tx_info = (TextView) findViewById(R.id.tx_archive_question);
		tx_titel.setText(s_title);
		tx_info.setText(c_title);
		
		
		JSONLoader json = new JSONLoader(new Messenger(new QuestionArchiveJSONHandler(this)));
		json.getQuestionResult(id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_archive, menu);
		return true;
	}
	
	public void addQuestions(JSONArray serverDaten){
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				questions.add(new QuestionResult(
						Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")), 	
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_a")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_b")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_c")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_d")),
						serverDaten.getJSONObject(i).getString("question"),
						serverDaten.getJSONObject(i).getString("position"),
						serverDaten.getJSONObject(i).getString("correct")));
			} catch (Exception e) {
				Log.d("CollectionAdapter Question", "problem bei i = "+i);
				continue;
			}
		}
		
		displayQuestion();

	}
	
	private void displayQuestion() {
		tx_info.setText(c_title+": "+(activeQuestion+1)+"/"+questions.size()+" Frage");
		if(questions.size()>0){
			sv_content.removeAllViews();
			if (li == null) {
				li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}
			question_view = li.inflate(R.layout.archive_question, null);
	
			((TextView) question_view.findViewById(R.id.tx_question))
					.setText(questions.get(activeQuestion).getQuestion());
	
			((TextView) question_view.findViewById(R.id.tx_correct_answer))
					.setText(questions.get(activeQuestion).getAnswer());
	
			
			int r_id = -1;
			if(questions.get(activeQuestion).getCorrect().equals("a")) {
				r_id = R.id.tx_a1;
			}else if(questions.get(activeQuestion).getCorrect().equals("b")) {
				r_id = R.id.tx_a2;
			}else if(questions.get(activeQuestion).getCorrect().equals("c")) {
				r_id = R.id.tx_a3;
			}else if(questions.get(activeQuestion).getCorrect().equals("d")) {
				r_id = R.id.tx_a4;
			}
			
			if(r_id != -1){
				((TextView) question_view.findViewById(r_id)).setBackgroundResource(R.drawable.bg_archive_answer_correct);
			}
			
			((TextView) question_view.findViewById(R.id.tx_1)).setText(Integer
					.toString(questions.get(activeQuestion).getCount_a()));
			
			((TextView) question_view.findViewById(R.id.tx_2)).setText(Integer
					.toString(questions.get(activeQuestion).getCount_b()));
	
			((TextView) question_view.findViewById(R.id.tx_3)).setText(Integer
					.toString(questions.get(activeQuestion).getCount_c()));
	
			((TextView) question_view.findViewById(R.id.tx_4)).setText(Integer
					.toString(questions.get(activeQuestion).getCount_d()));
	
			sv_content.addView(question_view);
			
			sv_content.setVisibility(View.VISIBLE);
			nav_wrapper.setVisibility(View.VISIBLE);
			tx_empty_info.setVisibility(View.GONE);
		}else{
			sv_content.setVisibility(View.GONE);
			nav_wrapper.setVisibility(View.GONE);
			tx_empty_info.setVisibility(View.VISIBLE);
		}
	}
	
	
	/**
	 * Clickmethode für die Vor-Zurück-Navigation
	 * 
	 * @param view
	 */
	public void navigationClick(final View view) {
		switch (view.getId()) {
		case R.id.btn_next:
			if(activeQuestion < (questions.size()-1)){
				activeQuestion++;
				displayQuestion();
			}			
			break;
		case R.id.btn_prev:
			if(activeQuestion > 0){
				activeQuestion--;
				displayQuestion();
			}
			break;
		}
	}

	/**
	 * Clickmethode für die Actionbaricons
	 * 
	 * @param view
	 */
	public void actionbarClick(final View view) {
		switch (view.getId()) {
		case R.id.btn_back_archive_question:
			finish();
			break;
		}
	}

}
