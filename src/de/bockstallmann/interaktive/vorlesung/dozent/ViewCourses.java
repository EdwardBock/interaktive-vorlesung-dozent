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
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ViewCourses extends Activity implements OnItemClickListener {

    private LoginFactory loginFactory;
	private JSONLoader jsonLoader;
	private ListView list;
	private ArrayList<Course> courses;
	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcourses);
        String uname = getIntent().getExtras().getString(Constants.LOGIN_UNAME);
        String pw = getIntent().getExtras().getString(Constants.LOGIN_PW);
        uname = uname.replaceAll(" ","");
        pw = pw.replaceAll(" ","");
        courses = new ArrayList<Course>();
        loginFactory = new LoginFactory(this, R.layout.course_row, courses, (Spinner)findViewById(R.id.spin_ViewCourses),this);
        jsonLoader = new JSONLoader(new Messenger(new LoginJSONHandler(loginFactory, this, new User(uname,pw))));
        jsonLoader.getCoursesAfterLogin(uname, pw);
        list = (ListView)findViewById(R.id.lv_courses);
       
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		list.setAdapter(loginFactory);
		list.setOnItemClickListener(this);
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
             finish();
             break;
    	}
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	 switch (item.getItemId()) {
         case R.id.logoutUser:
             SQLDataHandler db = new SQLDataHandler(this);
             User u = db.getUser();
             db.deleteUser(u);
             finish();
             return true;

         default:
             return super.onOptionsItemSelected(item);
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
