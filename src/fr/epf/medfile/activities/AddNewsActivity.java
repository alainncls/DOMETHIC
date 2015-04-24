package fr.epf.medfile.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import fr.epf.medfile.R;
import fr.epf.medfile.fragments.AddNewsDialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddNewsActivity extends FragmentActivity {
    private Button addNews;

    protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_add_news);

	addNews = (Button) findViewById(R.id.add_news_button);
	addNews.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		AddNewsDialogFragment dialog = new AddNewsDialogFragment(AddNewsActivity.this);
		FragmentManager fm = getSupportFragmentManager();
		dialog.show(fm, "tag");
	    }
	});
    }
}
