package fr.epf.medfile.daos.ressource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import fr.epf.medfile.daos.ServiceProvider;

public class RessourceLoad extends IntentService {
    private final String TAG = this.getClass().getSimpleName();

    public RessourceLoad() {
	super("RessourceLoad");
    }

    protected void onHandleIntent(Intent arg0) {
	DefaultHttpClient httpclient = new DefaultHttpClient();
	HttpGet requete = new HttpGet("http://oenologie.epf.fr/MedFile/dataressources.json");
	String result;
	HttpResponse response;

	// On vide la BDD avant de la charger
	ServiceProvider.getRInstance(getApplicationContext()).clearRessources();

	try {
	    response = httpclient.execute(requete);

	    response.getStatusLine().getStatusCode();
	    HttpEntity entity = response.getEntity();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
		sb.append(line + "\n");
	    }
	    result = sb.toString();

	    JSONArray jArray = new JSONArray(result);

	    for (int i = 0; i < jArray.length(); i++) {
		JSONObject obj = jArray.getJSONObject(i);

		ServiceProvider.getRInstance(getApplicationContext()).addRessource(obj.getInt("id"), obj.getInt("idPatient"), obj.getInt("idAuthor"), obj.getString("title"), obj.getString("type"), obj.getString("category"), obj.getString("text"), obj.getString("img"), obj.getString("date"));
	    }
	    Log.i(TAG, "Total Ressources Load : " + jArray.length());
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }
}
