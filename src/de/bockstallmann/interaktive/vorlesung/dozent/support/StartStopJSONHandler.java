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
			case 4: break;//CrashSicherung am Anfang
			case 3: Toast.makeText(context, "Vorlesung gestartet!",Toast.LENGTH_SHORT ).show();break;
			case 2: Toast.makeText(context, "Vorlesung beendet!",Toast.LENGTH_SHORT ).show();break;//Zur verwendung des Handlers als Sessionbeendenhandler
			case 1: Toast.makeText(context, "Fragerunde gestartet!",Toast.LENGTH_SHORT ).show();break;
			case 0: Toast.makeText(context, "Fragerunde gestoppt!", Toast.LENGTH_SHORT).show();break;
			default: Toast.makeText(context, "Fehler bei der Abfrage!", Toast.LENGTH_SHORT).show();break; 
			}
		}
	}
}
