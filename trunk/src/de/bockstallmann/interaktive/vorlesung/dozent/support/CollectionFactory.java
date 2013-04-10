package de.bockstallmann.interaktive.vorlesung.dozent.support;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.dozent.handler.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.dozent.handler.QuestionJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.handler.StartStopJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Collection;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Question;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;

public class CollectionFactory extends ArrayAdapter<Collection> {
	private ArrayList<Collection> collections;
	private ArrayList<Question> questions;
	private Context context;
	private LayoutInflater inflater;
	private int collectionID = 0;
	private int sessionID;
	private LinearLayout ll;
	private ProgressDialog pd;
	
	
	public CollectionFactory(Context theContext, int textViewResourceId,ArrayList<Collection> Collection, int Session_id, ProgressDialog PD) {
		super(theContext, textViewResourceId, Collection);
		this.collections = Collection;
		this.context = theContext;
		inflater = (LayoutInflater)theContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		questions = new ArrayList<Question>();
		sessionID = Session_id;
		pd = PD;
		JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(4, context)));
        json.setCrashCollection(sessionID);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Collection collection = collections.get(position);
		final int id = collection.getId();
		//Geöffnete Fragerunde
		if(collection.getId() == collectionID){		
			refreshCollectionCount(id);	
			if (view == null) {
	            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = vi.inflate(R.layout.collection_row_open, null);
	        }
			view = inflater.inflate(R.layout.collection_row_open, parent, false);

			TextView tv = (TextView) view.findViewById(R.id.tx_collection_name_open);
			tv.setText(collection.getTitle());
			TextView tx_count = (TextView) view.findViewById(R.id.tx_collection_qcount_open);
			int count = 0;
			int answers = 0;
			for(int i = 0; i<questions.size();i++){
				if(questions.get(i).getCollectionID() == collection.getId()){
					count++;
					answers += questions.get(i).questionIntCount();
				}					
			}
			tx_count.setText(count+" Fragen/ Ø"+answers/count+" Antworten");
			
			ll = (LinearLayout) view.findViewById(R.id.ll_collection_count);
			
			Button close = (Button) view.findViewById(R.id.btn_collection_close);
			close.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					dialogStarter(id);					
				}
			});
			
			Button refresh = (Button) view.findViewById(R.id.btn_collection_refresh);
			refresh.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					layoutVisibleChange(collectionID);			
				}
			});
			
		//Geschlossene Fragerunde	
		}else{
			if (view == null) {
	            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = vi.inflate(R.layout.collection_row_close, null);
	        }
			view = inflater.inflate(R.layout.collection_row_close, parent, false);
						
			TextView tv = (TextView) view.findViewById(R.id.tx_collection_name_close);
			tv.setText(collection.getTitle());
			TextView tx_count = (TextView) view.findViewById(R.id.tx_collection_qcount_close);
			int count = 0;
			int answers = 0;
			for(int i = 0; i<questions.size();i++){
				if(questions.get(i).getCollectionID() == collection.getId()){
					count++;
					answers += questions.get(i).questionIntCount();
				}					
			}
			tx_count.setText(count+" Fragen/ Ø"+answers/count+" Antworten");
			if(collection.getState() == 2){
				((TextView) view.findViewById(R.id.tx_collection_close_state)).setText(R.string.close);
			}else{
				((TextView) view.findViewById(R.id.tx_collection_close_state)).setText(R.string.before);
			}
		}
		return view;
	}
	
	private void refreshCollectionCount(int id){
		JSONLoader json = new JSONLoader(new Messenger(new QuestionJSONHandler(this,true)));
        json.getAllSessionsQuestions(sessionID);
	}
	
	public int getCollectionID(){
		return collectionID;
	}
	
	private void dialogStarter(final int coll_id){

		AlertDialog.Builder builder;
		//AlertDialog alertDialog;
		builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.Fragerundentext)
        .setPositiveButton("Schließen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(0,context)));
		        json.setCollectionActive(coll_id, 2);
		        for(int i = 0; i < collections.size();i++){
		        	if(collections.get(i).getId() == coll_id){
		        		collections.get(i).setState(2);
		        	}
		        }
		        layoutVisibleChange(0);
            }
        })
        .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
		builder.create();
		builder.show();
	}
	
	
	public void addCollections(JSONArray serverDaten){
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				collections.add(new Collection(
						Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")),
						serverDaten.getJSONObject(i).getString("title"),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("active"))));
			} catch (Exception e) {
				Log.d("CollectionAdapter", "problem bei i = "+i);
				continue;
			}
		}
		JSONLoader json = new JSONLoader(new Messenger(new QuestionJSONHandler(this,false)));
        json.getAllSessionsQuestions(sessionID);
        
	}
	
	public void addQuestions(JSONArray serverDaten){
		questions.clear();
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				questions.add(new Question(
						Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")), 
						Integer.parseInt(serverDaten.getJSONObject(i).getString("position")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_a")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_b")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_c")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_d")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("collection_id"))));
			} catch (Exception e) {
				Log.d("CollectionAdapter Question", "problem bei i = "+i);
				continue;
			}
		}
		pd.dismiss();
		this.notifyDataSetChanged();
	}
	public void refreshQuestions(JSONArray serverDaten){
		questions.clear();
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				questions.add(new Question(
						Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")), 
						Integer.parseInt(serverDaten.getJSONObject(i).getString("position")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_a")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_b")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_c")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_d")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("collection_id"))));
			} catch (Exception e) {
				Log.d("CollectionAdapter Question", "problem bei i = "+i);
				continue;
			}
		}
			ll.removeAllViews();
	        for(int i = 0; i< questions.size();i++){
	        	if(questions.get(i).getCollectionID() == collectionID){
		        	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);         
		        	View view = inflater.inflate(R.layout.collection_count,null);
		        	TextView q = (TextView) view.findViewById(R.id.tx_collection_question_number);
		        	q.setText(questions.get(i).getPosition());
		        	TextView c = (TextView) view.findViewById(R.id.tx_collection_question_number_count);
		        	c.setText(questions.get(i).questionCount());
		        	ll.addView(view);
	        	}
	        }		
	}
	
	public void layoutVisibleChange(int collectionid){
		this.collectionID = collectionid;
		this.notifyDataSetChanged();
	}
}
