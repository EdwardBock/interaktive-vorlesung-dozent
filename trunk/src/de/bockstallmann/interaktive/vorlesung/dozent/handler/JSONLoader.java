package de.bockstallmann.interaktive.vorlesung.dozent.handler;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.interaktive.vorlesung.dozent.support.ServerCommunication;



import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
/**
 * Thread für das Laden von Serverdaten. Ruft ServerCommunication auf.
 * @author Edward
 *
 */
public class JSONLoader extends Thread {

	private JSONArray serverDaten;
	private String scriptPath;
	private Messenger m;
	
	public JSONLoader(Messenger messenger){
		m = messenger;
	}
	
	@Override
	public void run() {
		// is loading
		serverDaten = ServerCommunication.getJSONDaten(scriptPath);
		//Log.d("Loader", "serverDaten: "+serverDaten.toString());
		Message message = Message.obtain();
		if(serverDaten == null){
			// had error
			Log.d("Loader", "serverDaten == null");
			message.arg1 = Constants.MSG_ERROR;
		} else {
			Log.d("Loader", "Fertig");
			// is finished
			message.arg1 = Constants.MSG_SUCCESS;
			message.obj = serverDaten;
		}
		try {
			m.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * JSON Rückgabe des scripts "script_all_courses.php"
	 */
	public void getCoursesAfterLogin(final String uname,final String pw){
		scriptPath = "script.app.login.php?uname="+uname+"&pw="+pw;
		this.start();
	}
	public void searchCourses(final String search) {
		scriptPath = "script_search_courses.php?search="+search;
		this.start();
	}
	public void startGetSessionsByCourse(final int id){
		scriptPath = "script.app.all_sessions_dozent.php?id="+id;
		this.start();
	}
	public void setSessionActive(final int id, final int active){
		scriptPath = "script.app.set_session_active.php?id="+id+"&active="+active;
		this.start();
	}
	
	public void setCollectionActive(final int id, final int active){
		scriptPath = "script.app.set_collection_active.php?id="+id+"&active="+active;
		this.start();
	}
	public void getCollectionsBySessionID(final int id){
		scriptPath = "script.app.all_sessions_collections.php?id="+id;
		this.start();
	}
	
	public void getAllCollectionsFromCourse(final int id){
		scriptPath = "script.app.all_course_collections.php?id="+id;
		this.start();
	}
	
	public void getAllCollectionsQuestions(final int id){
		scriptPath = "script.app.allCollectionsQuestions.php?id="+id;
		this.start();
	}
	public void getAllSessionsQuestions(final int id){
		scriptPath = "script.app.all_sessions_questions.php?id="+id;
		this.start();
	}
	public void setCrashCollection(final int id){
		scriptPath = "script.app.set_crash_collection_inactive.php?id="+id;
		this.start();
	}
}
