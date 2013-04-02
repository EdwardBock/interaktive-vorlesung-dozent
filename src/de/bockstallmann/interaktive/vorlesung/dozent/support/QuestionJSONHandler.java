package de.bockstallmann.interaktive.vorlesung.dozent.support;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class QuestionJSONHandler extends Handler{
	
	private CollectionFactory cf;
	private boolean refr;

	public QuestionJSONHandler(CollectionFactory coll, boolean refresh){
		cf = coll;
		refr = refresh;
	}

	
	public void handleMessage(Message msg) {
		Log.d("QuestionHandler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			if(refr){
				cf.refreshQuestions((JSONArray) msg.obj);
			}else{
				cf.addQuestions((JSONArray) msg.obj);
			}
		}
	};
}
