package de.bockstallmann.interaktive.vorlesung.dozent.support;


import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;

import de.bockstallmann.interaktive.vorlesung.dozent.model.Session;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class StartSessionFactory extends ArrayAdapter<Session>{

	

	private int id;
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Session> sessions;
	
	public StartSessionFactory(Context theContext, int textViewResourceId,
			ArrayList<Session> Sessions) {
		super(theContext, textViewResourceId, Sessions);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		sessions = Sessions;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.session_row, null);
        }
		view = inflater.inflate(R.layout.session_row, parent, false);
		
		Session session = sessions.get(position);
		
		((TextView) view.findViewById(R.id.tx_session_row_title)).setText(session.getTitle());
		((TextView) view.findViewById(R.id.tx_session_row_description)).setText(session.getRoom()+"; "+session.getBegin()+"-"+session.getEnd()+" Uhr");
		
		return view;
		
	}
	
	public void addSessions(JSONArray serverdaten){
		for(int i = 0;i < serverdaten.length();i++){
			try {
				sessions.add(new Session(serverdaten.getJSONObject(i).getInt("_id"),serverdaten.getJSONObject(i).getString("room"),
						serverdaten.getJSONObject(i).getString("info"),
						serverdaten.getJSONObject(i).getString("date_begin"),
						serverdaten.getJSONObject(i).getString("date_end")));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.d("CoursesDetailsFactory", "problem bei i = "+i);
				continue;
			}
		}
		this.notifyDataSetChanged();
	}
	


}
