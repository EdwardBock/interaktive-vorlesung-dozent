package de.bockstallmann.interaktive.vorlesung.dozent.handler;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.interaktive.vorlesung.dozent.support.StartSessionFactory;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SessionsJSONHandler extends Handler {
	
	private StartSessionFactory ssf;

	public SessionsJSONHandler(StartSessionFactory startSession){
		ssf = startSession;
	}
	public void handleMessage(Message msg) {
		Log.d("HandlerSessions",msg.arg1+"");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			ssf.addSessions((JSONArray) msg.obj);
		}else{
			ssf.pdDismiss();
		}
	};
}
