package de.bockstallmann.interaktive.vorlesung.dozent.support;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Collection;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Question;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;

public class CollectionFactory extends ArrayAdapter<Collection> {
	private ArrayList<Collection> collections;
	private ArrayList<Question> questions;
	private Context context;
	private LayoutInflater inflater;
	private int collectionID = 0;
	private View collectionView;
	private LinearLayout ll;
	
	
	public CollectionFactory(Context theContext, int textViewResourceId,ArrayList<Collection> Collection) {
		super(theContext, textViewResourceId, Collection);
		this.collections = Collection;
		this.context = theContext;
		inflater = (LayoutInflater)theContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		questions = new ArrayList<Question>();
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Collection collection = collections.get(position);
		final int id = collection.getId();
		if(collection.getId() == collectionID){
			if (view == null) {
	            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = vi.inflate(R.layout.collection_row_open, null);
	        }
			view = inflater.inflate(R.layout.collection_row_open, parent, false);

			TextView tv = (TextView) view.findViewById(R.id.tx_collection_name_open);
			tv.setText(collection.getTitle());
			
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
					refreshCollectionCount(id);					
				}
			});
			
			
		}else{
			if (view == null) {
	            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = vi.inflate(R.layout.collection_row_close, null);
	        }
			view = inflater.inflate(R.layout.collection_row_close, parent, false);
			
			
			TextView tv = (TextView) view.findViewById(R.id.tx_collection_name_close);
			tv.setText(collection.getTitle());
		}
		
		
		
		
		return view;
	}
	
	private void refreshCollectionCount(int id){
		JSONLoader json = new JSONLoader(new Messenger(new QuestionJSONHandler(this)));
        json.getAllCollectionsQuestions(id);
	}
	
	private void dialogStarter(final int id){

		AlertDialog.Builder builder;
		//AlertDialog alertDialog;
		builder = new AlertDialog.Builder(context);
		builder.setMessage("Wollen Sie wirklich die Fragerunde schlieﬂen?")
        .setPositiveButton("Schlieﬂen", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(0,context)));
		        json.setCollectionActive(id, 2);
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
						serverDaten.getJSONObject(i).getString("title")));
			} catch (Exception e) {
				Log.d("CollectionAdapter", "problem bei i = "+i);
				continue;
			}
		}
		this.notifyDataSetChanged();
	}
	
	public void addQuestions(JSONArray serverDaten){
		
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				questions.add(new Question(
						Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")), 
						Integer.parseInt(serverDaten.getJSONObject(i).getString("position")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_a")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_b")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_c")),
						Integer.parseInt(serverDaten.getJSONObject(i).getString("count_d"))));
			} catch (Exception e) {
				Log.d("CollectionAdapter Question", "problem bei i = "+i);
				continue;
			}
		}
        for(int i = 0; i< questions.size();i++){
        	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);         
        	View view = inflater.inflate(R.layout.collection_count,null);
        	TextView q = (TextView) view.findViewById(R.id.tx_collection_question_number);
        	q.setText(questions.get(i).getPosition());
        	TextView c = (TextView) view.findViewById(R.id.tx_collection_question_number_count);
        	c.setText(questions.get(i).questionCount());
        	ll.addView(view);
        }
	}
	
	public void layoutVisibleChange(int collectionid){
		this.collectionID = collectionid;
		this.notifyDataSetChanged();
	}
}
