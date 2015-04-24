package fr.epf.medfile.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import fr.epf.medfile.R;
import fr.epf.medfile.activities.AddNewsActivity;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.models.Patient;
import fr.epf.medfile.utils.Utils;

public class FragmentPatientInformation extends Fragment {
    public final static String TAG = FragmentPatientInformation.class.getSimpleName();
    private ImageView patientImage;
    private TextView patientName;
    private TextView patientPhone;
    private TextView patientEmail;
    private TextView patientBirthday;
    private TextView patientBirthLocation;
    private TextView patientAddress;
    private TextView patientCity;
    private TextView patientZip;
    private TextView patientEntryDate;
    private TextView patientAdmissionCauses;
    private TextView patientService;
    private TextView patientHealthInfo;

    public FragmentPatientInformation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_patient_information, container, false);
	view.setBackgroundColor(getResources().getColor(R.color.very_light_blue));

	Intent intent = this.getActivity().getIntent();
	int idPatient = intent.getIntExtra("idPatient", -1);
	Patient patient = ServiceProvider.getPInstance(this.getActivity().getApplicationContext()).getPatient(idPatient);

	patientImage = (ImageView) view.findViewById(R.id.patient_image);
	patientName = (TextView) view.findViewById(R.id.patient_name);
	patientPhone = (TextView) view.findViewById(R.id.phone_number);
	patientEmail = (TextView) view.findViewById(R.id.e_mail);
	patientBirthday = (TextView) view.findViewById(R.id.birthday);
	patientBirthLocation = (TextView) view.findViewById(R.id.birthLocation);
	patientAddress = (TextView) view.findViewById(R.id.patient_address);
	patientCity = (TextView) view.findViewById(R.id.patient_city);
	patientZip = (TextView) view.findViewById(R.id.patient_zip);
	patientEntryDate = (TextView) view.findViewById(R.id.patient_entry_date);
	patientAdmissionCauses = (TextView) view.findViewById(R.id.patient_admission_causes);
	patientService = (TextView) view.findViewById(R.id.patient_location);
	patientHealthInfo = (TextView) view.findViewById(R.id.patient_health_info);

	setPatient(patient);
	return view;
    }

    public void setPatient(Patient patient) {
	int sex = patient.getSex() == Patient.Sex.MALE ? R.drawable.male : R.drawable.female;
	patientImage.setImageDrawable(getResources().getDrawable(sex));
	patientName.setText(patient.getFirstName() + " " + patient.getLastName());
	patientPhone.setText(patient.getPhoneNumber());
	patientPhone.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		Intent callIntent = new Intent(Intent.ACTION_DIAL);
		callIntent.setData(Uri.parse("tel:" + Uri.encode(patientPhone.getText().toString())));
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(callIntent);
	    }
	});
	patientEmail.setText(patient.getEmail());
	patientEmail.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
		emailIntent.setType("message/rfc822");
		String Email = new String(patientEmail.getText().toString());
		System.out.println(Email);
		emailIntent.putExtra(Intent.EXTRA_EMAIL, (Email));
		startActivity(emailIntent);
	    }
	});

	patientBirthday.setText(Utils.formatSimple(patient.getBirthDate()));
	patientBirthLocation.setText(patient.getBirthAddress());

	patientAddress.setText(patient.getAddress());
	patientCity.setText(patient.getZipCode());
	patientZip.setText(patient.getCity());

	patientEntryDate.setText(Utils.formatSimple(patient.getEntryDate()));
	patientAdmissionCauses.setText(patient.getCauses());
	patientService.setText(patient.getService());

	patientHealthInfo.setText(patient.getHealthInfos());
    }
}
