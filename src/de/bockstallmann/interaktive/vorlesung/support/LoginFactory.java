package de.bockstallmann.interaktive.vorlesung.support;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.model.Session;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class LoginFactory extends ArrayAdapter<Course>{
	
	private int id;
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Course> course;
	
	
	public LoginFactory(Context theContext, int textViewResourceId,ArrayList<Course> Course) {
		super(theContext, textViewResourceId, Course);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		course = Course;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.course_row, null);
        }
		view = inflater.inflate(R.layout.course_row, parent, false);
		
		Course courses = course.get(position);
		
		((TextView)view.findViewById(R.id.tx_course_row_title)).setText(courses.getTitle());
		((TextView)view.findViewById(R.id.tx_course_row_description)).setText(courses.getSemester()+"; ");
		
		return view;
		
	}
	
	
	public void addCourse(JSONArray serverdaten){
		
		/*for(int i = 0;i < serverdaten.length();i++){
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
		this.notifyDataSetChanged();*/
	}
	


}
