package com.cacheclean.cleanapp.cacheappclean.UserI;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cacheclean.cleanapp.cacheappclean.R;

import java.io.File;

public class PhoneInfoActivity extends AppCompatActivity {

    private ImageView backBtn;
    private TextView model, brand, ram, cpu, storage, os, resolution, battery, ipAdress, id, user, finger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try {
            getSupportActionBar().hide();
        } catch (Exception e) {

        }

        setContentView(R.layout.info_about_phone);

        backBtn = (ImageView) findViewById(R.id.exit_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        model = (TextView) findViewById(R.id.model);
        brand = (TextView) findViewById(R.id.brand);
        ram = (TextView) findViewById(R.id.ram);
        cpu = (TextView) findViewById(R.id.cpu);
        storage = (TextView) findViewById(R.id.storage);
        os = (TextView) findViewById(R.id.os);
        resolution = (TextView) findViewById(R.id.resolution);
        battery = (TextView) findViewById(R.id.battery);
        ipAdress = (TextView) findViewById(R.id.ip);
        id = (TextView) findViewById(R.id.id);
        user = (TextView) findViewById(R.id.user);
        finger = (TextView) findViewById(R.id.finger);

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        double totalRam = (double)(memoryInfo.totalMem) / (1024 * 1024) /1000;
        totalRam = Math.ceil(totalRam);

        os.setText(Build.VERSION.RELEASE);
        model.setText(Build.MODEL);
        brand.setText(Build.MANUFACTURER);
        cpu.setText(Build.HARDWARE);
        ram.setText((int)totalRam + " GB");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        resolution.setText(screenHeight + "x" + screenWidth);

        WifiManager wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));

        try {
            ipAdress.setText(ip);

            battery.setText(String.valueOf(getBatteryCapacity().intValue()) + " MAh");

            File external = Environment.getExternalStorageDirectory();

            float freeStorageSpace = (float) Math.round((float) external.getFreeSpace() / (1024 * 1024 * 1024) * 100) / 100;
            float totalStorageSpace = (float) Math.round((float) external.getTotalSpace() / (1024 * 1024 * 1024) * 100) / 100;

            storage.setText(((float) Math.round((totalStorageSpace - freeStorageSpace) * 100) / 100) + " / " + totalStorageSpace + " GB");

            finger.setText("Not available");
        } catch (Exception e) {}

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                FingerprintManager fingerprintManager = (FingerprintManager) getApplicationContext().getSystemService(Context.FINGERPRINT_SERVICE);

                if (fingerprintManager.isHardwareDetected())
                    finger.setText("Available");
            }
        } catch (Exception e) {}

        try {
            id.setText(Build.SERIAL);
            user.setText(Build.USER);
        } catch (Exception e) {}
    }

    public Double getBatteryCapacity(){
        Object powerProfile = null;
        Double batteryCapacity = 0d;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try{
            powerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            batteryCapacity = (Double) Class.forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", String.class)
                    .invoke(powerProfile, "battery.capacity");
        }catch (Exception e) {
            e.printStackTrace();
        }

        return batteryCapacity;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
