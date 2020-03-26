package com.example.tasbihpintar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

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
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    boolean koneksi = false;
    String JSON_URL = "https://tasbih-pintar-backend-server.herokuapp.com/api";
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_list);
        progress = findViewById(R.id.progress);
        recycle = findViewById(R.id.recview);
        recycle.setHasFixedSize(true);
        changeFontActionBar();

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_card));

        getSupportActionBar().setTitle("Panduan Bacaan Lengkap");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected){
            Log.i("INTERNET : ", String.valueOf(isConnected));
            koneksi = true;
            progress.setVisibility(View.VISIBLE);
            getDetailJson();


        } else {
            koneksi = false;
            ContextThemeWrapper ctw = new ContextThemeWrapper(Panduan.this, R.style.MyAlert);
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
            alertDialogBuilder.setTitle("No internet connection");
            alertDialogBuilder.setMessage("Check your internet connection or try again");
            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alertDialogBuilder.show();

        }

        SwipeRefreshLayout.OnRefreshListener refresh = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                }
        };
        swipeRefreshLayout.setOnRefreshListener(refresh);

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

                        progress.setVisibility(View.INVISIBLE);
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

    public void changeFontActionBar(){
        ActionBar ab = getSupportActionBar();
        // Create a TextView programmatically.
        TextView tv = new TextView(getApplicationContext());
        // Create a LayoutParams for TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        // Apply the layout parameters to TextView widget
        tv.setLayoutParams(lp);
        // Set text to display in TextView
        tv.setText(ab.getTitle()); // ActionBar title text
        // Set the text color of TextView to black
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        // Set the monospace font for TextView text
        // This will change ActionBar title text font
        Typeface typeface = getResources().getFont(R.font.myfont);
        tv.setTypeface(typeface);
        // Set the ActionBar display option
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // Finally, set the newly created TextView as ActionBar custom view
        ab.setCustomView(tv);
    }


}
