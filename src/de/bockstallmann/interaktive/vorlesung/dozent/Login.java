package de.bockstallmann.interaktive.vorlesung.dozent;

import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.inveraktive.vorlesung.dozent.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class Login extends Activity {

	private Intent intent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(Login);
        intent = new Intent(this,ViewCourses.class);
    }
    View.OnClickListener Login = new View.OnClickListener() {
        public void onClick(View v) {
        	 EditText uname = (EditText) findViewById(R.id.login_user);
             EditText pw = (EditText) findViewById(R.id.login_pw);         
             intent.putExtra(Constants.LOGIN_UNAME, uname.getText().toString());
             intent.putExtra(Constants.LOGIN_PW, pw.getText().toString());
        	 startActivity(intent);
        }
      };
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

    
}
