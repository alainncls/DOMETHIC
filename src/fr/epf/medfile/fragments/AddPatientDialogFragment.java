package fr.epf.medfile.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import fr.epf.medfile.R;
import fr.epf.medfile.activities.NewsActivity;

@SuppressLint("ValidFragment")
public class AddPatientDialogFragment extends DialogFragment {
    private Button confirm;
    private Button cancel;

    public AddPatientDialogFragment() {
    }

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	View view = inflater.inflate(R.layout.dialog_fragment, container);
	getDialog().setTitle(R.string.add_patient_confirmation);
	confirm = (Button) view.findViewById(R.id.confirm_add_news);
	confirm.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		// dismiss();
		Intent intent = new Intent(context, NewsActivity.class);
		startActivity(intent);

	    }
	});

	cancel = (Button) view.findViewById(R.id.cancel_add_news);
	cancel.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		dismiss();
	    }
	});

	System.err.println("HELLO WORLD inside2");
	return view;
    }

    public AddPatientDialogFragment(Context context) {
	super();
	this.context = context;
    }
}
