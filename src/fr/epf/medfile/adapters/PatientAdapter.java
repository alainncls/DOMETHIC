package fr.epf.medfile.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.epf.medfile.R;
import fr.epf.medfile.models.Patient;
import fr.epf.medfile.models.Patient.Sex;

public class PatientAdapter extends ArrayAdapter<Patient> {
	private final String TAG = this.getClass().getSimpleName();

	private ImageView image;
	private TextView name;
	private TextView email;
	private TextView phone;
	private Patient patient;
	
	public PatientAdapter(Context context, List<Patient> objects) {
		super(context, 0, objects);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.entry_patients_list, parent, false);
		}
		patient = getItem(position);

		image = (ImageView) view.findViewById(R.id.patient_entry_image);
		name = (TextView) view.findViewById(R.id.patient_entry_name);
		email = (TextView) view.findViewById(R.id.patient_entry_email);
		phone = (TextView) view.findViewById(R.id.patient_entry_phone);
		
		displayPatient();
		
		int color = (position % 2 == 1? R.drawable.panel_list_light : R.drawable.panel_list_dark);
		view.setBackgroundResource(color);
		
		Log.i(TAG, "Loaded Patient : " + patient.getFirstName() + " " + patient.getLastName() );
		return view;
	}
	
	public void displayPatient() {
		int id_sex = (patient.getSex() == Sex.MALE ? R.drawable.male
				: R.drawable.female);
		image.setImageResource(id_sex);
		name.setText(patient.getFirstName() + " " + patient.getLastName());
		phone.setText(patient.getPhoneNumber());
		if (email != null) {
			email.setText(patient.getEmail());			
		}
	}
}