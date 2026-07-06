package com.example.aufgabenplannerelias;

import androidx.room.Room;
import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskItemDao taskItemDao();


    private static volatile AppDatabase INSTANCE;




    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class, "add_task_db"
                            )
                            .fallbackToDestructiveMigration() // <-- Genau das hier einfügen!
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
