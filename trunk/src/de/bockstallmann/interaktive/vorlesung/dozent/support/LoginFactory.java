package de.bockstallmann.interaktive.vorlesung.dozent.support;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import de.bockstallmann.interaktive.vorlesung.dozent.model.Course;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Session;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;
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
	
	
	public LoginFactory(Context theContext, int textViewResourceId,ArrayList<Course> arrayList) {
		super(theContext, textViewResourceId, arrayList);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		course = arrayList;
		
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
	
	
	public void addCourse(JSONArray serverDaten){
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				course.add(new Course(
						Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")), 
						serverDaten.getJSONObject(i).getString("titel"), 
						serverDaten.getJSONObject(i).getString("user_id"),
						serverDaten.getJSONObject(i).getString("semester"),
						serverDaten.getJSONObject(i).getString("pw")));
			} catch (Exception e) {
				Log.d("CoursesAdapter", "problem bei i = "+i);
				continue;
			}
		}
		this.notifyDataSetChanged();
	}
	


}
