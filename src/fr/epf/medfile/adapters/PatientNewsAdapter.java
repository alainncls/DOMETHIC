package fr.epf.medfile.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.epf.medfile.R;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.models.News;
import fr.epf.medfile.models.User;
import fr.epf.medfile.utils.Utils;

public class PatientNewsAdapter extends ArrayAdapter<News> {
    private final static String TAG = PatientNewsAdapter.class.getSimpleName();
    private News news;
    private TextView newsTitle;
    private TextView newsContent;
    private TextView newsDate;
    private TextView newsAuthor;

    public PatientNewsAdapter(Context context, List<News> objects) {
	super(context, 0, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

	if (view == null) {
	    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    view = inflater.inflate(R.layout.entry_patient_news_list, parent, false);
	}

	news = getItem(position);
	ServiceProvider.getPInstance(getContext()).getPatient(news.getPatientID());

	newsTitle = (TextView) view.findViewById(R.id.news_patient_title);
	newsContent = (TextView) view.findViewById(R.id.news_patient_content);
	newsDate = (TextView) view.findViewById(R.id.news_patient_date);
	newsAuthor = (TextView) view.findViewById(R.id.news_patient_author);

	setContent();

	int color = (position % 2 == 1 ? R.drawable.panel_list_light : R.drawable.panel_list_dark);
	view.setBackgroundResource(color);

	Log.i(TAG, "Loaded News : " + news.getTitle());
	return view;
    }

    public void setContent() {
	newsTitle.setText(news.getTitle());
	newsContent.setText(news.getContent());
	if (newsDate != null) {
	    newsDate.setText(Utils.formatFull(news.getDate()));
	    User author = ServiceProvider.getUInstance(this.getContext()).getUser(news.getAuthorID());
	    newsAuthor.setText(author.getFirstName() + " " + author.getLastName());
	}
    }

}
