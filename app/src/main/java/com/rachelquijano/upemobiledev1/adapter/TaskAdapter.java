package com.rachelquijano.upemobiledev1.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    List<String> tasks;

    public interface OnLongClickListener{
        void onTaskLongClicked(int position);
    }

    OnLongClickListener longClickListener;

    //Constructor
    public TaskAdapter(List<String> tasks, OnLongClickListener longClickListener){
        this.tasks = tasks;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Use the layout inflater to inflate a view (render the view by creating it in memory)
        View taskView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        //Wrap this view inside the holder and return it
        return new ViewHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    String task = tasks.get(position);
    holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTask;


        //Container that provides easy access to views that represent each row inside the recycler view
        public ViewHolder(@NonNull View taskView){
            super(taskView);
            tvTask = taskView.findViewById(android.R.id.text1);
        }

        //Update the view inside the viewholder with this data
        public void bind(String task){
            tvTask.setText(task);

            tvTask.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    //Notify the listener which position was long pressed
                    longClickListener.onTaskLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
