package de.bockstallmann.interaktive.vorlesung.dozent.support;

public interface Constants {
	
	public static final int RC_ADD_COURSE = 1;
	
		
	/**
	 * JSON Serverresponse Namen.
	 */
	public static final String JSON_STATUS = "status";
	public static final String JSON_DATEN = "daten";
	public static final String JSON_MESSAGE = "message";
	public static final String JSON_ID = "id";
	
	/**
	* Logindaten
	*/
	public static final String LOGIN_UNAME ="uname";
	public static final String LOGIN_PW ="pw";
	
	/**
	 * Constants für Messenger message.
	 */
	public static final int MSG_ERROR = 0;
	public static final int MSG_SUCCESS = 1;
	
	/**
	 * Constants für Course
	 */
	public static final String COURSE_ID = "course_id";
	public static final String COURSE_TITLE = "course_title";

	/**
	 * Constants für Session
	 */
	public static final String SESSION_ID = "session_id";
	public static final String SESSION_TITLE = "session_title";
}
