package de.bockstallmann.interaktive.vorlesung.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ServerCommunication {
	
	private final static String BASE_URL = "http://iv.jstallmann.com/";
	
	public static JSONArray getJSONDaten(final String script_pfad){

		// Aufruf vorbereiten
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(BASE_URL+script_pfad);
		HttpResponse response;
		
		try {
			// Serveradresse aufrufen
			response = httpClient.execute(httpGet);
			
			// TODO: Response überprüfen: "z.B. 200 ok" oder "404"
			Log.d("ServerConnection","Response: "+response.getStatusLine().toString());

			// Content aus der Requestrückgabe herausholen und über InputStreamReader in String übernehmen
			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) sb.append(line);
			String result = sb.toString();
			instream.close();
			
			Log.d("ServerConnection","Antwortstring: "+result);
			
			// Antwortstring in JSON Array verarbeiten			
			JSONObject json =new JSONObject(result);
			String status = json.getString(Constants.JSON_STATUS);
			Log.d("ServerConnection", "Rückgabestatus: "+status);
			
			JSONArray nameArray = json.names();
			
			Log.d("ServerConnection", "NameArray: "+nameArray.toString());
			
			JSONArray datenObj = json.getJSONArray(Constants.JSON_DATEN);
			
			Log.d("ServerConnection", "DatenObj: "+datenObj.toString());
			
			return datenObj;
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}finally{
			httpGet.abort();
		}
	}
}
