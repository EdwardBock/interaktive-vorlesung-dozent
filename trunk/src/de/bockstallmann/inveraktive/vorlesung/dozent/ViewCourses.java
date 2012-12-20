package de.bockstallmann.inveraktive.vorlesung.dozent;

import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.LoginFactory;
import de.bockstallmann.interaktive.vorlesung.support.LoginJSONHandler;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.view.Menu;

public class ViewCourses extends Activity {

    private LoginFactory loginFactory;
	private JSONLoader jsonLoader;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.);
        String uname = getIntent().getExtras().getString(Constants.LOGIN_UNAME);
        String pw = getIntent().getExtras().getString(Constants.LOGIN_PW);
        
        loginFactory = new LoginFactory(this, R.layout.course_row, new ArrayList<Course>() );
        jsonLoader = new JSONLoader(new Messenger(new LoginJSONHandler(loginFactory, this)));
        jsonLoader.getCoursesAfterLogin(uname, pw);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_courses, menu);
        return true;
    }
}
