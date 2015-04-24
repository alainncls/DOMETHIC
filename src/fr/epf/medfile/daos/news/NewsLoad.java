package fr.epf.medfile.daos.news;

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

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import fr.epf.medfile.daos.ServiceProvider;

@SuppressLint("SimpleDateFormat")
public class NewsLoad extends IntentService {
    public NewsLoad() {
	super("NewsLoad");
    }

    private final String TAG = this.getClass().getSimpleName();

    protected void onHandleIntent(Intent arg0) {
	Log.d(TAG, "Lancement de l'ajout");

	DefaultHttpClient httpclient = new DefaultHttpClient();
	HttpGet requete = new HttpGet("http://oenologie.epf.fr/MedFile/datanews.json");
	String result;
	HttpResponse response;

	// On vide la BDD avant de la charger
	ServiceProvider.getNInstance(getApplicationContext()).clearNews();

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

		ServiceProvider.getNInstance(getApplicationContext()).addNews(obj.getInt("id"), obj.getInt("idPatient"), obj.getInt("idAuthor"), obj.getString("date"), obj.getString("newsTitle"), obj.getString("newsContent"));
	    }
	    Log.i(TAG, "Total News Load : " + jArray.length());
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }
}
