package fr.epf.medfile.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public static final int BDD_VERSION = 32;
    public static final String BDD_NAME = "medFile";
    
    /*
     * *********************
     * *** TABLE PATIENTS **
     * *********************
     */

    public static final String TABLE_PATIENT = "patient";
    
    // Colums
    public static final String ID_PATIENT = "idPatient";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String BIRTH_DATE = "birthDate";
    public static final String SEX = "sex";
    public static final String SOCIAL_SECURITY = "socialSecurity";
    public static final String ADDRESS = "address";
    public static final String CITY = "city";
    public static final String ZIP_CODE = "zipCode";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String BIRTH_ADDRESS = "birthAddress";
    public static final String ENTRY_DATE = "entryDate";
    public static final String CAUSES = "causes";
    public static final String SERVICE = "service";
    public static final String HEALTH_INFOS = "healthInfos";

    // Create
    private static final String CREATE_TABLE_PATIENT = "CREATE TABLE " + TABLE_PATIENT + " (" + ID_PATIENT + " INTEGER PRIMARY KEY, " + FIRST_NAME + " TEXT, " + LAST_NAME + " TEXT, " + BIRTH_DATE + " TEXT, " + SEX + " TEXT, " + SOCIAL_SECURITY + " TEXT, " + ADDRESS + " TEXT, " + CITY + " TEXT, " + ZIP_CODE + " TEXT, " + EMAIL + " TEXT, " + PHONE_NUMBER + " TEXT, " + BIRTH_ADDRESS + " TEXT, "
	    + ENTRY_DATE + " TEXT, " + CAUSES + " TEXT, " + SERVICE + " TEXT, " + HEALTH_INFOS + " TEXT " + ");";
    
    /*
     * *********************
     * **** TABLE USERS ****
     * *********************
     */

    public static final String TABLE_USER = "user";
    
    // Colums
    public static final String ID_USER = "idUser";
    public static final String CPS = "cps";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    
    // Create
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" + ID_USER + " INTEGER PRIMARY KEY, " + FIRST_NAME + " TEXT NOT NULL, " + LAST_NAME + " TEXT NOT NULL, " + SERVICE + " TEXT, " + BIRTH_DATE + " TEXT, " + SEX + " TEXT, " + CPS + " TEXT, " + ADDRESS + " TEXT, " + CITY + " TEXT, " + ZIP_CODE + " TEXT, " + EMAIL + " TEXT, " + PHONE_NUMBER + " TEXT, "
	    + BIRTH_ADDRESS + " TEXT, " + HEALTH_INFOS + " TEXT, " + LOGIN + " TEXT, " + PASSWORD + " TEXT " + ");";

    
    /*
     * *********************
     * ** TABLE RESSOURCES *
     * *********************
     */

    public static final String TABLE_RESSOURCE = "ressource";
    
    // Colums
    public static final String ID_RESSOURCE = "idRessource";
    public static final String RESSOURCE_TITLE = "title";
    public static final String RESSOURCE_TYPE = "type"; // Texte, Image, etc.
    public static final String RESSOURCE_TEXT = "text";
    public static final String RESSOURCE_DATE = "date";
    public static final String RESSOURCE_CATEGORY = "category";
    public static final String RESSOURCE_URL = "url";
    public static final String ID_AUTHOR = "idAuthor";
    public static final String RESSOURCE_IMG = "image";
    
    // Create
    private static final String CREATE_TABLE_RESSOURCE = "CREATE TABLE " + TABLE_RESSOURCE + " (" + ID_RESSOURCE + " INTEGER PRIMARY KEY, " + ID_PATIENT + " INTEGER, " + ID_AUTHOR + " INTEGER, " + RESSOURCE_TYPE + " TEXT, " + RESSOURCE_CATEGORY + " TEXT, " + RESSOURCE_DATE + " TEXT, " + RESSOURCE_TITLE + " TEXT, " + RESSOURCE_IMG + " BLOB, " + RESSOURCE_URL + " TEXT, " + RESSOURCE_TEXT + " TEXT "
	    + ");";
    
    /*
     * *********************
     * **** TABLE NEWS *****
     * *********************
     */

    public static final String TABLE_NEWS = "news";

    // Colums
    public static final String ID_NEWS = "idNews";
    public static final String NEWS_TITLE = "newsTitle";
    public static final String NEWS_CONTENT = "newsContent";
    public static final String NEWS_DATE = "date";
    
    // Create
    private static final String CREATE_TABLE_NEWS = "CREATE TABLE " + TABLE_NEWS + " (" + ID_NEWS + " INTEGER PRIMARY KEY, " + ID_PATIENT + " INTEGER, " + ID_AUTHOR + " INTEGER, " + NEWS_TITLE + " TEXT , " + NEWS_CONTENT + " TEXT, " + NEWS_DATE + " TEXT " + ");";
    
    /*
     * *********************
     * *** CONSTRUCTEURS ***
     * *********************
     */

    public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
	super(context, name, factory, version);
    }

    public DBOpenHelper(Context context) {
	super(context, BDD_NAME, null, BDD_VERSION);
    }
    
    /*
     * ********************
     * ***** METHODES *****
     * ********************
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
	db.execSQL(CREATE_TABLE_PATIENT);
	db.execSQL(CREATE_TABLE_USER);
	db.execSQL(CREATE_TABLE_NEWS);
	db.execSQL(CREATE_TABLE_RESSOURCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	db.execSQL("DROP TABLE " + TABLE_PATIENT + ";");
	db.execSQL("DROP TABLE " + TABLE_USER + ";");
	db.execSQL("DROP TABLE " + TABLE_NEWS + ";");
	db.execSQL("DROP TABLE " + TABLE_RESSOURCE + ";");
	onCreate(db);
    }
}