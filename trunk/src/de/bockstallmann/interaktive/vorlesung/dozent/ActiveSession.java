package de.bockstallmann.interaktive.vorlesung.dozent;


import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.dozent.model.Collection;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Session;
import de.bockstallmann.interaktive.vorlesung.dozent.support.CollectionFactory;
import de.bockstallmann.interaktive.vorlesung.dozent.support.CollectionsJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.interaktive.vorlesung.dozent.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.dozent.support.StartStopJSONHandler;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;
import de.bockstallmann.inveraktive.vorlesung.dozent.R.layout;
import de.bockstallmann.inveraktive.vorlesung.dozent.R.menu;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ActiveSession extends Activity implements OnItemClickListener{
	
	ArrayList<Collection> collection;
	CollectionFactory cf;
	ListView list;
	JSONLoader json;
	int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_session);
        id = getIntent().getExtras().getInt(Constants.SESSION_ID);
        String course_title = getIntent().getExtras().getString(Constants.COURSE_TITLE);
        String session_title = getIntent().getExtras().getString(Constants.SESSION_TITLE);
        
        TextView tx_course = (TextView) findViewById(R.id.tx_active_session_titel);
        TextView tx_session = (TextView) findViewById(R.id.tx_active_session);
        tx_course.setText(course_title);
        tx_session.setText(session_title);
        list = (ListView) findViewById(R.id.lv_active);
        collection = new ArrayList<Collection>();
        cf = new CollectionFactory(this, R.layout.collection_row_close, collection);
        json = new JSONLoader(new Messenger(new CollectionsJSONHandler(cf)));
        json.getCollectionsBySessionID(id);
        JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(3, this)));
        json.setSessionActive(id, 1);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	list.setAdapter(cf);
    	list.setOnItemClickListener(this);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(2, this)));
		json.setSessionActive(id, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_active_session, menu);
        return true;
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Collection col = (Collection)parent.getItemAtPosition(position);
		cf.layoutVisibleChange(col.getId());
		JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(1,this)));
        json.setCollectionActive(col.getId(), 1);
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
