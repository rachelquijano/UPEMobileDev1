package com.rachelquijano.upemobiledev1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rachelquijano.upemobiledev1.adapter.TaskAdapter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    List<String> tasks;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvTasks;
    TaskAdapter taskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvTasks = findViewById(R.id.rvitems);


        loadTasks();

        TaskAdapter.OnLongClickListener onLongClickListener = new TaskAdapter.OnLongClickListener(){
            public void onTaskLongClicked(int position){
                //Delete the item from the model
                tasks.remove(position);
                //Notify the adapter
                taskAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveTasks();
            }
        };

        taskAdapter =  new TaskAdapter(tasks, onLongClickListener);
        rvTasks.setAdapter(taskAdapter);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));



        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String task = etItem.getText().toString();
                //Add item to the model
                tasks.add(task);
                //Notify adapter that an item is inserted
                taskAdapter.notifyItemInserted(tasks.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveTasks();
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //This function will load items by reading every line of the data file
    private void loadTasks(){
        try {
            tasks = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading tasks", e);
            tasks = new ArrayList<>();
        }
    }
    //This function saves items by writing them into the data file
    private void saveTasks(){
        try {
            FileUtils.writeLines(getDataFile(), tasks);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }

}