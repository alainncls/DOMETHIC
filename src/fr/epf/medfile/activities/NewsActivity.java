package fr.epf.medfile.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import fr.epf.medfile.R;
import fr.epf.medfile.adapters.NewsAdapter;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.daos.news.NewsListListener;
import fr.epf.medfile.models.News;
import fr.epf.medfile.utils.SharedMenu;

public class NewsActivity extends ListActivity implements NewsListListener {
	private final static String TAG = RepertoryActivity.class.getSimpleName();
	private List<NewsListListener> listeners = new ArrayList<NewsListListener>();
	private List<News> news;
	private NewsAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		String serviceUser = ServiceProvider
				.getUInstance(getApplicationContext()).getConnectedUser()
				.getService();
		news = ServiceProvider.getNInstance(getApplicationContext()).getNews(
				serviceUser);
		adapter = new NewsAdapter(this, news);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		for (NewsListListener el : listeners)
			el.onNewsSelect(position);
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(NewsActivity.this, PatientActivity.class);

		News news = (News) this.getListAdapter().getItem(position);
		int idPatient = news.getPatientID();
		intent.putExtra("idPatient", idPatient);
		intent.putExtra("idCategory", 1);
		Log.d(TAG, "Launching Patient Activity");
		startActivity(intent);
	}

	@Override
	public void onNewsSelect(int position) {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (SharedMenu.onOptionsItemSelected(item, this) == false) {
		}
		return super.onOptionsItemSelected(item);
	}
}
