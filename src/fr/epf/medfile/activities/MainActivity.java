package fr.epf.medfile.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import fr.epf.medfile.R;
import fr.epf.medfile.daos.news.NewsLoad;
import fr.epf.medfile.daos.patient.PatientLoad;
import fr.epf.medfile.daos.ressource.RessourceLoad;
import fr.epf.medfile.daos.user.UserLoad;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_loading);

	if (isNetworkAvailable()) {
	    Intent intent;

	    intent = new Intent(MainActivity.this, UserLoad.class);
	    startService(intent);

	    intent = new Intent(MainActivity.this, PatientLoad.class);
	    startService(intent);

	    intent = new Intent(MainActivity.this, NewsLoad.class);
	    startService(intent);

	    intent = new Intent(MainActivity.this, RessourceLoad.class);
	    startService(intent);
	}

	new CountDownTimer(3000, 1000) {
	    @Override
	    public void onTick(long miliseconds) {
	    }

	    @Override
	    public void onFinish() {
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
	    }
	}.start();
    }

    private boolean isNetworkAvailable() {
	ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
