package fr.epf.medfile.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import fr.epf.medfile.R;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.models.Patient.Sex;
import fr.epf.medfile.models.User;
import fr.epf.medfile.utils.SharedMenu;
import fr.epf.medfile.utils.Utils;

public class AccountActivity extends Activity {

    private ImageView userImage;
    private TextView userName;
    private TextView userPhone;
    private TextView userEmail;
    private TextView userBirthday;
    private TextView userBirthLocation;
    private TextView userAddress;
    private TextView userCity;
    private TextView userZip;
    private TextView userService;
    private TextView userHealthInfo;
    private TextView userCPS;
    private TextView userLogin;

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_my_account);

	User user = ServiceProvider.getUInstance(getApplicationContext()).getConnectedUser();
	userImage = (ImageView) findViewById(R.id.user_image);
	userName = (TextView) findViewById(R.id.user_name);
	userPhone = (TextView) findViewById(R.id.phone_number);
	userEmail = (TextView) findViewById(R.id.e_mail);
	userBirthday = (TextView) findViewById(R.id.birthday);
	userBirthLocation = (TextView) findViewById(R.id.birthLocation);
	userAddress = (TextView) findViewById(R.id.user_address);
	userCity = (TextView) findViewById(R.id.user_city);
	userLogin = (TextView) findViewById(R.id.user_login);
	userZip = (TextView) findViewById(R.id.user_zip);
	userService = (TextView) findViewById(R.id.user_location);
	userHealthInfo = (TextView) findViewById(R.id.user_health_info);
	userCPS = (TextView) findViewById(R.id.user_cps_number);

	int sex = user.getSex() == Sex.MALE ? R.drawable.male : R.drawable.female;
	userImage.setImageResource(sex);

	userName.setText(user.getFirstName() + " " + user.getLastName());
	userPhone.setText(user.getPhoneNumber());
	userPhone.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		Intent callIntent = new Intent(Intent.ACTION_DIAL);
		callIntent.setData(Uri.parse("tel:" + Uri.encode(userPhone.getText().toString())));
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(callIntent);
	    }
	});
	userEmail.setText(user.getEmail());
	userEmail.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View view) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
		emailIntent.setType("message/rfc822");
		System.out.println(userEmail.getText().toString());
		emailIntent.putExtra(Intent.EXTRA_EMAIL, userEmail.getText().toString());
		startActivity(emailIntent);
	    }
	});
	userBirthday.setText(Utils.formatSimple(user.getBirthDate()));
	userBirthLocation.setText(user.getBirthAddress());
	userAddress.setText(user.getAddress());
	userCity.setText(user.getZipCode());
	userZip.setText(user.getCity());
	userService.setText(user.getService());
	userCPS.setText(user.getCps());
	userHealthInfo.setText(user.getHealthInfos());
	userLogin.setText(user.getLogin());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	if (SharedMenu.onOptionsItemSelected(item, this) == false) {
	}
	return super.onOptionsItemSelected(item);
    }
}
