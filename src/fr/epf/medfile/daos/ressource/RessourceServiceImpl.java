package fr.epf.medfile.daos.ressource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.util.ByteArrayBuffer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import fr.epf.medfile.daos.DBOpenHelper;
import fr.epf.medfile.models.Ressource;
import fr.epf.medfile.utils.Utils;

public class RessourceServiceImpl implements RessourceService {

    private DBOpenHelper openHelper;

    private Map<AsyncTask<String, Void, Byte[]>, Integer> mapTask = new HashMap<AsyncTask<String, Void, Byte[]>, Integer>();

    public RessourceServiceImpl(Context context) {
	openHelper = new DBOpenHelper(context);
    }

    @Override
    public long addRessource(int ID, int idPatient, int idAuthor, String title, String type, String category, String text, String img, String date) {
	ContentValues values = new ContentValues();
	values.put(DBOpenHelper.ID_RESSOURCE, ID);
	values.put(DBOpenHelper.ID_PATIENT, idPatient);
	values.put(DBOpenHelper.ID_AUTHOR, idAuthor);
	values.put(DBOpenHelper.RESSOURCE_TITLE, title);
	values.put(DBOpenHelper.RESSOURCE_CATEGORY, category);
	values.put(DBOpenHelper.RESSOURCE_TYPE, type);
	values.put(DBOpenHelper.RESSOURCE_TEXT, text);
	values.put(DBOpenHelper.RESSOURCE_DATE, date);
	values.put(DBOpenHelper.RESSOURCE_URL, img);

	if (type.equals("image")) {
	    try {
		URL imageUrl = new URL(img);
		URLConnection ucon = imageUrl.openConnection();
		InputStream is = ucon.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayBuffer baf = new ByteArrayBuffer(500);
		int current = 0;
		while ((current = bis.read()) != -1) {
		    baf.append((byte) current);
		}

		byte[] bytes = baf.toByteArray();
		values.put(DBOpenHelper.RESSOURCE_IMG, bytes);

	    } catch (Exception e) {
		Log.e("Error", e.getMessage());
		e.printStackTrace();
	    }
	}
	SQLiteDatabase bdd = openHelper.getWritableDatabase();
	long id = bdd.insert(DBOpenHelper.TABLE_RESSOURCE, null, values);
	bdd.close();
	return id;
    }

    @Override
    public Ressource getRessource(int id) {
	Ressource ressource;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_RESSOURCE, null, DBOpenHelper.ID_RESSOURCE + " =?", new String[] { id + "" }, null, null, null);
	cursor.moveToFirst();
	ressource = cursor2Ressource(cursor);
	cursor.close();
	bdd.close();
	return ressource;
    }

    @Override
    public List<Ressource> getRessources() {
	List<Ressource> ressources = new ArrayList<Ressource>();
	Ressource ressource;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_RESSOURCE, null, null, null, null, null, DBOpenHelper.RESSOURCE_DATE + " DESC");
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
	    ressource = cursor2Ressource(cursor);
	    ressources.add(ressource);
	    cursor.moveToNext();
	}
	cursor.close();
	bdd.close();
	return ressources;
    }

    @Override
    public List<Ressource> getRessources(int idPatient) {
	List<Ressource> ressources = new ArrayList<Ressource>();
	Ressource ressource;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_RESSOURCE, null, DBOpenHelper.ID_PATIENT + " =?", new String[] { idPatient + "" }, null, null, DBOpenHelper.RESSOURCE_DATE + " DESC");
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
	    ressource = cursor2Ressource(cursor);
	    ressources.add(ressource);
	    cursor.moveToNext();
	}
	cursor.close();
	bdd.close();
	return ressources;
    }

    @Override
    public List<String> getCategories(int idPatient) {
	List<String> categories = new ArrayList<String>();
	String category;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	String[] columns = new String[] { DBOpenHelper.RESSOURCE_CATEGORY };
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_RESSOURCE, columns, DBOpenHelper.ID_PATIENT + " =?", new String[] { idPatient + "" }, DBOpenHelper.RESSOURCE_CATEGORY, null, DBOpenHelper.RESSOURCE_DATE + " DESC");
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
	    category = cursor.getString(cursor.getColumnIndex(DBOpenHelper.RESSOURCE_CATEGORY));
	    categories.add(category);
	    cursor.moveToNext();
	}
	cursor.close();
	bdd.close();
	return categories;
    }

    @Override
    public List<Ressource> getRessources(int idPatient, String category) {
	List<Ressource> ressources = new ArrayList<Ressource>();
	Ressource ressource;
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cursor = bdd.query(DBOpenHelper.TABLE_RESSOURCE, null, DBOpenHelper.ID_PATIENT + " =? AND " + DBOpenHelper.RESSOURCE_CATEGORY + " =?", new String[] { idPatient + "", category }, null, null, DBOpenHelper.RESSOURCE_DATE + " DESC");
	cursor.moveToFirst();
	while (!cursor.isAfterLast()) {
	    ressource = cursor2Ressource(cursor);
	    ressources.add(ressource);
	    cursor.moveToNext();
	}
	cursor.close();
	bdd.close();
	return ressources;
    }

    @Override
    public boolean removeRessource(int id) {
	SQLiteDatabase bdd = openHelper.getWritableDatabase();
	bdd.delete(DBOpenHelper.TABLE_RESSOURCE, DBOpenHelper.ID_USER + " =?", new String[] { id + "" });
	bdd.close();
	return true;
    }

    @Override
    public boolean clearRessources() {
	SQLiteDatabase bdd = openHelper.getWritableDatabase();
	bdd.delete(DBOpenHelper.TABLE_RESSOURCE, null, null);
	bdd.close();
	return true;
    }

    private Ressource cursor2Ressource(Cursor cursor) {
	Ressource ressource = new Ressource();
	ressource.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.ID_RESSOURCE)));
	ressource.setIdPatient(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.ID_PATIENT)));
	ressource.setIdAuthor(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.ID_AUTHOR)));
	ressource.setTitle(cursor.getString(cursor.getColumnIndex(DBOpenHelper.RESSOURCE_TITLE)));
	ressource.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.RESSOURCE_TYPE)));
	ressource.setCategory(cursor.getString(cursor.getColumnIndex(DBOpenHelper.RESSOURCE_CATEGORY)));
	ressource.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.RESSOURCE_TEXT)));

	if (ressource.getType().equals("image")) {
	    Bitmap image = null;
	    ByteArrayInputStream imageStream;
	    byte[] blob = cursor.getBlob(cursor.getColumnIndex(DBOpenHelper.RESSOURCE_IMG));
	    if (blob != null && blob.length > 0) {
		imageStream = new ByteArrayInputStream(blob);
		image = BitmapFactory.decodeStream(imageStream);
	    } else {
		AsyncTask<String, Void, Byte[]> load = LoadImgFromURL();
		load.execute(cursor.getString(cursor.getColumnIndex(DBOpenHelper.RESSOURCE_URL)));
		try {
		    Byte[] result;
		    result = load.get();
		    byte[] blo = new byte[result.length];
		    int j = 0;
		    for (Byte b : result)
			blo[j++] = b.byteValue();
		    setBlob(blo, load);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		} catch (ExecutionException e) {
		    e.printStackTrace();
		}
	    }
	    ressource.setImg(image);
	}

	try {
	    ressource.setDate(Utils.parse(cursor.getString(cursor.getColumnIndex(DBOpenHelper.RESSOURCE_DATE))));
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return ressource;
    }

    @Override
    public boolean isEmpty() {
	SQLiteDatabase bdd = openHelper.getReadableDatabase();
	Cursor cur = bdd.rawQuery("SELECT COUNT(*) FROM " + DBOpenHelper.TABLE_RESSOURCE, null);
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

    private void setBlob(byte[] result, AsyncTask<String, Void, Byte[]> task) {
	ContentValues values = new ContentValues();
	values.put(DBOpenHelper.RESSOURCE_IMG, result);
	SQLiteDatabase bdd2 = openHelper.getWritableDatabase();
	bdd2.update(DBOpenHelper.TABLE_RESSOURCE, values, DBOpenHelper.ID_RESSOURCE + "=?", new String[] { mapTask.get(task) + "" });
	bdd2.close();
	mapTask.remove(task);
	Log.i("Ressource bdd", "Task done: " + task.hashCode());
    }

    private AsyncTask<String, Void, Byte[]> LoadImgFromURL() {
	return new AsyncTask<String, Void, Byte[]>() {

	    protected Byte[] doInBackground(String... urls) {
		try {
		    URL imageUrl = new URL(urls[0]);
		    URLConnection ucon = imageUrl.openConnection();
		    InputStream is = ucon.getInputStream();
		    BufferedInputStream bis = new BufferedInputStream(is);
		    ByteArrayBuffer baf = new ByteArrayBuffer(500);
		    int current = 0;
		    while ((current = bis.read()) != -1) {
			baf.append((byte) current);
		    }

		    byte[] bytes = baf.toByteArray();
		    Byte[] byteObjects = new Byte[bytes.length];
		    int i = 0;
		    for (byte b : bytes)
			byteObjects[i++] = b;

		    return byteObjects;
		} catch (Exception e) {
		    Log.e("Error", e.getMessage());
		    e.printStackTrace();
		}
		return null;
	    }

	    protected void onPostExecute(Byte[] result) {
		byte[] blob = new byte[result.length];
		int j = 0;
		for (Byte b : result)
		    blob[j++] = b.byteValue();
		setBlob(blob, this);
	    }
	};
    }
}
