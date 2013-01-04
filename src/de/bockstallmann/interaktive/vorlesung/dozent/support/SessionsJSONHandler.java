package de.bockstallmann.interaktive.vorlesung.dozent.support;

import org.json.JSONArray;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SessionsJSONHandler extends Handler {
	
	private StartSessionFactory ssf;

	public SessionsJSONHandler(StartSessionFactory startSession){
		ssf = startSession;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			ssf.addSessions((JSONArray) msg.obj);
		}
	};
}
