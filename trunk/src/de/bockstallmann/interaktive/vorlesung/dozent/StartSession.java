package de.bockstallmann.interaktive.vorlesung.dozent;

import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.dozent.handler.CollectionsJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.handler.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.dozent.handler.SessionsJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Course;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Session;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.interaktive.vorlesung.dozent.support.StartSessionFactory;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;
import de.bockstallmann.inveraktive.vorlesung.dozent.R.layout;
import de.bockstallmann.inveraktive.vorlesung.dozent.R.menu;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class StartSession extends Activity implements OnItemClickListener {

	ArrayList<Session> sessions;
	StartSessionFactory ssf;
	ListView listV;
	JSONLoader json;
	String courseTitle;
	ProgressDialog pd;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        pd = new ProgressDialog(this);
        pd.setMessage(getText(R.string.pd_session));
        pd.setCancelable(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
        pd.show();
        setContentView(R.layout.activity_start_session);
        int id = getIntent().getExtras().getInt(Constants.COURSE_ID);
        courseTitle = getIntent().getExtras().getString(Constants.COURSE_TITLE);
        TextView titel = (TextView) findViewById(R.id.actionbar_title);
        titel.setText(courseTitle);
        
        sessions = new ArrayList<Session>();
        ssf = new StartSessionFactory(this, R.layout.session_row, sessions,pd,id);
        listV = (ListView) findViewById(R.id.start_session_listview);
        json = new JSONLoader(new Messenger(new SessionsJSONHandler(ssf)));
        json.startGetSessionsByCourse(id);
        
       
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	listV.setAdapter(ssf);
    	listV.setOnItemClickListener(this);
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_start_session, menu);
        return true;
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Session session = (Session) parent.getItemAtPosition(position);
		Log.d("Sessioninhalt", ""+session.getID());
		Intent intent = new Intent(this, ActiveSession.class);
		intent.putExtra(Constants.SESSION_ID, session.getID());
		intent.putExtra(Constants.SESSION_TITLE, session.getTitle());
		intent.putExtra(Constants.COURSE_TITLE, courseTitle);
		startActivity(intent);
		
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
