package com.example.nickc.to_dolist;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class To_Do_List extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private ArrayList<String> toDoListArray;
    //private String inputString;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to__do__list);
        ListView toDoListItems = (ListView) findViewById(R.id.toDoListXML);
        toDoListArray = new ArrayList<>();
        toDoListItems.setOnItemLongClickListener(this);
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,   // custom layout for the list
                toDoListArray
        );
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int size = prefs.getInt("arraySize", 0);
        for (int i = 0; i < size; i++){
            String s = prefs.getString(i + "", "");
            toDoListArray.add(s);
        }
        ListView listView = (ListView) findViewById(R.id.toDoListXML);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void add (View view) throws FileNotFoundException {
        EditText input = (EditText) findViewById(R.id.input);
        String text = input.getText().toString();
        if (text.length() > 0){
            if (toDoListArray.size() > 0){
                for (int i = 0; i < toDoListArray.size(); i++){
                    if (text.compareTo(toDoListArray.get(i)) < 0){
                        toDoListArray.add(i,text);
                        break;
                    }
                    if (i == toDoListArray.size()-1){
                        toDoListArray.add(text);
                        break;
                    }
                }
            } else {
                toDoListArray.add(text);
            }

            ListView listView = (ListView) findViewById(R.id.toDoListXML);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            input.setText("");
        } else {
            Toast.makeText(To_Do_List.this, "Please type something", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView) findViewById(R.id.toDoListXML);
        toDoListArray.remove(position);
        adapter.notifyDataSetChanged();
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.i("lifecycle", "onSaveInstanceState was called");
        bundle.putStringArrayList("storedArray", toDoListArray);
        //Toast.makeText(To_Do_List.this, "SaveInstanceState", Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt("arraySize", toDoListArray.size());
        for (int i = 0; i < toDoListArray.size(); i++){
            prefsEditor.putString(i+"", toDoListArray.get(i));
        }
        prefsEditor.apply();
    }
    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        Log.i("lifecycle", "onRestoreInstanceState was called");
        toDoListArray = bundle.getStringArrayList("storedArray");
        adapter.notifyDataSetChanged();
        //Toast.makeText(To_Do_List.this, "RestoreInstanceState", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle", "onResume was called");
        //Toast.makeText(To_Do_List.this, "ResumeState", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("lifecycle", "onDestroy was called");
        //Toast.makeText(To_Do_List.this, "DestoryState", Toast.LENGTH_SHORT).show();
    }
}