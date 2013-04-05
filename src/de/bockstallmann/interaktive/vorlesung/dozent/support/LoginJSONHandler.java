package de.bockstallmann.interaktive.vorlesung.dozent.support;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.dozent.Login;
import de.bockstallmann.interaktive.vorlesung.dozent.model.User;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class LoginJSONHandler extends Handler {
	
	private LoginFactory login;
	private Activity viewCourse;
	private SQLDataHandler db;
	private User user;

	public LoginJSONHandler(LoginFactory Login, Activity ViewCourse, User u){
		login = Login;
		viewCourse = ViewCourse;
		db = new SQLDataHandler(viewCourse);
		user = u;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			if(db.getUser() == null){
				db.addUser(user);
			}
			login.addCourse((JSONArray) msg.obj);
		}else if(msg.arg1 == Constants.MSG_ERROR){
			Toast.makeText(viewCourse, "Falscher LOGIN!!!", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(viewCourse, Login.class);
			viewCourse.startActivity(intent);
			viewCourse.finish();
		}
	};
}
