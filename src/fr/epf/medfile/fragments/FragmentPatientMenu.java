package fr.epf.medfile.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.epf.medfile.R;
import fr.epf.medfile.adapters.CategoryAdapter;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.daos.ressource.RessourceCategoryListListener;

public class FragmentPatientMenu extends ListFragment {
	public final static String TAG = FragmentPatientMenu.class.getSimpleName();
	private List<String> categories;
	private CategoryAdapter adapter;
	private List<RessourceCategoryListListener> listeners = new ArrayList<RessourceCategoryListListener>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Intent intent = this.getActivity().getIntent();
		int idPatient = intent.getIntExtra("idPatient", -1);
		int idCategory = intent.getIntExtra("idCategory", 0);
		categories = ServiceProvider.getRInstance(
				this.getActivity().getApplicationContext()).getCategories(
				idPatient);
		categories.add(0, getResources().getString(R.string.general_info));
		categories.add(1, getResources().getString(R.string.news));

		adapter = new CategoryAdapter(this.getActivity(), categories, idCategory);
		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void addListener(RessourceCategoryListListener listener) {
		listeners.add(listener);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		v.setSelected(true);
		((ArrayAdapter<String>) this.getListAdapter()).notifyDataSetChanged();

		for (RessourceCategoryListListener el : listeners)
			el.onCategorySelect(position);
		System.err.println("CLICKED");
		super.onListItemClick(l, v, position, id);
	}

	public List<String> getCategories() {
		return categories;
	}

	public void getFragment() {
	}
}
