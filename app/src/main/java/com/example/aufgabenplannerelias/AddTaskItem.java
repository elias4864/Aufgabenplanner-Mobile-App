package com.example.aufgabenplannerelias;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskItem extends AppCompatActivity {

    public static final String EXTRA_PRODUKTNAME = "Produktname";
    public static final String EXTRA_TITLE = "Titel";
    public static final String EXTRA_DONE = "Erledigt";
    public static final String EXTRA_ID = "Id";
    public static final String EXTRA_BESCHREIBUNG = "Beschreibung";
    public static final String EXTRA_DUE = "Ablaufdatum";
    public static final String EXTRA_PRIORITY = "Priorität";
    public static final String EXTRA_DIFFICULTY = "Schwierigkeit";

    public static final int RESULT_DELETE = 2;

    private EditText etProduktname;
    private EditText etBeschreibung;
    private EditText ettitle;
    private EditText etduedate;
    private EditText etpriority;
    private EditText etdone;

    private Spinner spDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        // Views initialisieren
        etProduktname = findViewById(R.id.etProduktname);
        ettitle = findViewById(R.id.ettitle);
        etBeschreibung = findViewById(R.id.etBeschreibung);
        etduedate = findViewById(R.id.etduedate);
        etpriority = findViewById(R.id.etpriority);
        etdone = findViewById(R.id.etdone);

        spDifficulty = findViewById(R.id.spDifficulty);
        spDifficulty.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                Difficulty.values()
        ));

        Button btnSpeichern = findViewById(R.id.btnSpeichern);
        Button btnAbbrechen = findViewById(R.id.btnAbbrechen);
        Button btnLoeschen = findViewById(R.id.btnLöschen);

        // Klick-Listener
        btnSpeichern.setOnClickListener(v -> speichern());
        btnAbbrechen.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        btnLoeschen.setOnClickListener(v -> löschen());
    }

    private void speichern() {
        String produktname = etProduktname.getText().toString().trim();
        String beschreibung = etBeschreibung.getText().toString().trim();
        String title = ettitle.getText().toString().trim();
        String duedate = etduedate.getText().toString().trim();
        String priority = etpriority.getText().toString().trim();
        String doneStr = etdone.getText().toString().trim();
        String difficulty = spDifficulty.getSelectedItem().toString();

        // Validierung
        if (title.isEmpty() || beschreibung.isEmpty() || duedate.isEmpty() || priority.isEmpty() || difficulty.isEmpty()) {
            Toast.makeText(this, "Bitte alle Felder ausfüllen.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Daten an die MainActivity zurückgeben
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_PRODUKTNAME, produktname);
        resultIntent.putExtra(EXTRA_TITLE, title);
        resultIntent.putExtra(EXTRA_BESCHREIBUNG, beschreibung);
        resultIntent.putExtra(EXTRA_DUE, duedate);
        resultIntent.putExtra(EXTRA_PRIORITY, priority);
        resultIntent.putExtra(EXTRA_DONE, doneStr);
        resultIntent.putExtra(EXTRA_DIFFICULTY, difficulty);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void löschen() {
        Intent resultIntent = new Intent();
        setResult(RESULT_DELETE, resultIntent);
        finish();
    }
}
