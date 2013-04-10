package de.bockstallmann.interaktive.vorlesung.dozent.handler;

import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.dozent.model.User;
import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLDataHandler extends SQLiteOpenHelper {
	
	private final static int DB_VERSION = 3;
	
	private String TABLE_LOGIN = "login";
	private String TABLE_LOGIN_USER = "user";
	private String TABLE_LOGIN_PW = "pw";
	
	public SQLDataHandler(final Context context){
		super(context, Constants.DB_NAME, null, DB_VERSION);
	}
	@Override
	public void onCreate(final SQLiteDatabase database) {
		database.execSQL(Constants.CREATE_TABLE_LOGIN);
	}
	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		// erst bei einem Update von notwendig
		onCreate(db);
	}
	public void addUser(User user){
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("SQL-add",user.getUser());
			ContentValues values = new ContentValues();
			values.put(TABLE_LOGIN_USER, user.getUser());
			values.put(TABLE_LOGIN_PW , user.getPw());
			
			db.insert(TABLE_LOGIN, null, values);
			db.close();
	}
	
	public User getUser(){
		SQLiteDatabase db = this.getReadableDatabase();
		String sqlQuery = "SELECT user,pw FROM " + TABLE_LOGIN;
		User user = null;
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if(cursor.moveToFirst()){
		
			user = new User(cursor.getString(0), cursor.getString(1));
			Log.d("SQL",user.getUser());
		}else Log.d("SQL","false");
		
		return user;
	}
	
	public void deleteUser(User user){
		SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN, TABLE_LOGIN_USER + " = ?",
                new String[] { String.valueOf(user.getUser()) });
        db.close();
	}
}
