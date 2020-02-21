package com.cacheclean.cleanapp.cacheappclean.UserI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cacheclean.cleanapp.cacheappclean.JunkScan;
import com.cacheclean.cleanapp.cacheappclean.R;
import com.cacheclean.cleanapp.cacheappclean.UserI.invent.FragmentWrappers;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import static com.cacheclean.cleanapp.cacheappclean.ScreenSp.cleanEnabled;

public class MainActiv extends AppCompatActivity {

    private RelativeLayout optimizeNow;
    private RelativeLayout batterysaver, junkcleaner, speedbooster, cpucooler;

    private DecoView arcView, arcView2;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    private int alljunk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AdView adView = findViewById(R.id.adView);

                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
            }
        }, 2000);

        optimizeNow = (RelativeLayout) findViewById(R.id.optimize_now);
        optimizeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cleanEnabled) {

                    try {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean("isUsedCachedData", false);
                        editor.commit();
                    } catch (Exception e) {

                    }

                    Intent i = new Intent(MainActiv.this, JunkScan.class);
                    i.putExtra("junk", alljunk + "");
                    startActivity(i);

                    cleanEnabled = false;
                } else {

                    LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View layout = inflater.inflate(R.layout.my_toast, null);

//                    ImageView image = (ImageView) layout.findViewById(R.id.image);

                    TextView text = (TextView) layout.findViewById(R.id.textView1);
                    text.setText("No Junk Files ALready Cleaned.");

                    Toast toast = new Toast(MainActiv.this);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        batterysaver = (RelativeLayout) findViewById(R.id.batterysaver);
        junkcleaner = (RelativeLayout) findViewById(R.id.junkcleaner);
        speedbooster = (RelativeLayout) findViewById(R.id.speedbooster);
        cpucooler = (RelativeLayout) findViewById(R.id.cpucooler);

        batterysaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FragmentWrappers.class);
                intent.putExtra(FragmentWrappers.REQUEST_ACTIVITY_CODE, FragmentWrappers.BATTERY_SAVER_CODE);
                startActivity(intent);
            }
        });

        junkcleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FragmentWrappers.class);
                intent.putExtra(FragmentWrappers.REQUEST_ACTIVITY_CODE, FragmentWrappers.JUNK_CLEANER_CODE);
                startActivity(intent);
            }
        });

        speedbooster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FragmentWrappers.class);
                intent.putExtra(FragmentWrappers.REQUEST_ACTIVITY_CODE, FragmentWrappers.BOOSTER_CODE);
                startActivity(intent);
            }
        });

        cpucooler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhoneInfoActivity.class);
                startActivity(intent);
            }
        });

        arcView = (DecoView) findViewById(R.id.dynamicArcView2);
        arcView2 = (DecoView) findViewById(R.id.dynamicArcView3);

        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.RED)  // to show used memory color
                .setRange(0, 100, 100)
                .setLineWidth(32f)
                .build();

        arcView.addSeries(seriesItem2);

        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(0)
                .setListener(new DecoEvent.ExecuteEventListener() {
                    @Override
                    public void onEventStart(DecoEvent decoEvent) {
                    }

                    @Override
                    public void onEventEnd(DecoEvent decoEvent) {

                    }
                })
                .build());

        SeriesItem seriesItem = new SeriesItem.Builder(Color.BLUE)  // to show used memory color
                .setRange(0, 100, 0)
                .setLineWidth(32f)
                .setInterpolator(new AccelerateInterpolator())
                .build();

        int series1Index2 = arcView2.addSeries(seriesItem);

        arcView2.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(100)
                .setDuration(0)
                .setListener(new DecoEvent.ExecuteEventListener() {
                    @Override
                    public void onEventStart(DecoEvent decoEvent) {
                    }

                    @Override
                    public void onEventEnd(DecoEvent decoEvent) {

                    }
                })
                .build());

        arcView2.addEvent(new DecoEvent.Builder(33).setIndex(series1Index2).setDelay(3000).setListener(new DecoEvent.ExecuteEventListener() {
            @Override
            public void onEventStart(DecoEvent decoEvent) {

            }

            @Override
            public void onEventEnd(DecoEvent decoEvent) {

            }
        }).build());
    }
}
