package de.bockstallmann.interaktive.vorlesung.dozent;


import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.dozent.handler.CollectionsJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.handler.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.dozent.handler.StartStopJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Collection;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Session;
import de.bockstallmann.interaktive.vorlesung.dozent.support.CollectionFactory;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ActiveSession extends Activity implements OnItemClickListener{
	
	private ArrayList<Collection> collection;
	private CollectionFactory cf;
	private ListView list;
	private JSONLoader json;
	private int id;
	private ProgressDialog pd;
	private String session_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_session);
        
        pd = new ProgressDialog(this);
        
        pd.setMessage(getText(R.string.pd_collection));
        pd.setCancelable(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
        pd.show();
        
        id = getIntent().getExtras().getInt(Constants.SESSION_ID);
        String course_title = getIntent().getExtras().getString(Constants.COURSE_TITLE);
        session_title = getIntent().getExtras().getString(Constants.SESSION_TITLE);
        
        TextView tx_course = (TextView) findViewById(R.id.tx_active_session_titel);
        TextView tx_session = (TextView) findViewById(R.id.tx_active_session);
        tx_course.setText(course_title);
        tx_session.setText(session_title);
        list = (ListView) findViewById(R.id.lv_active);
        collection = new ArrayList<Collection>();
        cf = new CollectionFactory(this, R.layout.collection_row_close, collection, id, pd);
        json = new JSONLoader(new Messenger(new CollectionsJSONHandler(cf)));
        json.getCollectionsBySessionID(id);
        JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(3, this)));
        json.setSessionActive(id, 1);
    }
   
    @Override
    protected void onResume() {
    	super.onResume();
    	JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(3, this)));
        json.setSessionActive(id, 1);
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
    	if(cf.getCollectionID() == 0 && col.getState() != 2){			
			cf.layoutVisibleChange(col.getId());
			JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(1,this)));
	        json.setCollectionActive(col.getId(), 1);
    	}else if(col.getState() == 2){
    		Intent intent = new Intent(this, QuestionArchive.class);
    		intent.putExtra(Constants.COLLECTION_ID, col.getId());
    		intent.putExtra(Constants.SESSION_TITLE, session_title);
    		intent.putExtra(Constants.COLLECTION_TITLE, col.getTitle());
    		startActivity(intent);
    	}
	}
    
    /**
   	 * Clickmethode für die Actionbaricons
   	 * @param view
   	 */
   	public void exit(final View view){
   				finish();
   	}
}
