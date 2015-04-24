package fr.epf.medfile.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.epf.medfile.R;
import fr.epf.medfile.fragments.AddPatientDialogFragment;

public class AddPatientActivity extends FragmentActivity {
    private Button addPatient;

    protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);

	setContentView(R.layout.fragment_add_patient_information);

	addPatient = (Button) findViewById(R.id.add_patient_button);
	addPatient.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		AddPatientDialogFragment dialog = new AddPatientDialogFragment(AddPatientActivity.this);
		FragmentManager fm = getSupportFragmentManager();
		dialog.show(fm, "tag");
	    }
	});

	Button choixsex = (Button) findViewById(R.id.female_option);
	choixsex.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
	    }
	});

	Button choix2 = (Button) findViewById(R.id.male_option);
	choix2.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
	    }
	});
    }
}
