package com.example.aufgabenplannerelias;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aufgabenplannerelias.Difficulty;
import com.example.aufgabenplannerelias.R;
import com.example.aufgabenplannerelias.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends Activity {

    private ListView listView;
    private List<Task> taskliste;
    private ArrayAdapter<Task> adapter;

    // Eingabefelder angepasst an die neuen Task-Attribute
    private EditText etid, etTitle, etDescription, etPriority, etDueDate,etDone, etDifficulty;
    private Button btnTaskHinzufuegen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI-Elemente initialisieren
        listView = findViewById(R.id.listViewItems);
        etTitle = findViewById(R.id.etTitle);             // angepasst (alt: etName)
        etDescription = findViewById(R.id.etDescription); // angepasst (alt: etTaskDescription)
        etPriority = findViewById(R.id.etPriority);       // angepasst (alt: etPreis)
        etDueDate = findViewById(R.id.etDueDate);
        etDone = findViewById(R.id.etDone);
        etDifficulty = findViewById(R.id.etDifficulty);
        etid = findViewById(R.id.etid);
        // angepasst (alt: etMarkt)
        btnTaskHinzufuegen = findViewById(R.id.btnHinzufuegen);

        taskliste = new ArrayList<>();

        // Beispiel-Tasks passend zum neuen Konstruktor hinzufügen
        // Struktur: Task(id, title, description, priority, duedate, done, difficulty)
        // Hinweis: Falls dein Konstruktor keine ID verlangt, kannst du die '1' und '2' weglassen.
        taskliste.add(new Task(1, "Projekt fertigstellen", "WISS Connect App fixen", "Hoch", "10.07.2026", false, Difficulty.MITTEL));
        taskliste.add(new Task(2, "Einkaufen gehen", "Zutaten für das Abendessen holen", "Mittel", "07.07.2026", false, Difficulty.LEICHT));

        // Custom Adapter Implementierung
        adapter = new ArrayAdapter<Task>(this, R.layout.list_item, taskliste) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
                }

                Task aktuellerTask = taskliste.get(position);

                // IDs aus list_item.xml verknüpfen (angepasst an Task-Attribute)
                TextView tvTitle = convertView.findViewById(R.id.tvTitle);
                TextView tvDescription = convertView.findViewById(R.id.tvDescription);
                TextView tvPriority = convertView.findViewById(R.id.tvPriority);
                TextView tvDueDate = convertView.findViewById(R.id.tvDueDate);
                TextView tvDone = convertView.findViewById(R.id.tvDone);
                TextView tvDifficulty = convertView.findViewById(R.id.tvDifficulty);
                TextView tvid = convertView.findViewById(R.id.tvid);
                Button btnDone = convertView.findViewById(R.id.btnDone);

                if (aktuellerTask != null) {
                    // Werte aus dem echten Task-Objekt setzen
                    tvTitle.setText(aktuellerTask.getTitle());
                    tvDescription.setText(aktuellerTask.getDescription());
                    tvPriority.setText("Priorität: " + aktuellerTask.getPriority());
                    tvDueDate.setText("Bis: " + aktuellerTask.getDuedate());
                    tvDone.setText("Erledigt: " + (aktuellerTask.isDone() ? "Ja" : "Nein"));
                    tvDifficulty.setText("Schwierigkeit: " + aktuellerTask.getDifficulty());
                    tvid.setText("ID: " + aktuellerTask.getId());
                    tvDifficulty.setText("Schwierigkeit: " + aktuellerTask.getDifficulty());


                    // "Erledigt"-Button Logik
                    btnDone.setOnClickListener(v -> {
                        taskliste.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(getContext(), "Task erledigt!", Toast.LENGTH_SHORT).show();
                    });
                }

                return convertView;
            }
        };

        listView.setAdapter(adapter);

        // Klick-Event zum Hinzufügen eines neuen Tasks
        btnTaskHinzufuegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String priority = etPriority.getText().toString().trim();
                String duedate = etDueDate.getText().toString().trim();
                String done = etDone.getText().toString().trim();
                String difficulty = etDifficulty.getText().toString().trim();
                String id = etid.getText().toString().trim();



                if (!title.isEmpty()) {
                    // ID wird hier exemplarisch über die Listengröße + 1 generiert
                    int neueId = taskliste.size() + 1;

                    // Neuen Task erstellen (done = false, difficulty = null als Standard)
                    Task neuerTask = new Task(neueId, title, description, priority, duedate, false, Difficulty.LEICHT);

                    taskliste.add(neuerTask);
                    adapter.notifyDataSetChanged();
                    leereFelder();

                    Toast.makeText(TaskListActivity.this, "Task hinzugefügt!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TaskListActivity.this, "Bitte einen Titel eingeben!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hilfsmethode zum Leeren der neuen Felder
    private void leereFelder() {
        etid.setText("");
        etTitle.setText("");
        etDescription.setText("");
        etPriority.setText("");
        etDueDate.setText("");
        etDone.setText("");
        etDifficulty.setText("");

    }
}