package de.bockstallmann.interaktive.vorlesung.dozent.support;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class QuestionJSONHandler extends Handler{
	
	private CollectionFactory cf;

	public QuestionJSONHandler(CollectionFactory coll){
		cf = coll;
	}

	
	public void handleMessage(Message msg) {
		Log.d("QuestionHandler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
				cf.addQuestions((JSONArray) msg.obj);
		}
	};
}
