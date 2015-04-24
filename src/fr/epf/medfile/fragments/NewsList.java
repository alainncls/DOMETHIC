package fr.epf.medfile.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import fr.epf.medfile.activities.PatientActivity;
import fr.epf.medfile.adapters.NewsAdapter;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.daos.news.NewsListListener;

public class NewsList extends ListFragment {
    public NewsList() {
    }

    private List<NewsListListener> newsListener = new ArrayList<NewsListListener>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	NewsAdapter adapter = new NewsAdapter(getActivity(), ServiceProvider.getNInstance(getActivity()).getNews());
	setListAdapter(adapter);

	return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
	Log.d("TAG", "onListItemClick");
	super.onListItemClick(l, v, position, id);
	for (NewsListListener el : newsListener) {
	    el.onNewsSelect(position);
	}

	Intent intent = new Intent(this.getActivity(), PatientActivity.class);
	startActivity(intent);

	Log.d("TAG", "onListItemClick2");
    }
}
