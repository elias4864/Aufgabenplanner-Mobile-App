package com.example.aufgabenplannerelias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskItem> {

    public TaskAdapter(Context context, List<TaskItem> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }

        TaskItem item = getItem(position);
        TextView tvid= convertView.findViewById(R.id.tvid);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);
        TextView tvDifficulty = convertView.findViewById(R.id.tvDifficulty);
        TextView tvDueDate = convertView.findViewById(R.id.tvDueDate);
        TextView tvDone = convertView.findViewById(R.id.tvDone);
        TextView tvPriority = convertView.findViewById(R.id.tvPriority);


        if (item != null) {
            tvTitle.setText(item.title);
            tvid.setText(String.valueOf(item.id));
            tvDescription.setText(item.description);
            tvDifficulty.setText("(" + item.Difficulty + ")");
            tvDueDate.setText("Datum: " + item.duedate);
            tvPriority.setText("Prio: " + item.priority);
            tvDone.setText("Erledigt: " + (item.done ? "Ja" : "Nein"));
            tvid.setText("ID: " + item.id);
            tvDifficulty.setText("Schwierigkeit: " + item.Difficulty);
        }

        return convertView;
    }
}
