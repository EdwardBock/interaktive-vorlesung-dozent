package de.bockstallmann.interaktive.vorlesung.dozent;

import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.dozent.model.Course;
import de.bockstallmann.interaktive.vorlesung.dozent.model.User;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.interaktive.vorlesung.dozent.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.dozent.support.LoginFactory;
import de.bockstallmann.interaktive.vorlesung.dozent.support.LoginJSONHandler;
import de.bockstallmann.interaktive.vorlesung.dozent.support.SQLDataHandler;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;


import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewCourses extends Activity implements OnItemClickListener {

    private LoginFactory loginFactory;
	private JSONLoader jsonLoader;
	private ListView list;
	private ArrayList<Course> courses;
	private Spinner semester;
	private SQLDataHandler db;
	private Intent intent;
	private String uname = "";
	private String pw = "";

	private void loginCheck(){
		if(db.getUser() == null){	
			if(uname == null){
				uname = "";
		        pw = "";  
	        	startActivity(intent);
	       	 	finish();
	        }
	    }else{
        	uname = db.getUser().getUser();
          	pw = db.getUser().getPw();
        }
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcourses);
        //if(getIntent().getStringExtra(Constants.LOGIN_UNAME) != ""){
        	uname = getIntent().getStringExtra(Constants.LOGIN_UNAME);
            pw = getIntent().getStringExtra(Constants.LOGIN_PW);
            Log.d("Username check",""+uname);
        //}       
        db = new SQLDataHandler(this);
        intent = new Intent(this, Login.class);
        loginCheck();
               
        uname = uname.replaceAll(" ","");
        pw = pw.replaceAll(" ","");
        semester = (Spinner)findViewById(R.id.spin_ViewCourses);
        courses = new ArrayList<Course>();
        loginFactory = new LoginFactory(this, R.layout.course_row, courses, semester,this);
        jsonLoader = new JSONLoader(new Messenger(new LoginJSONHandler(loginFactory, this, new User(uname,pw))));
        jsonLoader.getCoursesAfterLogin(uname, pw);
        list = (ListView)findViewById(R.id.lv_courses);
        uname = "";
        pw = "";       
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		loginCheck();
		list.setAdapter(loginFactory);
		list.setOnItemClickListener(this);
		semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                loginFactory.setVisibleCourse(parent.getItemAtPosition(pos).toString());
                Toast.makeText(getBaseContext(), 
                		parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
	}
	
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_courses, menu);
        return true;
    }
    
    public void actionBarClick(final View v){
    	switch(v.getId()){
    	case R.id.btn_logout:
    		 SQLDataHandler db = new SQLDataHandler(this);
             User u = db.getUser();
             db.deleteUser(u);
             startActivity(intent);
	       	 finish();
             break;
    	}
    }
    
    

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Course course = (Course) parent.getItemAtPosition(position);
		Intent intent = new Intent(this, StartSession.class);
		intent.putExtra(Constants.COURSE_ID, course.getID());
		intent.putExtra(Constants.COURSE_TITLE, course.getTitle());
		startActivity(intent);
		
	}
}
