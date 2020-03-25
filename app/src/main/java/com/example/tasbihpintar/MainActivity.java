package com.example.tasbihpintar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;
    private int counter = 0;
    private int initialCounter = 0;
    private boolean getar = false;
    FloatingActionButton floatingActionButton ;
    TextView number, namaDzikir;
    RelativeLayout lay3, lay2;

    private void vibation(Vibrator vibrator ,int duration){
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
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
        return true;
    }


    private void vibration(Vibrator vibrator, int volume){
        vibrator.vibrate(VibrationEffect.createOneShot(volume, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_card));
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        number = findViewById(R.id.number);
        namaDzikir = findViewById(R.id.pujian);

        /*
        Atur nilai awalan nama zikir dan nilai counternya

         */
        final String firstVal = String.valueOf(initialCounter);
        number.setText(firstVal);
        namaDzikir.setText("Tasbih (سبحانالله)");

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

                }
                else if (counter == 66){
                    vibration(vibrator, 500);
                    number.setText(String.valueOf(66));
                } else if (counter == 67){
                    namaDzikir.setText("Takibr (الله أكبر)");

                }

                else if (counter == 99){
                    number.setText(String.valueOf(99));
                    vibration(vibrator, 500);
                } else if (counter == 100){
                    counter = 0;
                    namaDzikir.setText("Tasbih (سبحانالله)");
                    number.setText(String.valueOf(counter));
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
















//        mBottomNav = findViewById(R.id.navigation);
//        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.navigation_favorites:
//                        Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });




    }

}
