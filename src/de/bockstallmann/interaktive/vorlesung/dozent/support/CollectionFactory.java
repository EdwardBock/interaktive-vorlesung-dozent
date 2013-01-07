package de.bockstallmann.interaktive.vorlesung.dozent.support;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Collection;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;

public class CollectionFactory extends ArrayAdapter<Collection> {
	private ArrayList<Collection> collections;
	private Context context;
	private LayoutInflater inflater;
	
	
	public CollectionFactory(Context theContext, int textViewResourceId,ArrayList<Collection> Collection) {
		super(theContext, textViewResourceId, Collection);
		this.collections = Collection;
		this.context = theContext;
		inflater = (LayoutInflater)theContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.collection_row, null);
        }
		view = inflater.inflate(R.layout.collection_row, parent, false);
		Collection collection = collections.get(position);
		
		TextView tv = (TextView) view.findViewById(R.id.tx_collection);
		tv.setText(collection.getTitle());
		
		ImageButton start = (ImageButton) view.findViewById(R.id.btn_collection_start);
		start.setTag(position);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Collection coll = collections.get(Integer.parseInt(v.getTag().toString()));
				Log.d("Position:--", ""+coll.getId());
				JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(1, context)));
				json.setCollectionActive(coll.getId(),1);
			}
		});
		
		ImageButton stop = (ImageButton) view.findViewById(R.id.btn_collection_stop);
		stop.setTag(position);
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Collection coll = collections.get(Integer.parseInt(v.getTag().toString()));
				Log.d("Position:--Stop", v.getTag().toString());
				JSONLoader json = new JSONLoader(new Messenger(new StartStopJSONHandler(0, context)));
				json.setCollectionActive(coll.getId(),0);
			}
		});
		
		
		return view;
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
}
