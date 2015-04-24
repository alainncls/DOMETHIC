package fr.epf.medfile.daos.news;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.epf.medfile.daos.DBOpenHelper;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.models.News;
import fr.epf.medfile.models.Patient;
import fr.epf.medfile.utils.Utils;

@SuppressLint("SimpleDateFormat")
public class NewsServiceImpl implements NewsService {

    private DBOpenHelper openHelper;

    public NewsServiceImpl(Context context) {
	openHelper = new DBOpenHelper(context);
    }

    @Override
    public long addNews(int iD, int patientID, int authorID, String date, String title, String content) {
	ContentValues values = new ContentValues();
	values.put(DBOpenHelper.ID_NEWS, iD);
	values.put(DBOpenHelper.ID_PATIENT, patientID);
	values.put(DBOpenHelper.ID_AUTHOR, authorID);
	values.put(DBOpenHelper.NEWS_DATE, date);
	values.put(DBOpenHelper.NEWS_TITLE, title);
	values.put(DBOpenHelper.NEWS_CONTENT, content);

	SQLiteDatabase bdd = openHelper.getWritableDatabase();
	long id = bdd.insert(DBOpenHelper.TABLE_NEWS, null, values);
	bdd.close();
	return id;
    }

    @Override
    public News getNews(int id) {
	News news;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_NEWS, null, DBOpenHelper.ID_NEWS + " =?", new String[] { id + "" }, null, null, DBOpenHelper.NEWS_DATE + " DESC");
	cursor.moveToFirst();
	news = cursor2News(cursor);
	cursor.close();
	bdd.close();
	return news;
    }

    @Override
    public List<News> getNews() {
	List<News> news = new ArrayList<News>();
	News n = new News();
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_NEWS, null, null, null, null, null, DBOpenHelper.NEWS_DATE + " DESC");
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
	    n = cursor2News(cursor);
	    news.add(n);
	    cursor.moveToNext();
	}
	cursor.close();
	bdd.close();
	return news;
    }

    @Override
    public List<News> getNews(String service) {
	List<Patient> patients = ServiceProvider.getPInstance(null).getPatients(service);
	List<News> news = new ArrayList<News>();
	News n = new News();
	Cursor cursor;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	String where = "";
	List<String> whereArgs = new ArrayList<String>();
	for (Patient patient : patients) {
	    where += " OR " + DBOpenHelper.ID_PATIENT + "=?";
	    whereArgs.add(patient.getID() + "");
	}
	where = where.substring(3);
	cursor = bdd.query(DBOpenHelper.TABLE_NEWS, null, where, whereArgs.toArray(new String[] {}), null, null, DBOpenHelper.NEWS_DATE + " DESC");
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
	    n = cursor2News(cursor);
	    news.add(n);
	    cursor.moveToNext();
	}
	cursor.close();
	bdd.close();
	return news;
    }

    @Override
    public List<News> getNewsPatient(int idPatient) {
	List<News> news = new ArrayList<News>();
	News n = new News();
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_NEWS, null, DBOpenHelper.ID_PATIENT + " =?", new String[] { idPatient + "" }, null, null, DBOpenHelper.NEWS_DATE + " DESC");
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
	    n = cursor2News(cursor);
	    news.add(n);
	    cursor.moveToNext();
	}
	cursor.close();
	bdd.close();
	return news;
    }

    @Override
    public boolean removeNews(int id) {
	SQLiteDatabase bdd = openHelper.getWritableDatabase();
	bdd.delete(DBOpenHelper.TABLE_NEWS, DBOpenHelper.ID_NEWS + " =?", new String[] { id + "" });
	bdd.close();
	return true;
    }

    @Override
    public boolean clearNews() {
	SQLiteDatabase bdd = openHelper.getWritableDatabase();
	bdd.delete(DBOpenHelper.TABLE_NEWS, null, null);
	bdd.close();
	return true;
    }

    private News cursor2News(Cursor cursor) {
	News news = new News();
	news.setID(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.ID_NEWS)));
	news.setPatientID(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.ID_PATIENT)));
	news.setAuthorID(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.ID_AUTHOR)));
	news.setTitle(cursor.getString(cursor.getColumnIndex(DBOpenHelper.NEWS_TITLE)));
	news.setContent(cursor.getString(cursor.getColumnIndex(DBOpenHelper.NEWS_CONTENT)));

	try {
	    news.setDate(Utils.parse(cursor.getString(cursor.getColumnIndex(DBOpenHelper.NEWS_DATE))));
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return news;
    }

    @Override
    public boolean isEmpty() {
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cur = bdd.rawQuery("SELECT COUNT(*) FROM " + DBOpenHelper.TABLE_NEWS, null);
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
