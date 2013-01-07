package de.bockstallmann.interaktive.vorlesung.dozent.support;



import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class StartStopJSONHandler extends Handler{

	private int status;
	private Context context;

	public StartStopJSONHandler(int Status,Context thecontext){
		status = Status;
		context = thecontext;
	}
	public void handleMessage(Message msg) {
		Log.d("HandlerStartStop","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			switch(status){
			case 1: Toast.makeText(context, "Fragerunde gestartet!",Toast.LENGTH_SHORT ).show();break;
			case 0: Toast.makeText(context, "Fragerunde gestoppt!", Toast.LENGTH_SHORT).show();break;
			default: Toast.makeText(context, "Fehler bei der Abfrage!", Toast.LENGTH_SHORT).show();break; 
			}
		}else Toast.makeText(context, "Fehler bei der Abfrage!1", Toast.LENGTH_SHORT).show();
	};
}
