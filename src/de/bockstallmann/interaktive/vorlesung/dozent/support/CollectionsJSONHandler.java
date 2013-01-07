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

	public CollectionsJSONHandler(CollectionFactory coll){
		cf = coll;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			cf.addCollections((JSONArray) msg.obj);
		}
	};
}
