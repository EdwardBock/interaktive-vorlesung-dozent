package de.bockstallmann.interaktive.vorlesung.dozent.support;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class LoginJSONHandler extends Handler {
	
	private LoginFactory login;
	private Activity viewCourse;

	public LoginJSONHandler(LoginFactory Login, Activity ViewCourse){
		login = Login;
		viewCourse = ViewCourse;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			login.addCourse((JSONArray) msg.obj);
		}else if(msg.arg1 == Constants.MSG_ERROR){
			Toast.makeText(viewCourse, "Falscher LOGIN!!!", Toast.LENGTH_LONG).show();
			viewCourse.finish();
		}
	};
}
