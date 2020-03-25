package com.example.tasbihpintar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Panduan extends AppCompatActivity {

    ArrayList<DataController> list = new ArrayList<>(); // Alokasikan memori untuk simpan list
    RecyclerView recycle;
    DataAdapter dataAdapter;
    String JSON_URL = "https://tasbih-pintar-backend-server.herokuapp.com/api";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_card));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycle = findViewById(R.id.recview);
        recycle.setHasFixedSize(true);
        getDetailJson();

    }

    private void getDetailJson(){
        StringRequest stringRequest = new StringRequest(JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DataController data = new DataController();
                        data.setGambar(jsonObject.getString("gambar"));
                        data.setNamaDzikir(jsonObject.getString("nama_dzikir"));
                        data.setKeterangan(jsonObject.getString("keterangan"));
                        data.setNumberItem(i+1);
                        list.add(data);
                        viewResult(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void viewResult(ArrayList<DataController> list){
        recycle.setLayoutManager(new GridLayoutManager(this,1));
        DataAdapter dataAdapter = new DataAdapter(list);
        recycle.setAdapter(dataAdapter);
    }
}
