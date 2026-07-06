package com.example.aufgabenplannerelias;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskItemDao {

    @Query("SELECT * FROM task_item_table WHERE id = :id")
    TaskItem getById(int id);

    @Query("SELECT * FROM task_item_table ORDER BY id DESC")
    List<TaskItem> getAll();

    @Insert
    void insert(TaskItem item);

    @Update
    void update(TaskItem item);

    @Query("DELETE FROM task_item_table")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM task_item_table")
    int getCount();

    @Delete
    void delete(TaskItem item);

    @Query("SELECT * FROM task_item_table ORDER BY title ASC")
    List<TaskItem> getAllItems();
}
