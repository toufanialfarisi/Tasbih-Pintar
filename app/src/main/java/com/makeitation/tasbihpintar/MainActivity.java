package com.makeitation.tasbihpintar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pusher.pushnotifications.PushNotifications;


public class MainActivity extends AppCompatActivity {


    private int counter = 0;
    private int initialCounter = 0;
    private boolean getar = false;
    private boolean refrences = false;

    private FloatingActionButton floatingActionButton ;
    private TextView number, namaDzikir;
    private RelativeLayout lay3, lay2;
    private Vibrator vibrator;
    private Typeface typeface;
    private Switch switchGetar;

    // favorite refrence : https://github.com/wasabeef/awesome-android-ui
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_card));
        changeFontActionBar();

        /*
            Cloud Messaging
            dokumentasi : https://pusher.com/tutorials/push-notifications-android
        */
        PushNotifications.start(getApplicationContext(), "5e5cb362-53dd-4170-9b88-c4f1637f70d1");
        PushNotifications.addDeviceInterest("hello");

        /*
            TOOLBAR
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_card));
        */

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean booting = sharedPreferences.contains("booting");
        Log.d("KONDISI", String.valueOf(booting));
        if (!booting){
            // do nothing
        } else {

            Typeface typeface = getResources().getFont(R.font.myfont); // ganti font title bar
            /*
                efek tap target untuk perkenalan fitur di aplikasi saat aplikasi pertama kali diinstal
                https://github.com/KeepSafe/TapTargetView
             */

            final View view = findViewById(R.id.switchAB);
            new TapTargetSequence(this)
                    .targets(TapTarget.forView(findViewById(R.id.fab),"Tombol Reset", "Klik tombol ini untuk mereset counter ke nilai 0")
                                    .tintTarget(false)
                                    .descriptionTypeface(typeface)
                                    .titleTypeface(typeface)
                                    .outerCircleColor(R.color.floatingColor),
                            TapTarget.forView(findViewById(R.id.play), "Tombol Play", "Klik tombol ini untuk memulai perhitungan dzikir")
                                    .tintTarget(false)
                                    .descriptionTypeface(typeface)
                                    .titleTypeface(typeface)
                                    .targetRadius(70)
                                    .outerCircleColor(R.color.colorPrimary),
                            TapTarget.forView(findViewById(R.id.switchGetar), "Mode Getar", "Aktifkan mode getar saat berdzikir")
                                    .tintTarget(false)
                                    .titleTypeface(typeface)
                                    .descriptionTypeface(typeface)
                                    .outerCircleColor(R.color.panduan_bacaan),
                            TapTarget.forView(findViewById(R.id.number), "Bacaalah : ", "1. Tasbih (Subhanallah) 33x \n2. Tahmid (Alhamdulillah) 33x \n3. Takbir (Allahuakbar) 33x \n4. Tahlil 1x")
                                    .tintTarget(true)
                                    .descriptionTypeface(typeface)
                                    .titleTypeface(typeface)
                                    .outerCircleColor(R.color.bacaanSehat)
                            )
                    .listener(new TapTargetSequence.Listener() {
                @Override
                public void onSequenceFinish() {
                    dialogForm(R.layout.hadis, R.id.hadis, "Rasulullah bersabda", true);

                }

                @Override
                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                }

                @Override
                public void onSequenceCanceled(TapTarget lastTarget) {

                }
            }).start();

            /*
                Setelah user membaca panduan yang disajikan dalam bentuk
                TapTargetView, maka simpan status bootingnya ke SharedPreference
                agar TapTargetView hanya muncul sekali saja saat aplikasi
                pertama kali dijalankan setelah instalasi.
             */
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("booting", "pertama_kali");
                editor.commit();
            // SESSION

        }
        // ini untuk full screen /  menghilangkan title bar
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        number = findViewById(R.id.number);
        namaDzikir = findViewById(R.id.pujian);

        switchGetar = findViewById(R.id.switchGetar);
        switchGetar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getar = true;
                    Toast.makeText(MainActivity.this, "Mode Getar ON", Toast.LENGTH_SHORT).show();
                }
                else {
                    getar = false;
                    Toast.makeText(MainActivity.this, "Mode Getar OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
            Atur nilai awalan nama zikir dan nilai counternya
         */
        final String firstVal = String.valueOf(initialCounter);
        number.setText(firstVal);
        namaDzikir.setText("Tasbih (سبحان الله)");

        lay3 = findViewById(R.id.lay3);
        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter += 1;
                if (counter == 33){
                    vibration(vibrator, 500);
                    number.setText(String.valueOf(33));

                } else if (counter == 34){
                    namaDzikir.setText("Tahmid (الحمدلله)");
                    number.setText(String.valueOf(34));

                }
                else if (counter == 66){
                    vibration(vibrator, 500);
                    number.setText(String.valueOf(66));
                } else if (counter == 67){
                    namaDzikir.setText("Takbir (الله أكبر)");
                    number.setText(String.valueOf(67));

                }
                else if (counter == 99){
                    number.setText(String.valueOf(99));
                    vibration(vibrator, 500);

                    Typeface font = getResources().getFont(R.font.myfont); // ganti font title bar
                    TapTargetView.showFor(MainActivity.this,
                            TapTarget.forView(findViewById(R.id.number), "Selanjutnya bacalah Tahlil 1x", " لَا إِلَهَ إِلَّا اَللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ  لَهُ اَلْمُلْكُ وَلَهُ اَلْحَمْدُ  وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ  ")
                                    .outerCircleColor(R.color.colorPrimary)
                                    .tintTarget(true)
                                    .targetRadius(100)
                                    .textTypeface(font)
                                    .descriptionTextSize(45)
                                    .descriptionTextColor(R.color.putih), new TapTargetView.Listener(){
                                @Override
                                public void onTargetClick(TapTargetView view){
                                    super.onTargetClick(view);
                                    counter = 0;
                                    vibration(vibrator, 700);
                                    number.setText(String.valueOf(counter));
                                    namaDzikir.setText("Tasbih (سبحان الله)");
                                }
                            }
                    );

                } else if (counter == 100){
                    namaDzikir.setText("Tahlil");
                    number.setText(String.valueOf(counter));

                } else if (counter == 101){
                    counter = 0;
                    vibration(vibrator, 700);
                    number.setText(String.valueOf(counter));
                    namaDzikir.setText("Tasbih (سبحان الله)");
                }

                else {
                    if (getar == true){
                        vibration(vibrator, 55);
                    number.setText(String.valueOf(counter));

                    } else {
                            number.setText(String.valueOf(counter));
                        }
                }
            }
        });

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                number.setText(String.valueOf(counter));
                namaDzikir.setText("Tasbih (سبحانالله)");
                vibration(vibrator, 250);

            }
        });

        lay2 = findViewById(R.id.lay2);
        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent panduan = new Intent(MainActivity.this, Panduan.class);
                startActivity(panduan);
            }
        });

/*
        Fragment Bottom navigation
        import com.google.android.material.bottomnavigation.BottomNavigationView;

        mBottomNav = findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_favorites:
                        Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

 */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        /*
            MenuItem item = menu.findItem(R.id.switchId);
            item.setActionView(R.layout.layout_switch);
            Switch switchBtn = item.getActionView().findViewById(R.id.switchAB);
            switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        getar = true;
                        Toast.makeText(MainActivity.this, "Mode Getar ON", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        getar = false;
                        Toast.makeText(MainActivity.this, "Mode Getar OFF", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        */

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.about:
                dialogForm(R.layout.about, R.id.about, "About", false);
        }

        return super.onOptionsItemSelected(item);
    }

    private void dialogForm(int layout ,int resourceId, String title, final boolean isToasAvail){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(layout, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.info);
        dialog.setTitle(title);
         TextView text = (TextView) dialogView.findViewById(resourceId);
         dialog.setPositiveButton("tutup", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 if (isToasAvail == true){
                     Toast.makeText(MainActivity.this, "Selamat Berdzikir", Toast.LENGTH_SHORT).show();
                 } else {
                     // do nothing
                 }

                 dialog.cancel();
             }
         });
         dialog.show();

    }

    private void vibration(Vibrator vibrator, int volume){
        vibrator.vibrate(VibrationEffect.createOneShot(volume, VibrationEffect.DEFAULT_AMPLITUDE));
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
