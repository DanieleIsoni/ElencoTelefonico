package it.daniso.elencotelefonico;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import static it.daniso.elencotelefonico.MainActivity.incarichi;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String nomeIncarico = getIntent().getStringExtra(MainActivity.INCARICO_ID);
        Incarico incarico = findIncarico(nomeIncarico, 0, incarichi.size()-1);


        setTitle(incarico.getNomeIncarico());
        TextView nome_cognome = (TextView) findViewById(R.id.nome_cognomeTV);
        nome_cognome.setText(incarico.getNomePersona());
        TextView telNumber = (TextView) findViewById(R.id.telNumberTV);
        telNumber.setText(incarico.person.getTelNumber());
        TextView email = (TextView) findViewById(R.id.emailTV);
        email.setText(incarico.person.getEmail());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Incarico findIncarico(String nomeIncarico, int i, int f) {
        int m = (i+f)/2;
        if(incarichi.get(m).getNomeIncarico() == nomeIncarico)
            return incarichi.get(m);

        return incarichi.get(m).getNomeIncarico().compareTo(nomeIncarico) == -1 ?
                findIncarico(nomeIncarico, i, m) :
                findIncarico(nomeIncarico, m, f);
    }

}
