package it.daniso.elencotelefonico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String incaricoId = getIntent().getStringExtra(MainActivity.INCARICO_ID);
        Incarico incarico = DataProvider.incaricoMap.get(incaricoId);

        TextView tIncarico = (TextView) findViewById(R.id.incaricoTV);
        tIncarico.setText(incarico.getNomeIncarico());
        TextView nome_cognome = (TextView) findViewById(R.id.nome_cognomeTV);
        nome_cognome.setText(incarico.getNomePersona());
        TextView telNumber = (TextView) findViewById(R.id.telNumberTV);
        telNumber.setText(incarico.getTelNumber());
        TextView email = (TextView) findViewById(R.id.emailTV);
        email.setText(incarico.getEmail());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
