package fr.epf.medfile.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import fr.epf.medfile.R;
import fr.epf.medfile.adapters.PatientAdapter;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.daos.patient.PatientListListener;
import fr.epf.medfile.models.Patient;
import fr.epf.medfile.utils.SharedMenu;

public class RepertoryActivity extends ListActivity implements PatientListListener {
    private List<PatientListListener> listeners = new ArrayList<PatientListListener>();
    private List<Patient> patients;
    private PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	String serviceUser = ServiceProvider.getUInstance(getApplicationContext()).getConnectedUser().getService();
	patients = ServiceProvider.getPInstance(getApplicationContext()).getPatients(serviceUser);
	adapter = new PatientAdapter(this, patients);
	setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu, menu);
	menu.findItem(R.id.action_add_patient).setVisible(true);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	super.onOptionsItemSelected(item);

	if (SharedMenu.onOptionsItemSelected(item, this) == false) {
	}
	return super.onOptionsItemSelected(item);

    }

    public void addListener(PatientListListener listener) {
	listeners.add(listener);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
	for (PatientListListener el : listeners)
	    el.onPatientSelect(position);
	super.onListItemClick(l, v, position, id);

	Intent intent = new Intent(RepertoryActivity.this, PatientActivity.class);

	Patient patient = (Patient) this.getListAdapter().getItem(position);
	int idPatient = patient.getID();
	intent.putExtra("idPatient", idPatient);
	startActivity(intent);
    }

    public void onPatientSelect(int position) {
    }
}