package de.bockstallmann.interaktive.vorlesung.dozent.support;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import de.bockstallmann.interaktive.vorlesung.dozent.ViewCourses;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Course;
import de.bockstallmann.interaktive.vorlesung.dozent.model.Session;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class LoginFactory extends ArrayAdapter<Course>{
	
	private int id;
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Course> course;
	private ArrayList<Course> allcourses;
	private Spinner semester;
	private ArrayList<String> entrys;
	private Context viewC;
	
	public LoginFactory(Context theContext, int textViewResourceId,ArrayList<Course> arrayList, Spinner semester, Context vc) {
		super(theContext, textViewResourceId, arrayList);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		course = arrayList;
		entrys = new ArrayList<String>();
		
		this.semester = semester;
		viewC = vc;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.course_row, null);
        }
		view = inflater.inflate(R.layout.course_row, parent, false);
		
		ImageView pw = (ImageView) view.findViewById(R.id.lock_courseView);
		Course courses = course.get(position);
		if(courses.hasPassword()){
			pw.setImageResource(R.drawable.ic_lock_lock);
		}else{
			pw.setImageResource(R.drawable.ic_lock_open);
		}
		((TextView)view.findViewById(R.id.tx_course_row_title)).setText(courses.getTitle());
		((TextView)view.findViewById(R.id.tx_course_row_description)).setText(courses.getSemester()+"\n"+courses.getYear());
				
		return view;
		
	}
	
	public void setVisibleCourse(String selectedsem){
		String[] sem = selectedsem.split(" ");
		course.clear();
		for(int i = 0; i < allcourses.size();i++){
			if(allcourses.get(i).getSemester().equals(sem[0]) && allcourses.get(i).getYear().equals(sem[1])){
				course.add(allcourses.get(i));
			}
			if(selectedsem.equals("Alle Semester")){
				course.add(allcourses.get(i));
			}
		}
		this.notifyDataSetChanged();
	}
	
	
	public void addCourse(JSONArray serverDaten){
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				course.add(new Course(
						Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")), 
						serverDaten.getJSONObject(i).getString("title"), 
						serverDaten.getJSONObject(i).getString("user_id"),
						serverDaten.getJSONObject(i).getString("semester"),
						serverDaten.getJSONObject(i).getString("year"),
						serverDaten.getJSONObject(i).getString("pw")));
			} catch (Exception e) {
				Log.d("CoursesAdapter", "problem bei i = "+i);
				continue;
			}
		}
		allcourses = (ArrayList<Course>) course.clone();
		this.notifyDataSetChanged();
		entrys.clear();
		entrys.add("Alle Semester");
		fillSpinner();
	}
	
	public void fillSpinner(){
		
		for(int i = 0; i < course.size(); i++){
			String temp = course.get(i).getSemester()+" "+course.get(i).getYear();
			boolean exist = false;
			if(entrys.size()==0){
				entrys.add(temp);
			}
			for(int y=0; y < entrys.size(); y++){	
					if(temp.equals(entrys.get(y))){
						exist = true;
					}
			}
			if(!exist){
				entrys.add(temp);
			}
		}
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(viewC,R.layout.spinner_layout, entrys);
		spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
		semester.setAdapter(spinnerArrayAdapter);
		
		
	}
	


}
