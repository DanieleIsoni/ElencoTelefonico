package it.daniso.elencotelefonico;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static it.daniso.elencotelefonico.Incarico.incarichi;
import static it.daniso.elencotelefonico.Incarico.incarichiMap;
import static it.daniso.elencotelefonico.Person.persone;

public class MainActivity extends AppCompatActivity {

    public static final String INCARICO_ID = "INCARICO_ID";
    public static final String DATA = "DATA";
    public static final int ADD_ITEM_REQUEST = 1;
    IncaricoListAdapter adapter;
    ListView lv;
    DatabaseHandler db = new DatabaseHandler(this);
    List<String> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                intent.putExtra(INCARICO_ID, incarico.getNomeIncarico());

                startActivity(intent);
            }
        });


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
                adapter.myNotifyDataSetChanged();
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_ITEM_REQUEST){
            if(resultCode == RESULT_OK){
                datas = data.getStringArrayListExtra(DATA);
                if(!persone.containsKey(datas.get(1).toLowerCase())) {
                    db.insertPerson(datas.get(1), datas.get(2), datas.get(3), datas.get(4), datas.get(5));
                    db.getAllPersone();
                }
                if(!incarichiMap.containsKey(datas.get(0).toLowerCase())) {
                    db.insertIncarico(datas.get(0), datas.get(1));
                    db.getAllIncarichi();
                } else {
                    Toast.makeText(this,"Incarico già esistente", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this,"Non tutti i campi sono stati compilati", Toast.LENGTH_LONG).show();
            }
        }
    }
}
