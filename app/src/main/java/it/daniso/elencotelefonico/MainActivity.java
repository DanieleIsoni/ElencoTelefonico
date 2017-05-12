package it.daniso.elencotelefonico;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static it.daniso.elencotelefonico.Incarico.incarichi;
import static it.daniso.elencotelefonico.Incarico.incarichiMap;
import static it.daniso.elencotelefonico.Person.persone;

public class MainActivity extends AppCompatActivity {

    public static final String INCARICO_ID = "INCARICO_ID";
    public static final String DATA = "DATA";
    public static final int ADD_ITEM_REQUEST = 1;
    private static final int REQUEST_EXTERNAL_STORAGE_WRITE = 1;
    public static final int REQUEST_EXTERNAL_STORAGE_READ = 2;
    private static String[] PERMISSIONS_STORAGE = {
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
    };
    private MenuItem backupMenu;
    private MenuItem restoreMenu;

    IncaricoListAdapter adapter;
    ListView lv;
    DatabaseHandler db = new DatabaseHandler(this);
    List<String> datas;
    Bundle savedInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.savedInstance = savedInstanceState;

        //db.insertPerson("snidnl96m21l378n", "Daniele", "Isoni", "44400", "ciao");
        db.getAllPersone();

        //db.insertIncarico("Lavoro", "snidnl96m21l378n");
        db.getAllIncarichi();


        adapter = new IncaricoListAdapter(this, R.layout.list_item, incarichi);
        lv = (ListView) findViewById(R.id.lista_personale);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Incarico incarico = incarichi.get(i);
                intent.putExtra(INCARICO_ID, incarico.getIncaricoId());

                startActivity(intent);
            }
        });

        registerForContextMenu(lv);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lista_personale) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_context, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.cancella:
                db.removeIncarico(incarichi.get(info.position).getIncaricoId());
                incarichiMap.remove(incarichi.get(info.position).getIncaricoId());
                adapter.getIncarichi().remove(incarichi.get(info.position));
                db.getAllPersone();
                db.getAllIncarichi();
                adapter.myNotifyDataSetChanged();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        super.onContextMenuClosed(menu);
        adapter.myNotifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        menu.findItem(R.id.action_add).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_ITEM_REQUEST);

                return false;
            }
        });

        backupMenu = menu.findItem(R.id.action_backup);
        menu.findItem(R.id.action_backup).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
           @Override
            public boolean onMenuItemClick(MenuItem menuItem){
               verifyStoragePermissionsAndBackup(MainActivity.this);

               return false;
           }
        });

        restoreMenu = menu.findItem(R.id.action_restore);
        menu.findItem(R.id.action_restore).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem){
                verifyStoragePermissionsAndRestore(MainActivity.this);

                return false;
            }
        });
        return true;
    }


    //Verify if the app has the permission for reading/writing on storage, if not it requests the permission to the user, if it has the permissions execute the db_backup
    public void verifyStoragePermissionsAndBackup(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE_WRITE
            );
        } else {
            db.backupDatabase(MainActivity.this,getApplicationContext());
        }
    }

    public void verifyStoragePermissionsAndRestore(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE_READ
            );
        } else {
            db.performFileSearch(MainActivity.this);
        }
    }

    //If the user gives permissions execute backup else disable the backup function
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_WRITE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    db.backupDatabase(MainActivity.this,getApplicationContext());

                } else {
                    backupMenu.setEnabled(false);
                    backupMenu.setVisible(false);
                }
            case REQUEST_EXTERNAL_STORAGE_READ:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    db.performFileSearch(this);

                } else {
                    restoreMenu.setEnabled(false);
                    restoreMenu.setVisible(false);
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_ITEM_REQUEST){
            if(resultCode == RESULT_OK){
                datas = data.getStringArrayListExtra(DATA);
                if(!persone.containsKey(datas.get(2).toLowerCase())) {
                    db.insertPerson(datas.get(2), datas.get(3), datas.get(4), datas.get(5), datas.get(6));
                    db.getAllPersone();
                }
                if(!incarichiMap.containsKey(datas.get(0).toLowerCase())) {
                    db.insertIncarico(datas.get(0), datas.get(1), datas.get(2));
                    db.getAllIncarichi();
                } else {
                    Toast.makeText(this,"Incarico già esistente", Toast.LENGTH_LONG).show();
                }
                adapter.myNotifyDataSetChanged();
            } else {
                Toast.makeText(this,"Non tutti i campi sono stati compilati", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == MainActivity.REQUEST_EXTERNAL_STORAGE_READ && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                try {
                    db.dbToRestore = getContentResolver().openInputStream(uri);
                    db.restoreDatabase(MainActivity.this,getApplicationContext());
                    db.getAllPersone();
                    db.getAllIncarichi();
                    adapter.myNotifyDataSetChanged();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
