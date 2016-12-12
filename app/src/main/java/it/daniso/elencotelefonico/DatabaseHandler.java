package it.daniso.elencotelefonico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "ElencoTelefonicoDB";

    //Incarichi tables name
    private static final String TABLE_INCARICHI = "incarichi";
    private static final String TABLE_PERSONE = "persone";

    //Persone table columns names
    private static final String KEY_PERSON_ID = "personId";
    private static final String P_NOME = "nome";
    private static final String P_COGNOME = "cognome";
    private static final String P_TEL_NUM = "telNumber";
    private static final String P_EMAIL = "email";

    //Incarichi table columns names
    private static final String KEY_NOME = "nome";
    private static final String PERSON_ID = KEY_PERSON_ID;


    public DatabaseHandler(Context context) {

        super( context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        String CREATE_PERSONE_TABLE = "CREATE TABLE " + TABLE_PERSONE + "("
                + KEY_PERSON_ID + " TEXT PRIMARY KEY,"
                + P_NOME + " TEXT,"
                + P_COGNOME + " TEXT,"
                + P_TEL_NUM + " TEXT,"
                + P_EMAIL + " TEXT," + ")";

        String CREATE_INCARICHI_TABLE = "CREATE TABLE " + TABLE_INCARICHI + "("
                + KEY_NOME + " TEXT PRIMARY KEY,"
                + PERSON_ID + " TEXT"
                + "CONSTRAINT FOREIGN KEY(" + PERSON_ID + ") REFERENCES " + TABLE_PERSONE + "(" + KEY_PERSON_ID + ")" + ")";


        db.execSQL(CREATE_PERSONE_TABLE);
        db.execSQL(CREATE_INCARICHI_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCARICHI);

        onCreate(db);

    }


    public void insertPerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_PERSON_ID, person.getCodFiscale());
        cv.put(P_NOME, person.getName());
        cv.put(P_COGNOME, person.getSurname());
        cv.put(P_TEL_NUM, person.getTelNumber());
        cv.put(P_EMAIL, person.getEmail());

        db.insert(TABLE_PERSONE, null, cv);

        db.close();
    }

    public void insertIncarico(Incarico incarico){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NOME, incarico.getNomeIncarico());
        cv.put(PERSON_ID, incarico.person.getCodFiscale());

        db.insert(TABLE_INCARICHI, null, cv);

        db.close();

    }

    public void getAllPersone(){
        String selectQuery = "SELECT  * FROM " + TABLE_PERSONE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Person person = new Person();
                person.setCodFiscale(cursor.getString(1));
                person.setName(cursor.getString(2));
                person.setSurname(cursor.getString(3));
                person.setTelNumber(cursor.getString(4));
                person.setEmail(cursor.getString(5));

                MainActivity.persone.add(person);
            } while (cursor.moveToNext());
        }
    }

    public void getAllIncarichi(){
        String selectQuery = "SELECT  * FROM " + TABLE_INCARICHI;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Incarico incarico = new Incarico();
                incarico.setNomeIncarico(cursor.getString(1));
                incarico.setPerson(cursor.getString(2));

                MainActivity.incarichi.add(incarico);
            } while (cursor.moveToNext());
        }
    }
}