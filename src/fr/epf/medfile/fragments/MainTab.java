package fr.epf.medfile.fragments;

import fr.epf.medfile.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainTab extends Fragment {

	public MainTab() {
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View convertView = inflater.inflate(R.layout.main_tab, container,false);
			return convertView;
	}
}
