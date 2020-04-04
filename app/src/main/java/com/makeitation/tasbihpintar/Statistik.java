package com.makeitation.tasbihpintar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * TODO : Membuat statistik frequency tasbih yang sudah dilakukan dalam bentuk grafik
 * TODO : (1) Membuat DBHandler class untuk menangani penyimpanan data history ke SQLITE
 * TODO : (2) Membuat UI Statistik / history beserta komponennya
 * TODO : (3) Membuat logic penyimpanan histori
 * TODO : (4) Membuat notification builder sebagai reminder fixed
 *
 *
 */

public class Statistik extends AppCompatActivity {
    public static final String FREKUENSI = "frekuensi";
    private static final String TABLE_NAME = "history";
    TextView numberPenyelesaian, numberPersentasi, nilai, keteranganNilai, emot;
    DBHandler db;
    ArrayList<Integer> list;
    ArrayList<Integer> dateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);
        toolbarEnabler(true, true);

        numberPenyelesaian = findViewById(R.id.number_penyelesaian);
        nilai = findViewById(R.id.nilai);
        keteranganNilai = findViewById(R.id.keterangan_nilai);
        emot = findViewById(R.id.emot);
        MaterialCalendarView materialCalendarView = findViewById(R.id.calendar);
        numberPersentasi = findViewById(R.id.number_persentasi);

        db = new DBHandler(this);
        list = db.getRecord();

        int sumResult = 0;

        for (int i : list){
            sumResult += i;
        }
        dateList = db.getDateRecord();
        for (int j : dateList){
            Log.i("DATELIST", String.valueOf(j));
        }
        if (sumResult == 1){

            db.insertDate(String.valueOf(CalendarDay.today()));
            materialCalendarView.setDateSelected(CalendarDay.today(), true);
            Log.i("TODAY", String.valueOf(CalendarDay.today()));
        }

        Log.i("QUERY", String.valueOf(list));
        Log.i("QUERY SUM", String.valueOf(sumResult));

        if (sumResult == 5){
            db.deleteAll();
            Log.i("QUERY DELETE ALL", "true");
        }

        Log.i("QUERY", String.valueOf(list));
        Log.i("QUERY SUM", String.valueOf(sumResult));

        int persentase = (sumResult*100) / 5;
        Log.i("QUERY PERSENTASE", String.valueOf(persentase));
        numberPenyelesaian.setText(String.valueOf(sumResult) + "/5");
        numberPersentasi.setText(String.valueOf(persentase) + "%");

        if (sumResult < 3 ){
            emot.setText(":(");
            nilai.setText("Tingkatkan lagi");
            keteranganNilai.setText("Dzikirlah setiap selesai sholat fardhu");
        } else if (sumResult == 3){
            emot.setText(":|");
            nilai.setText("Sedikit lagi");
            keteranganNilai.setText("Ayo terus tingkatkan dzikirnya");
        }
        else if (sumResult >= 4){
            emot.setText(":)");
            nilai.setText("Luar Biasa");
            keteranganNilai.setText("Tetaplah istiqomah dzikir selesai sholat fardhu ");
        }


    }

    public void toolbarEnabler(@Nullable boolean isEnableToolbar, @Nullable boolean setBlackStatusBarIcon){
        if (isEnableToolbar == true){
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Statistik");
        }
        if (setBlackStatusBarIcon == true){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

}
