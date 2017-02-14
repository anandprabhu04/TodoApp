package me.anandprabhu.todoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import me.anandprabhu.todoapp.R;
import me.anandprabhu.todoapp.data.model.TodoListItem;

/**
 * Created by Anand Prabhu.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private Context context;
    private ArrayList<TodoListItem> items;

    public RecyclerViewAdapter(Context context, ArrayList<TodoListItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_todo_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.dateText.setText(convertDate(items.get(position).getScheduledDate()));
        if (items.get(position).getStatus().equals("PENDING")) {
            viewHolder.taskText.setCheckMarkDrawable(R.drawable.ic_check_box_pending);
        } else {
            viewHolder.taskText.setCheckMarkDrawable(R.drawable.ic_check_box_completed);
        }
        viewHolder.taskText.setText(items.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String convertDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
            Date d = format.parse(date);
            SimpleDateFormat listViewFormat = new SimpleDateFormat("MMM-dd", Locale.ENGLISH);
            return listViewFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerViewAdapter.clickListener = clickListener;
    }

    // TODO Updated data dynamically
    public void update(ArrayList<TodoListItem> updatedItems) {
        items.clear();
        items.addAll(updatedItems);
        notifyDataSetChanged();
    }

    // Item Click Listener Interface
    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView dateText;
        private CheckedTextView taskText;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            dateText = (TextView) view.findViewById(R.id.date_text);
            taskText = (CheckedTextView) view.findViewById(R.id.task_text);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }
}
