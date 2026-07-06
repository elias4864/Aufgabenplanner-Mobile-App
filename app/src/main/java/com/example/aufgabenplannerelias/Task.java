package com.example.aufgabenplannerelias;

public class Task {


    private int id;
    private String title;

    private String description;

    private String priority;
    private String duedate;
    private boolean done;
    private Difficulty Difficulty;


    public Task(int id, String title, String description, String priority, String duedate, boolean done, Difficulty Difficulty) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.duedate = duedate;
        this.done = done;
        this.Difficulty = Difficulty;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Difficulty getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        Difficulty = difficulty;
    }
}
