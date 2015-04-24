package fr.epf.medfile.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import fr.epf.medfile.R;
import fr.epf.medfile.utils.SharedMenu;

public class SettingsActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_settings);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	if (SharedMenu.onOptionsItemSelected(item, this) == false) {
	}
	return super.onOptionsItemSelected(item);
    }
}
