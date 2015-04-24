package fr.epf.medfile.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import fr.epf.medfile.R;
import fr.epf.medfile.activities.AccountActivity;
import fr.epf.medfile.activities.AddNewsActivity;
import fr.epf.medfile.activities.AddPatientActivity;
import fr.epf.medfile.activities.LoginActivity;
import fr.epf.medfile.activities.NewsActivity;
import fr.epf.medfile.activities.RepertoryActivity;

public class SharedMenu {

    public static boolean onOptionsItemSelected(MenuItem item, Activity caller) {
	switch (item.getItemId()) {
	case R.id.action_go_home: {
	    Intent intent = new Intent(caller, NewsActivity.class);
	    caller.startActivity(intent);
	    return true;
	}
	case R.id.action_go_account: {
	    Intent intent = new Intent(caller, AccountActivity.class);
	    caller.startActivity(intent);
	    return true;
	}
	case R.id.action_go_repertory: {
	    Intent intent = new Intent(caller, RepertoryActivity.class);
	    caller.startActivity(intent);
	    return true;
	}
	case R.id.action_disconnect: {
	    Intent intent = new Intent(caller, LoginActivity.class);
	    caller.startActivity(intent);
	    return true;
	}
	case R.id.action_add_patient: {
	    Intent intent = new Intent(caller, AddPatientActivity.class);
	    caller.startActivity(intent);
	    return true;
	}
	case R.id.action_add_news: {
	    Intent intent = new Intent(caller, AddNewsActivity.class);
	    caller.startActivity(intent);
	    return true;
	}
	default:
	    return false;
	}
    }
}
