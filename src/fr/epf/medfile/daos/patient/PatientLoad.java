package fr.epf.medfile.daos.patient;

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

public class PatientLoad extends IntentService {

    public PatientLoad() {
	super("PatientLoad");
    }

    private final String TAG = this.getClass().getSimpleName();

    @SuppressLint("SimpleDateFormat")
    protected void onHandleIntent(Intent arg0) {
	DefaultHttpClient httpclient = new DefaultHttpClient();
	HttpGet requete = new HttpGet("http://oenologie.epf.fr/MedFile/datapatients.json");
	String result;
	HttpResponse response;

	// On vide la BDD avant de la charger
	ServiceProvider.getPInstance(getApplicationContext()).clearPatients();

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

		ServiceProvider.getPInstance(getApplicationContext()).addPatient(obj.getInt("id"), obj.getString("firstName"), obj.getString("lastName"), obj.getString("socialSecurity"), obj.getString("birthdate"), obj.getString("sex"), obj.getString("address"), obj.getString("city"), obj.getString("zipCode"), obj.getString("email"), obj.getString("phoneNumber"), obj.getString("entryDate"),
			obj.getString("causes"), obj.getString("service"), obj.getString("healthInfo"), obj.getString("birthAddress"));
	    }
	    Log.i(TAG, "Total Patients Load : " + jArray.length());
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }
}
