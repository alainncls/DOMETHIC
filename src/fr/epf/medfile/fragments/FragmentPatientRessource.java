package fr.epf.medfile.fragments;

import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.epf.medfile.adapters.RessourceAdapter;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.daos.ressource.RessourceImageListener;
import fr.epf.medfile.models.Ressource;

public class FragmentPatientRessource extends ListFragment {
	public final static String TAG = FragmentPatientRessource.class
			.getSimpleName();
	private List<Ressource> ressources;
	private RessourceAdapter adapter;

	public FragmentPatientRessource() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Intent intent = this.getActivity().getIntent();
		int idPatient = intent.getIntExtra("idPatient", -1);
		String category = this.getArguments().getString("category");
		// Get the proper ressources
		ressources = ServiceProvider.getRInstance(
				this.getActivity().getApplicationContext()).getRessources(
				idPatient, category);
		adapter = new RessourceAdapter(getActivity(), ressources);
		this.setListAdapter(adapter);
		adapter.setListener((RessourceImageListener) this.getActivity());

		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
