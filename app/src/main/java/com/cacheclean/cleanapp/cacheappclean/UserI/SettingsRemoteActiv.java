package com.cacheclean.cleanapp.cacheappclean.UserI;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.cacheclean.cleanapp.cacheappclean.R;

import java.util.Locale;

public class SettingsRemoteActiv extends AppCompatActivity {

    private Switch notifsStateChanger;
    private Spinner langChanger;
    private SharedPreferences configs;
    private SharedPreferences.Editor configsEditor;

    private LinearLayout privacyReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_remote);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configs = getSharedPreferences("Configs", MODE_PRIVATE);
        configsEditor = configs.edit();

        notifsStateChanger = (Switch) findViewById(R.id.notifsCenterChange);

        privacyReference = (LinearLayout) findViewById(R.id.privacy_reference);

        privacyReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri policy_privacy = Uri.parse("https://docs.google.com/document/d/1N0CT-j8uRo5dPMT7Ife7ySbAZ9PmlShW0nbza-11jF0/edit?usp=sharing");
                Intent link = new Intent (Intent.ACTION_VIEW, policy_privacy);
                startActivity (link);
            }
        });

        if (checkNotificationEnabled())
            notifsStateChanger.setChecked(true);

        notifsStateChanger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!checkNotificationEnabled()) {
                    redirectToSecureSettings();

                    return;
                }

                configsEditor.putBoolean("isRequireNotifsControl", isChecked);
                configsEditor.apply();

                int NOTIFY_ID = 1;

                if (!isChecked) {
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(NOTIFY_ID);
                }
            }
        });

        langChanger = (Spinner) findViewById(R.id.langChanger);

        try {
            langChanger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String lang = sharedPreferences.getString("lang", "en");

                    switch (position) {
                        case 0:
                            if (!lang.equals("en")) {
                                Locale locale = new Locale("en");
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;

                                getBaseContext().getResources().updateConfiguration(config, null);
                            }
                            break;
                        case 1:
                            if (!lang.equals("ru")) {
                                Locale locale = new Locale("ru");
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;

                                getBaseContext().getResources().updateConfiguration(config, null);
                            }
                            break;
                        case 2:
                            if (!lang.equals("ar")) {
                                Locale locale = new Locale("ar");
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;

                                getBaseContext().getResources().updateConfiguration(config, null);
                            }
                            break;
                        case 3:
                            if (!lang.equals("zh")) {
                                Locale locale = new Locale("zh");
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;

                                getBaseContext().getResources().updateConfiguration(config, null);
                            }
                            break;
                        case 4:
                            if (!lang.equals("es")) {
                                Locale locale = new Locale("es");
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;

                                getBaseContext().getResources().updateConfiguration(config, null);
                            }
                            break;
                        case 5:
                            if (!lang.equals("fr")) {
                                Locale locale = new Locale("fr");
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;

                                getBaseContext().getResources().updateConfiguration(config, null);
                            }
                            break;
                        case 6:
                            if (!lang.equals("ko")) {
                                Locale locale = new Locale("ko");
                                Locale.setDefault(locale);
                                Configuration config = new Configuration();
                                config.locale = locale;

                                getBaseContext().getResources().updateConfiguration(config, null);
                            }
                            break;
                    }

//                    try {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                startService(new Intent (getApplicationContext(), ComeBackToApped.class));
//
//                                System.exit(0);
//                            }
//                        }, 1500);
//                    } catch (Exception e) {
//                        Toast.makeText(SettingsRemoteActiv.this, "Reset your app", Toast.LENGTH_SHORT).show();
//                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.toString() + "", Toast.LENGTH_SHORT).show();
        }
    }

    //check notification access setting is enabled or not
    public boolean checkNotificationEnabled() {
        try{
            if(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    "enabled_notification_listeners").contains(getApplicationContext().getPackageName())) {
                return true;
            } else {
                return false;
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void redirectToSecureSettings() {
        Toast.makeText(getApplicationContext(), "You should allow this app to use notification control before using",
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                startActivity (new Intent(getApplicationContext(), MainMenuScreen.class));

                finish ();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static long TIME_INTERVAL_FOR_EXIT = 1500;
    private long lastTimeBackPressed;

    @Override
    public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
        if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
//            if(System.currentTimeMillis() - lastTimeBackPressed < TIME_INTERVAL_FOR_EXIT) {
//                finish();
//            }
//            else {}

//            startActivity (new Intent (getApplicationContext(), MePageActivity.class));
            finish ();

            return true;
        } else {
            return super.onKeyDown(pKeyCode, pEvent);
        }
    }
}