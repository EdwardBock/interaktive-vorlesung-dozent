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
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Collection;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;

public class CollectionFactory extends ArrayAdapter<Collection> {
	private ArrayList<Collection> collections;
	private Context context;
	private LayoutInflater inflater;
	private int collectionID = 0;
	
	
	public CollectionFactory(Context theContext, int textViewResourceId,ArrayList<Collection> Collection) {
		super(theContext, textViewResourceId, Collection);
		this.collections = Collection;
		this.context = theContext;
		inflater = (LayoutInflater)theContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
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
			
			Button close = (Button) view.findViewById(R.id.btn_collection_close);
			close.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					dialogStarter(id);					
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
	
	public void layoutVisibleChange(int collectionid){
		this.collectionID = collectionid;
		this.notifyDataSetChanged();
	}
}
