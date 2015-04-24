package fr.epf.medfile.daos.user;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.epf.medfile.daos.DBOpenHelper;
import fr.epf.medfile.models.Patient.Sex;
import fr.epf.medfile.models.User;
import fr.epf.medfile.utils.Utils;

public class UserServiceImpl implements UserService {

	private DBOpenHelper openHelper;
	private User connectedUser = null;

	public UserServiceImpl(Context context) {
		openHelper = new DBOpenHelper(context);
	}

	@Override
	public long addUser(int ID, String firstName, String lastName, String cps,
    		String birthDate, String sex, String service, String address,
			String city, String zipCode, String email, String phoneNumber,
			String healthInfos, String birthAddress, String password,
			String login) {
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.ID_USER, ID);
		values.put(DBOpenHelper.FIRST_NAME, firstName);
		values.put(DBOpenHelper.LAST_NAME, lastName);
		values.put(DBOpenHelper.BIRTH_DATE, birthDate);
		values.put(DBOpenHelper.SEX, sex);
		values.put(DBOpenHelper.CPS, cps);
		values.put(DBOpenHelper.ADDRESS, address);
		values.put(DBOpenHelper.EMAIL, email);
		values.put(DBOpenHelper.PHONE_NUMBER, phoneNumber);
		values.put(DBOpenHelper.SERVICE, service);
		values.put(DBOpenHelper.LOGIN, login);
		values.put(DBOpenHelper.PASSWORD, password);
		values.put(DBOpenHelper.CITY, city);
		values.put(DBOpenHelper.ZIP_CODE, zipCode);
		values.put(DBOpenHelper.BIRTH_ADDRESS, birthAddress);
		values.put(DBOpenHelper.HEALTH_INFOS, healthInfos);

		SQLiteDatabase bdd = openHelper.getWritableDatabase();
		long id = bdd.insert(DBOpenHelper.TABLE_USER, null, values);
		bdd.close();
		return id;
	}

	@Override
	public User getUser(int id) {
		User user;
		SQLiteDatabase bdd = openHelper.getReadableDatabase();
		Cursor cursor = bdd.query(DBOpenHelper.TABLE_USER, null,
				DBOpenHelper.ID_USER + " =?", new String[] {id+""}, null, null, null);
		cursor.moveToFirst();
		user = cursor2User(cursor);
		cursor.close();
		bdd.close();
		return user;
	}

	// @Override
	// public User getUserAtPosition(int pos) {
	// User user;
	// SQLiteDatabase bdd = openHelper.getReadableDatabase();
	// Cursor cursor = bdd.query(DBOpenHelper.TABLE_USER,
	// null,
	// null,
	// null,
	// null,
	// null,
	// DBOpenHelper.FIRST_NAME + "," + DBOpenHelper.LAST_NAME,
	// pos+",1");
	// cursor.moveToFirst();
	// user = cursor2User(cursor);
	// cursor.close();
	// return user;
	// }

	@Override
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		User user;
		SQLiteDatabase bdd = openHelper.getReadableDatabase();
		Cursor cursor = bdd.query(DBOpenHelper.TABLE_USER, null, null, null,
				null, null, DBOpenHelper.FIRST_NAME + ","
						+ DBOpenHelper.LAST_NAME);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			user = cursor2User(cursor);
			users.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		bdd.close();
		return users;
	}

	@Override
	public boolean removeUser(int id) {
		SQLiteDatabase bdd = openHelper.getWritableDatabase();
		bdd.delete(DBOpenHelper.TABLE_USER, DBOpenHelper.ID_USER + " =?",
				new String[] {id+""});
		bdd.close();
		return true;
	}

	@Override
	public boolean clearUsers() {
		SQLiteDatabase bdd = openHelper.getWritableDatabase();
		bdd.delete(DBOpenHelper.TABLE_USER, null, null);
		bdd.close();
		return true;
	}

	private User cursor2User(Cursor cursor) {
		User user = new User();
		user.setID(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.ID_USER)));
		user.setFirstName(cursor.getString(cursor
				.getColumnIndex(DBOpenHelper.FIRST_NAME)));
		user.setLastName(cursor.getString(cursor
				.getColumnIndex(DBOpenHelper.LAST_NAME)));
		user.setCps(cursor.getString(cursor.getColumnIndex(DBOpenHelper.CPS)));
		user.setSex(cursor.getString(cursor.getColumnIndex(DBOpenHelper.SEX))
				.contains("M") ? Sex.MALE : Sex.FEMALE);
		user.setAddress(cursor.getString(cursor
				.getColumnIndex(DBOpenHelper.ADDRESS)));
		user.setEmail(cursor.getString(cursor
				.getColumnIndex(DBOpenHelper.EMAIL)));
		user.setPhoneNumber(cursor.getString(cursor
				.getColumnIndex(DBOpenHelper.PHONE_NUMBER)));
		user.setService(cursor.getString(cursor
				.getColumnIndex(DBOpenHelper.SERVICE)));
		user.setLogin(cursor.getString(cursor
				.getColumnIndex(DBOpenHelper.LOGIN)));
		user.setPassword(cursor.getString(cursor
				.getColumnIndex(DBOpenHelper.PASSWORD)));
		user.setCity(cursor.getString(cursor.getColumnIndex(DBOpenHelper.CITY)));
		user.setZipCode(cursor.getString(cursor.getColumnIndex(DBOpenHelper.ZIP_CODE)));
		user.setBirthAddress(cursor.getString(cursor.getColumnIndex(DBOpenHelper.BIRTH_ADDRESS)));
		user.setHealthInfos(cursor.getString(cursor.getColumnIndex(DBOpenHelper.HEALTH_INFOS)));

		try {
			user.setBirthDate(Utils.parse(cursor.getString(cursor
					.getColumnIndex(DBOpenHelper.BIRTH_DATE))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getConnectedUser() {
		return connectedUser;
	}

	@Override
	public boolean connectUser(String login, String password) {
		SQLiteDatabase bdd = openHelper.getReadableDatabase();
		Cursor cursor = bdd.query(DBOpenHelper.TABLE_USER, null,
				DBOpenHelper.LOGIN + " =?" + " AND "
						+ DBOpenHelper.PASSWORD + " =?", new String[] {login,password}, null,
				null, null);
		if (!cursor.moveToFirst()) {
			bdd.close();
			return false;
		} else {
			connectedUser = cursor2User(cursor);
			bdd.close();
			return true;
		}
	}

	@Override
	public boolean isEmpty() {
		SQLiteDatabase bdd = openHelper.getReadableDatabase();
		Cursor cur = bdd.rawQuery("SELECT COUNT(*) FROM "+DBOpenHelper.TABLE_USER, null);
		if (cur != null) {
		    cur.moveToFirst();                       // Always one row returned.
		    if (cur.getInt (0) == 0) {               // Zero count means empty table.
		    	bdd.close();
		    	return true;
		    }
		}
		bdd.close();
		return false;
	}
}
