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
	
	public StartSessionFactory(Context theContext, int textViewResourceId, ArrayList<Session> arrayList) {
		super(theContext, textViewResourceId, arrayList);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		sessions = arrayList;
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
		String time_begin = session.getBegin().substring(session.getBegin().indexOf(" ")+1);
		String time_end = session.getEnd().substring(session.getEnd().indexOf(" ")+1);
		String date = session.getBegin().substring(0, session.getBegin().indexOf(" "));
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		date = day+"."+month+"."+year;
		
		((TextView) view.findViewById(R.id.tx_session_row_title)).setText(session.getTitle());
		((TextView) view.findViewById(R.id.tx_session_row_description)).setText(session.getRoom()+"; "+time_begin+"-"+time_end+" Uhr  "+date);
		
		return view;
		
	}
	
	public void addSessions(JSONArray serverdaten){
		for(int i = 0;i < serverdaten.length();i++){
			try {
				sessions.add(new Session(Integer.parseInt(serverdaten.getJSONObject(i).getString("_id")),
						serverdaten.getJSONObject(i).getString("room"),
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
