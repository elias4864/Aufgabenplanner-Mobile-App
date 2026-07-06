package com.example.aufgabenplannerelias;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_ITEM = 1;

    private List<TaskItem> eintraege;
    private TaskAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Datenbank initialisieren
        db = AppDatabase.getDatabase(this);

        // UI-Elemente verknüpfen
        ListView listViewItems = findViewById(R.id.listViewItems);
        Button btnHinzufuegen = findViewById(R.id.btnHinzufuegen);
        Button btnlöschen = findViewById(R.id.btnlöschen);

        // Adapter Setup
        eintraege = new ArrayList<>();
        adapter = new TaskAdapter(this, eintraege);
        listViewItems.setAdapter(adapter);

        // Klick-Listener für den Hinzufügen-Button
        btnHinzufuegen.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTaskItem.class);
            startActivityForResult(intent, REQUEST_ADD_ITEM);
        });

        // Klick-Listener für den Löschen-Button
        btnlöschen.setOnClickListener(v -> {
            new Thread(() -> {
                db.taskItemDao().deleteAll();
                runOnUiThread(this::loadItemsFromDatabase);
            }).start();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Daten bei jedem App-Start/Rückkehr frisch aus der DB laden
        loadItemsFromDatabase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_ITEM && resultCode == RESULT_OK && data != null) {
            String produktname = data.getStringExtra(AddTaskItem.EXTRA_PRODUKTNAME);
            String description = data.getStringExtra(AddTaskItem.EXTRA_BESCHREIBUNG);
            String title = data.getStringExtra(AddTaskItem.EXTRA_TITLE);
            String duedate = data.getStringExtra(AddTaskItem.EXTRA_DUE);
            String priority = data.getStringExtra(AddTaskItem.EXTRA_PRIORITY);
            String difficulty = data.getStringExtra(AddTaskItem.EXTRA_DIFFICULTY);
            String done = data.getStringExtra(AddTaskItem.EXTRA_DONE);






            // Neues TaskItem-Objekt erstellen
            TaskItem item = new TaskItem();
            item.id = eintraege.size() + 1;
            item.title = title;
            item.description = description;
            item.priority = priority;
            item.duedate = duedate;
            item.done = false;
            item.Difficulty = Difficulty.valueOf(difficulty);



            // In der Datenbank im Hintergrund-Thread speichern
            new Thread(() -> {
                db.taskItemDao().insert(item);
                // Nach dem Speichern die Liste direkt neu aus der DB laden
                runOnUiThread(this::loadItemsFromDatabase);
            }).start();
        }
    }

    // Daten aus der Datenbank laden und im UI anzeigen
    private void loadItemsFromDatabase() {
        new Thread(() -> {
            // 1. Alle Tasks aus der DB holen
            List<TaskItem> items = db.taskItemDao().getAllItems();

            // 3. UI auf dem Hauptthread aktualisieren
            runOnUiThread(() -> {
                eintraege.clear();
                eintraege.addAll(items);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }
}
