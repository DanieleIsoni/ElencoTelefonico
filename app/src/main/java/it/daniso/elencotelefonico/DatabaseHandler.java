package it.daniso.elencotelefonico;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static it.daniso.elencotelefonico.Incarico.incarichi;
import static it.daniso.elencotelefonico.Incarico.incarichiMap;
import static it.daniso.elencotelefonico.Person.persone;
import static it.daniso.elencotelefonico.Person.personeList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "ElencoTelefonicoDB.db";

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
    private static final String KEY_INCARICO = "incaricoId";
    private static final String NOME_INCARICO = "nome";
    private static final String PERSON_ID = KEY_PERSON_ID;

    //Costanti
    public static final String DEFAULT = "SELEZIONA";


    public DatabaseHandler(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PERSONE_TABLE = "CREATE TABLE " + TABLE_PERSONE + "("
                + KEY_PERSON_ID + " TEXT PRIMARY KEY,"
                + P_NOME + " TEXT,"
                + P_COGNOME + " TEXT,"
                + P_TEL_NUM + " TEXT,"
                + P_EMAIL + " TEXT" + ")";

        String CREATE_INCARICHI_TABLE = "CREATE TABLE " + TABLE_INCARICHI + "("
                + KEY_INCARICO + " TEXT PRIMARY KEY, "
                + NOME_INCARICO + " TEXT,"
                + PERSON_ID + " TEXT,"
                + " FOREIGN KEY(" + PERSON_ID + ") REFERENCES " + TABLE_PERSONE + "(" + KEY_PERSON_ID + ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";


        db.execSQL(CREATE_PERSONE_TABLE);
        db.execSQL(CREATE_INCARICHI_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCARICHI + TABLE_PERSONE);

        onCreate(db);

    }


    public void insertPerson(String codFiscale, String name, String surname, String telNumber, String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_PERSON_ID, codFiscale);
        cv.put(P_NOME, name);
        cv.put(P_COGNOME, surname);
        cv.put(P_TEL_NUM, telNumber);
        cv.put(P_EMAIL, email);
        try {
            db.insert(TABLE_PERSONE, null, cv);
        } catch(SQLiteConstraintException e){
            System.err.println("Exception");
        }
        db.close();
    }

    public void insertIncarico(String incaricoId, String nomeIncarico, String codFiscale){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_INCARICO, incaricoId);
        cv.put(NOME_INCARICO, nomeIncarico);
        cv.put(PERSON_ID, codFiscale);

        db.insert(TABLE_INCARICHI, null, cv);

        db.close();

    }

    public void removeIncarico(String incaricoId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_INCARICHI, KEY_INCARICO+"=?", new String[]{incaricoId});
    }

    public Person getPerson(String personId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_PERSONE, new String[] { KEY_PERSON_ID,
                        P_NOME, P_COGNOME, P_TEL_NUM, P_EMAIL}, KEY_PERSON_ID + "=?",
                new String[] { personId }, null, null, null, null);

        if(cursor.moveToFirst()){
            Person person = new Person(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4));

            return person;
        }
        return null;
    }



    public void getAllPersone(){
        String selectQuery = "SELECT * FROM " + TABLE_PERSONE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            personeList.clear();
            personeList.add(new Person(DEFAULT, "Seleziona", "Dipendente", "", ""));
            do{
                Person person = new Person();
                person.setCodFiscale(cursor.getString(0));
                person.setName(cursor.getString(1));
                person.setSurname(cursor.getString(2));
                person.setTelNumber(cursor.getString(3));
                person.setEmail(cursor.getString(4));

                persone.put(person.getCodFiscale().toLowerCase(), person);
                personeList.add(person);
            } while (cursor.moveToNext());
        }
    }

    public void getAllIncarichi(){
        String selectQuery = "SELECT * FROM " + TABLE_INCARICHI;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            incarichi.clear();
            do{
                Incarico incarico = new Incarico();
                incarico.setIncaricoId(cursor.getString(0));
                incarico.setNomeIncarico(cursor.getString(1));
                incarico.setPerson(cursor.getString(2));
                incarichi.add(incarico);
                incarichiMap.put(incarico.getIncaricoId(), incarico);
            } while (cursor.moveToNext());
        }
    }

    public void backupDatabase(Activity thisActivity, Context context){
        final String inFileName = context.getDatabasePath(DATABASE_NAME).getPath();
        File dbFile = new File(inFileName);
        try {
            InputStream in = new FileInputStream(dbFile);

            Calendar calendar = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
            String todayDate = df.format(Calendar.getInstance().getTime());
            String newDirName = Environment.getExternalStorageDirectory()+"/ElencoDB_Backup/";
            String outFileName = todayDate+".db";
            File newDir = new File(newDirName);
            newDir.mkdirs();
            File outFile = new File(newDir, outFileName);

            OutputStream out = new FileOutputStream(outFile);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.flush();
            out.close();
            Toast.makeText(thisActivity,"Data backup saved in "+outFile.getPath(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}