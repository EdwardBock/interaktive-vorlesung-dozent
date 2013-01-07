package de.bockstallmann.interaktive.vorlesung.dozent;

import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.dozent.model.Course;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.interaktive.vorlesung.dozent.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.dozent.support.LoginFactory;
import de.bockstallmann.interaktive.vorlesung.dozent.support.LoginJSONHandler;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;


import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ViewCourses extends Activity implements OnItemClickListener {

    private LoginFactory loginFactory;
	private JSONLoader jsonLoader;
	private ListView list;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcourses);
        String uname = getIntent().getExtras().getString(Constants.LOGIN_UNAME);
        String pw = getIntent().getExtras().getString(Constants.LOGIN_PW);
        uname = uname.replaceAll(" ","");
        pw = pw.replaceAll(" ","");
        loginFactory = new LoginFactory(this, R.layout.course_row, new ArrayList<Course>() );
        jsonLoader = new JSONLoader(new Messenger(new LoginJSONHandler(loginFactory, this)));
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Course course = (Course) parent.getItemAtPosition(position);
		Intent intent = new Intent(this, StartSession.class);
		intent.putExtra(Constants.COURSE_ID, course.getID());
		intent.putExtra(Constants.COURSE_TITLE, course.getTitle());
		startActivity(intent);
		
	}
}
