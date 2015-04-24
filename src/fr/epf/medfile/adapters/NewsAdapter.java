package fr.epf.medfile.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.epf.medfile.R;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.models.News;
import fr.epf.medfile.models.Patient;
import fr.epf.medfile.models.Patient.Sex;
import fr.epf.medfile.utils.Utils;

public class NewsAdapter extends ArrayAdapter<News> {
    private final static String TAG = NewsAdapter.class.getSimpleName();
    private News news;
    private ImageView newsImage;
    private TextView newsName;
    private TextView newsTitle;
    private TextView newsContent;
    private TextView newsDate;

    public NewsAdapter(Context context, List<News> objects) {
	super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	if (convertView == null) {
	    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    convertView = inflater.inflate(R.layout.entry_news_list, parent, false);
	}

	news = getItem(position);
	Patient patient = ServiceProvider.getPInstance(getContext()).getPatient(news.getPatientID());

	newsName = (TextView) convertView.findViewById(R.id.patient_name);
	newsTitle = (TextView) convertView.findViewById(R.id.news_title);
	newsContent = (TextView) convertView.findViewById(R.id.news_content);
	newsImage = (ImageView) convertView.findViewById(R.id.profile_picture);
	newsDate = (TextView) convertView.findViewById(R.id.news_date);

	displayNews(patient);

	int color = (position % 2 == 1 ? R.drawable.panel_list_light : R.drawable.panel_list_dark);
	convertView.setBackgroundResource(color);

	Log.i(TAG, "Loaded New");
	return convertView;
    }

    public void displayNews(Patient patient) {
	newsName.setText(patient.getFirstName() + " " + patient.getLastName());
	newsTitle.setText(news.getTitle());
	newsContent.setText(news.getContent());
	if (newsDate != null) {
	    newsDate.setText(Utils.formatFull(news.getDate()));
	    int id_sex = (patient.getSex() == Sex.MALE ? R.drawable.male : R.drawable.female);
	    newsImage.setImageResource(id_sex);
	}
    }
}
