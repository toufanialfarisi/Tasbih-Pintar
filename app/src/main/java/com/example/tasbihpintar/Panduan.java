package com.example.tasbihpintar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Panduan extends AppCompatActivity {

    ArrayList<DataController> list = new ArrayList<>(); // Alokasikan memori untuk simpan list
    RecyclerView recycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_card));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycle = findViewById(R.id.recview);
        recycle.setHasFixedSize(true);
        list.addAll(DataPanduan.getData());
        Log.d("DATA", String.valueOf(list));
        viewResult();

    }

    private void viewResult(){
        recycle.setLayoutManager(new GridLayoutManager(this,1));
        DataAdapter dataAdapter = new DataAdapter(list);
        recycle.setAdapter(dataAdapter);


    }
}
