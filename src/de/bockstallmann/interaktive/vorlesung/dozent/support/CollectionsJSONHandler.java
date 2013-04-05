package de.bockstallmann.interaktive.vorlesung.dozent.support;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.dozent.support.Constants;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class CollectionsJSONHandler extends Handler {

	private CollectionFactory cf;
	private StartSessionFactory ssf;

	public CollectionsJSONHandler(CollectionFactory coll){
		cf = coll;
	}
	public CollectionsJSONHandler(StartSessionFactory ssfa){
		ssf = ssfa;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			if(ssf != null){
				Log.d("CollectJSON Handler", msg.obj.toString());
				ssf.addCollections((JSONArray) msg.obj);
			}else if(cf != null){
				cf.addCollections((JSONArray) msg.obj);
			}
		}else{
			if(ssf != null){
				ssf.pdDismiss();
			}
		}
	};
}
