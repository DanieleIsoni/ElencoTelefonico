package it.daniso.elencotelefonico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.daniso.elencotelefonico.Person.personeList;
import static it.daniso.elencotelefonico.PersonExpandableListAdapter.select;

public class AddActivity extends AppCompatActivity {

    ExpandableListView expView;
    PersonExpandableListAdapter expAdapter;
    HashMap<String, List<Person>> listDataChild;
    List<Person> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expView = (ExpandableListView) findViewById(R.id.exp_view);
        list = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataChild.put(select, personeList);
        expAdapter = new PersonExpandableListAdapter(this, listDataChild);
        expView.setAdapter(expAdapter);

        expView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
