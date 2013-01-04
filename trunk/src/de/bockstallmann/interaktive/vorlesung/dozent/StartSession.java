package de.bockstallmann.interaktive.vorlesung.dozent;

import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.dozent.model.Session;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.interaktive.vorlesung.dozent.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.dozent.support.SessionsJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.support.StartSessionFactory;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;
import de.bockstallmann.inveraktive.vorlesung.dozent.R.layout;
import de.bockstallmann.inveraktive.vorlesung.dozent.R.menu;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class StartSession extends Activity {

	ArrayList<Session> sessions;
	StartSessionFactory ssf;
	ListView listV;
	JSONLoader json;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);
        int id = getIntent().getExtras().getInt(Constants.COURSE_ID);
        sessions = new ArrayList<Session>();
        ssf = new StartSessionFactory(this, R.layout.session_row, sessions);
        listV = (ListView) findViewById(R.id.start_session_listview);
        json = new JSONLoader(new Messenger(new SessionsJSONHandler(ssf)));
        json.startGetSessionsByCourse(id);
       
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	listV.setAdapter(ssf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start_session, menu);
        return true;
    }
    
    /**
   	 * Clickmethode für die Actionbaricons
   	 * @param view
   	 */
   	public void actionbarClick(final View view){
   		switch (view.getId()) {
   			case R.id.actionbar_back:
   			case R.id.actionbar_logo:
   				finish();
   				break;
   		}
   	}
}
