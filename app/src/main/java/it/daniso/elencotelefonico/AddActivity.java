package it.daniso.elencotelefonico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import static it.daniso.elencotelefonico.DatabaseHandler.DEFAULT;
import static it.daniso.elencotelefonico.Person.personeList;

public class AddActivity extends AppCompatActivity {

    Spinner spinner;
    PersonSpinnerAdapter adapter;
    EditText codFiscale;
    EditText name;
    EditText surname;
    EditText telNum;
    EditText email;
    EditText inc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new PersonSpinnerAdapter(this, android.R.layout.simple_spinner_item, personeList);
        spinner.setAdapter(adapter);

        inc = (EditText) findViewById(R.id.inputIncarico);

        codFiscale = (EditText) findViewById(R.id.inputCodFiscale);
        name = (EditText) findViewById(R.id.inputNome);
        surname = (EditText) findViewById(R.id.inputCognome);
        telNum = (EditText) findViewById(R.id.inputTelNum);
        email = (EditText) findViewById(R.id.inputEmail);

        telNum.setText("+39 0461 9 ");
        email.setText("@comalp.esercito.difesa.it");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Person person = personeList.get(i);
                if(person.getCodFiscale() != DEFAULT) {
                    codFiscale.setText(person.getCodFiscale());
                    name.setText(person.getName());
                    surname.setText(person.getSurname());
                    telNum.setText(person.getTelNumber());
                    email.setText(person.getEmail());
                } else {
                    codFiscale.setText("");
                    name.setText("");
                    surname.setText("");
                    telNum.setText("+39 0461 9 ");
                    email.setText("@comalp.esercito.difesa.it");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);

        menu.findItem(R.id.action_done).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ArrayList<String> result = new ArrayList<>();
                result.add(inc.getText().length() == 0 ? null : inc.getText().toString().toLowerCase());
                result.add(inc.getText().length() == 0 ? null : inc.getText().toString());
                result.add(codFiscale.getText().length() == 0 ? null : codFiscale.getText().toString());
                result.add(name.getText().length() == 0 ? null : name.getText().toString());
                result.add(surname.getText().length() == 0 ? null : surname.getText().toString());
                result.add(telNum.getText().length() == 0 ? null : telNum.getText().toString());
                result.add(email.getText().length() == 0 ? null : email.getText().toString());

                if(result.size() == 7 && result.get(0) != null && result.get(1) != null && result.get(2) != null &&
                        result.get(3) != null && result.get(4) != null && result.get(5) != null && result.get(6) != null) {
                    Intent returnIntent = new Intent();
                    returnIntent.putStringArrayListExtra(MainActivity.DATA, result);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
                return false;
            }
        });

        return true;
    }
}
