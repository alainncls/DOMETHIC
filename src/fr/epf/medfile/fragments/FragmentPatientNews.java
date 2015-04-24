package fr.epf.medfile.fragments;

import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.epf.medfile.adapters.PatientNewsAdapter;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.models.News;

public class FragmentPatientNews extends ListFragment {
    public final static String TAG = FragmentPatientNews.class.getSimpleName();
    private List<News> news;
    private PatientNewsAdapter adapter;

    public FragmentPatientNews() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	Intent intent = this.getActivity().getIntent();
	int idPatient = intent.getIntExtra("idPatient", -1);

	// Get the proper ressources
	news = ServiceProvider.getNInstance(this.getActivity().getApplicationContext()).getNewsPatient(idPatient);
	adapter = new PatientNewsAdapter(getActivity(), news);
	this.setListAdapter(adapter);

	return super.onCreateView(inflater, container, savedInstanceState);
    }
}
