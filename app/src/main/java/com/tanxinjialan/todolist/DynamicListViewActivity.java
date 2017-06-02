package com.tanxinjialan.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class DynamicListViewActivity extends AppCompatActivity {

    private EditText newItem;
    private Button addButton;
    private ListView dynamicListView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_list_view);

        newItem = (EditText) findViewById(R.id.editListText);
        addButton = (Button) findViewById(R.id.addItemButton);
        dynamicListView = (ListView) findViewById(R.id.itemListView);

        // Initialize the list and add item
        list = new ArrayList<String>();
        // Initialize the arrayAdapter
        adapter = new ArrayAdapter<String>(DynamicListViewActivity.this, android.R.layout.simple_list_item_1, list);
        // Setting the adapter to the listView
        dynamicListView.setAdapter(adapter);

        //Add new item to the listView on click button (add)
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = newItem.getText().toString();
                if (todoItem.length() > 0) {
                    // Add editText Value to the list
                    list.add(newItem.getText().toString());
                    // Refresh the listView
                    adapter.notifyDataSetChanged();
                    //Clear the editText for the new item
                    newItem.setText("");
                }
            }
        });

        // Delete item on the long click on the item
        dynamicListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // If user wants to delete item
                // Show alert message to reassure to delete item
                AlertDialog alert = new AlertDialog.Builder(DynamicListViewActivity.this).create();
                alert.setTitle("Delete");
                alert.setMessage("Delete item?");
                // "OK" Button on alert message
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the item from list
                        list.remove(position);
                        // Apply changes on the adapter to refresh the listView
                        adapter.notifyDataSetChanged();
                        Log.i("Status", "OK button clicked!");
                    }
                });
                // "Cancel" Button on alert message
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Status", "Cancel button clicked!");
                        dialog.dismiss();
                    }
                });

                alert.show();
                return true;
            }
        });

    }
}
