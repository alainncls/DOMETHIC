package fr.epf.medfile.daos.patient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fr.epf.medfile.daos.DBOpenHelper;
import fr.epf.medfile.models.Patient;
import fr.epf.medfile.models.Patient.Sex;
import fr.epf.medfile.utils.Utils;

public class PatientServiceImpl implements PatientService {

    private DBOpenHelper openHelper;

    public PatientServiceImpl(Context context) {
	openHelper = new DBOpenHelper(context);
    }

    @Override
    public long addPatient(int ID, String firstName, String lastName, String socialSecurity, String birthDate, String sex, String address, String city, String zipCode, String email, String phoneNumber, String entryDate, String causes, String service, String healthInfos, String birthAddress) {
	ContentValues values = new ContentValues();
	values.put(DBOpenHelper.ID_PATIENT, ID);
	values.put(DBOpenHelper.FIRST_NAME, firstName);
	values.put(DBOpenHelper.LAST_NAME, lastName);
	values.put(DBOpenHelper.BIRTH_DATE, birthDate);
	values.put(DBOpenHelper.SEX, sex);
	values.put(DBOpenHelper.SOCIAL_SECURITY, socialSecurity);
	values.put(DBOpenHelper.ADDRESS, address);
	values.put(DBOpenHelper.CITY, city);
	values.put(DBOpenHelper.ZIP_CODE, zipCode);
	values.put(DBOpenHelper.EMAIL, email);
	values.put(DBOpenHelper.PHONE_NUMBER, phoneNumber);
	values.put(DBOpenHelper.BIRTH_ADDRESS, birthAddress);
	values.put(DBOpenHelper.ENTRY_DATE, entryDate);
	values.put(DBOpenHelper.CAUSES, causes);
	values.put(DBOpenHelper.SERVICE, service);
	values.put(DBOpenHelper.HEALTH_INFOS, healthInfos);

	SQLiteDatabase bdd = openHelper.getWritableDatabase();
	long id = bdd.insert(DBOpenHelper.TABLE_PATIENT, null, values);
	Log.i("PatientService", "Patient inserted at id=" + id + " (original id was " + ID + ")");
	bdd.close();
	return id;
    }

    @Override
    public Patient getPatient(int id) {
	Patient patient;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_PATIENT, null, DBOpenHelper.ID_PATIENT + " =?", new String[] { id + "" }, null, null, null);
	cursor.moveToFirst();
	patient = cursor2Patient(cursor);
	cursor.close();
	bdd.close();
	return patient;
    }

    @Override
    public List<Patient> getPatients() {
	List<Patient> patients = new ArrayList<Patient>();
	Patient patient;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_PATIENT, null, null, null, null, null, DBOpenHelper.FIRST_NAME + "," + DBOpenHelper.LAST_NAME);
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
	    patient = cursor2Patient(cursor);
	    patients.add(patient);
	    cursor.moveToNext();
	}
	cursor.close();
	bdd.close();
	return patients;
    }

    @Override
    public List<Patient> getPatients(String service) {
	List<Patient> patients = new ArrayList<Patient>();
	Patient patient;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_PATIENT, null, DBOpenHelper.SERVICE + " =?", new String[] { service }, null, null, DBOpenHelper.FIRST_NAME + "," + DBOpenHelper.LAST_NAME);
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
	    patient = cursor2Patient(cursor);
	    patients.add(patient);
	    cursor.moveToNext();
	}
	cursor.close();
	bdd.close();
	return patients;
    }

    @Override
    public boolean removePatient(int id) {
	SQLiteDatabase bdd = openHelper.getWritableDatabase();
	bdd.delete(DBOpenHelper.TABLE_PATIENT, DBOpenHelper.ID_PATIENT + " =?", new String[] { id + "" });
	bdd.close();
	return true;
    }

    @Override
    public boolean clearPatients() {
	SQLiteDatabase bdd = openHelper.getWritableDatabase();
	bdd.delete(DBOpenHelper.TABLE_PATIENT, null, null);
	bdd.close();
	return true;
    }

    private Patient cursor2Patient(Cursor cursor) {
	Patient patient = new Patient();
	patient.setID(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.ID_PATIENT)));
	patient.setFirstName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FIRST_NAME)));
	patient.setLastName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.LAST_NAME)));
	patient.setSocialSecurity(cursor.getString(cursor.getColumnIndex(DBOpenHelper.SOCIAL_SECURITY)));
	patient.setSex(cursor.getString(cursor.getColumnIndex(DBOpenHelper.SEX)).contains("M") ? Sex.MALE : Sex.FEMALE);
	patient.setAddress(cursor.getString(cursor.getColumnIndex(DBOpenHelper.ADDRESS)));
	patient.setCity(cursor.getString(cursor.getColumnIndex(DBOpenHelper.CITY)));
	patient.setZipCode(cursor.getString(cursor.getColumnIndex(DBOpenHelper.ZIP_CODE)));
	patient.setEmail(cursor.getString(cursor.getColumnIndex(DBOpenHelper.EMAIL)));
	patient.setPhoneNumber(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PHONE_NUMBER)));
	patient.setBirthAddress(cursor.getString(cursor.getColumnIndex(DBOpenHelper.BIRTH_ADDRESS)));
	patient.setCauses(cursor.getString(cursor.getColumnIndex(DBOpenHelper.CAUSES)));
	patient.setService(cursor.getString(cursor.getColumnIndex(DBOpenHelper.SERVICE)));
	patient.setHealthInfos(cursor.getString(cursor.getColumnIndex(DBOpenHelper.HEALTH_INFOS)));

	try {
	    patient.setBirthDate(Utils.parse(cursor.getString(cursor.getColumnIndex(DBOpenHelper.BIRTH_DATE))));
	    patient.setEntryDate(Utils.parse(cursor.getString(cursor.getColumnIndex(DBOpenHelper.ENTRY_DATE))));
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return patient;
    }

    @Override
    public boolean isEmpty() {
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cur = bdd.rawQuery("SELECT COUNT(*) FROM " + DBOpenHelper.TABLE_PATIENT, null);
	if (cur != null) {
	    cur.moveToFirst(); // Always one row returned.
	    if (cur.getInt(0) == 0) { // Zero count means empty table.
		bdd.close();
		return true;
	    }
	}
	bdd.close();
	return false;
    }
}
