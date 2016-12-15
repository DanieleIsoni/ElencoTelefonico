package it.daniso.elencotelefonico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import static it.daniso.elencotelefonico.Incarico.incarichiMap;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Incarico incarico = incarichiMap.get(getIntent().getStringExtra(MainActivity.INCARICO_ID));

        setTitle(incarico.getNomeIncarico());
        TextView nome_cognome = (TextView) findViewById(R.id.nome_cognomeTV);
        nome_cognome.setText(incarico.getNomePersona());
        TextView telNumber = (TextView) findViewById(R.id.telNumberTV);
        telNumber.setText(incarico.person.getTelNumber());
        TextView email = (TextView) findViewById(R.id.emailTV);
        email.setText(incarico.person.getEmail());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
