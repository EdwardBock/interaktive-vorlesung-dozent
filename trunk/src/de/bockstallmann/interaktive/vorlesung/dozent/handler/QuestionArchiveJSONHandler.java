package de.bockstallmann.interaktive.vorlesung.dozent.handler;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import de.bockstallmann.interaktive.vorlesung.dozent.QuestionArchive;
import de.bockstallmann.interaktive.vorlesung.dozent.support.CollectionFactory;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;

public class QuestionArchiveJSONHandler  extends Handler{

	private QuestionArchive qa;

	public QuestionArchiveJSONHandler(QuestionArchive QA){
		qa = QA;
	}

	
	public void handleMessage(Message msg) {
		Log.d("QuestionArchiveHandler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			qa.addQuestions((JSONArray) msg.obj);
		}
	};
}
