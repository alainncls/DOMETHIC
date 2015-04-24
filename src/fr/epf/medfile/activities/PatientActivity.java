package fr.epf.medfile.activities;

import java.util.List;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.imagezoom.ImageAttacher;

import fr.epf.medfile.R;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.daos.ressource.RessourceCategoryListListener;
import fr.epf.medfile.daos.ressource.RessourceImageListener;
import fr.epf.medfile.fragments.FragmentPatientInformation;
import fr.epf.medfile.fragments.FragmentPatientMenu;
import fr.epf.medfile.fragments.FragmentPatientNews;
import fr.epf.medfile.fragments.FragmentPatientRessource;
import fr.epf.medfile.models.Patient;
import fr.epf.medfile.utils.SharedMenu;

public class PatientActivity extends FragmentActivity implements
		RessourceCategoryListListener, RessourceImageListener {
	private FragmentManager manager;
	private int idPatient;
	private FragmentPatientMenu fragmentPatientMenu;

	private ImageView imvPreview;
	private Button btnClose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient);

		imvPreview = (ImageView) findViewById(R.id.fullview);
		btnClose = (Button) findViewById(R.id.close);
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imvPreview.setVisibility(View.GONE);
				btnClose.setVisibility(View.GONE);

			}
		});

		manager = getFragmentManager();

		fragmentPatientMenu = new FragmentPatientMenu();
		fragmentPatientMenu.setArguments(getIntent().getExtras());
		fragmentPatientMenu.addListener(this);

		FragmentTransaction ft = manager.beginTransaction();

		ft.add(R.id.patient_menu, fragmentPatientMenu);

		ft.commit();

		View view = this.getWindow().getDecorView();
		view.setBackgroundColor(getResources()
				.getColor(R.color.very_light_blue));

		Intent intent = this.getIntent();
		idPatient = intent.getIntExtra("idPatient", -1);
		int idCategory = intent.getIntExtra("idCategory", 0);
		Patient p = ServiceProvider.getPInstance(this).getPatient(idPatient);
		this.setTitle(p.getFirstName() + " " + p.getLastName());
		onCategorySelect(idCategory);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		menu.findItem(R.id.action_add_news).setVisible(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (SharedMenu.onOptionsItemSelected(item, this) == false) {
			// handle local menu items here or leave blank
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCategorySelect(int position) {
		List<String> categories = ServiceProvider.getRInstance(
				getApplicationContext()).getCategories(idPatient);
		categories.add(0, "General Information");
		categories.add(1, "News");
		getIntent().getExtras().remove("idCategory");
		getIntent().putExtra("idCategory", position);

		switch (position) {
		case 0: {
			FragmentPatientInformation fragmentPatientInformation = new FragmentPatientInformation();
			fragmentPatientInformation.setArguments(getIntent().getExtras());
			manager.beginTransaction()
					.replace(R.id.patient_content, fragmentPatientInformation)
					.commit();
			break;
		}
		case 1: {
			FragmentPatientNews patientNewsFragment = new FragmentPatientNews();
			patientNewsFragment.setArguments(getIntent().getExtras());

			manager.beginTransaction()
					.replace(R.id.patient_content, patientNewsFragment)
					.commit();
			break;
		}
		default: {
			// Create the Fragment
			FragmentPatientRessource patientRessourcesFragment = new FragmentPatientRessource();
			Bundle a = getIntent().getExtras();
			a.putString("category", categories.get(position));
			patientRessourcesFragment.setArguments(a);
			manager.beginTransaction()
					.replace(R.id.patient_content, patientRessourcesFragment)
					.commit();
			break;
		}
		}
	}

	@Override
	public void onImageSelect(Bitmap image) {
		Log.i("Patient Activity", "On a cliqué");
		imvPreview.setImageBitmap(image);
		new ImageAttacher(imvPreview);
		ImageAttacher.MAX_ZOOM = 5.0f; // Double the current Size
		ImageAttacher.MIN_ZOOM = 1.0f; // Half the current Size
		imvPreview.setVisibility(View.VISIBLE);
		btnClose.setVisibility(View.VISIBLE);
	}
}
