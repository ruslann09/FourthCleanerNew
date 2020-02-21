package com.cacheclean.cleanapp.cacheappclean.Frags;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cacheclean.cleanapp.cacheappclean.ActivMain;
import com.cacheclean.cleanapp.cacheappclean.JunkAlar;
import com.cacheclean.cleanapp.cacheappclean.JunkScan;
import com.cacheclean.cleanapp.cacheappclean.R;
import com.cacheclean.cleanapp.cacheappclean.UserI.invent.FragmentWrappers;

import java.io.File;
import java.util.List;
import java.util.Random;

import static android.content.Context.ALARM_SERVICE;
import static com.cacheclean.cleanapp.cacheappclean.ScreenSp.cleanEnabled;

public class JunkClean extends Fragment {

    public final static String MODE_START_NOW = "START_NOW_MODE";

    public final static String JUNKSFILES = "JUNKSFILESALL", TEMPFILES = "TEMPORARIESFILESALL";

    ImageView mainbrush;
    TextView maintext, cachetext, temptext, residuetext, systemtext;
    public static ImageView mainbutton;

    public static final int RC_HANDLE_PERMISSIONA_ALL = 2;

    public static boolean isPermission = false;

    int checkvar = 0;
    int alljunk;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    View view;

    private LinearLayout tools, myPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.junk_cleaner, container, false);
        mainbrush = (ImageView) view.findViewById(R.id.mainbrush);
        mainbutton = (ImageView) view.findViewById(R.id.mainbutton);

        maintext = (TextView) view.findViewById(R.id.maintext);
        cachetext = (TextView) view.findViewById(R.id.cachetext);
        temptext = (TextView) view.findViewById(R.id.temptext);
        residuetext = (TextView) view.findViewById(R.id.residuetext);
        systemtext = (TextView) view.findViewById(R.id.systemtext);

        try {

            sharedpreferences = getActivity().getSharedPreferences("waseem", Context.MODE_PRIVATE);

            if (cleanEnabled) {
//                mainbrush.setImageResource(R.drawable.red_pogo_circle);
                cachetext.setTextColor(getResources().getColor(R.color.red));
                temptext.setTextColor(getResources().getColor(R.color.red));
                residuetext.setTextColor(getResources().getColor(R.color.red));
                systemtext.setTextColor(getResources().getColor(R.color.red));

                Random ran1 = new Random();
                final int proc1 = sharedpreferences.getInt("proc1", ran1.nextInt(80) + 10);

                Random ran2 = new Random();
                final int proc3 = sharedpreferences.getInt("proc2", ran2.nextInt(70) + 20);

                Random ran3 = new Random();
                final int proc2 = sharedpreferences.getInt("proc3", ran3.nextInt(100) + 30);

                Random ran4 = new Random();
                final int proc4 = sharedpreferences.getInt("proc4", ran4.nextInt(90) + 20);

                alljunk = proc1 + proc2 + proc3 + proc4;

                SpannableStringBuilder builder = new SpannableStringBuilder();

                String red = getString(R.string.total_junks);
                SpannableString redSpannable = new SpannableString(red);
                redSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#655050")), 0, red.length(), 0);
                builder.append(redSpannable);

                String white = alljunk + " MB";
                SpannableString whiteSpannable = new SpannableString(white);
                whiteSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, white.length(), 0);
                builder.append(whiteSpannable);

                maintext.setText(builder, TextView.BufferType.SPANNABLE);
//                maintext.setTextColor(Color.parseColor("#f63030"));

                cachetext.setText(proc1 + " MB");

                temptext.setText(proc2 + " MB");

                residuetext.setText(proc3 + " MB");

                systemtext.setText(proc4 + " MB");

            } else {
//                mainbrush.setImageResource(R.drawable.green_pogo_circle);
                cachetext.setTextColor(getResources().getColor(R.color.orange_light));
                temptext.setTextColor(getResources().getColor(R.color.orange_light));
                residuetext.setTextColor(getResources().getColor(R.color.orange_light));
                systemtext.setTextColor(getResources().getColor(R.color.orange_light));


                maintext.setText(R.string.super_clear);

                cachetext.setText(0 + " MB");

                temptext.setText(0 + " MB");

                residuetext.setText(0 + " MB");

                systemtext.setText(0 + " MB");
            }

            if (getActivity().getIntent() != null) {
                try {
                    if (getActivity().getIntent ().getStringExtra(FragmentWrappers.RUNTIME_MODE).equals (MODE_START_NOW))
                        makeCleaning();
                } catch (Exception e) {

                }
            }


            mainbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        isPermission = true;

                        if (cleanCacheAndTemp(getActivity().getApplicationContext(), false) > 0) {
                            cleanEnabled = true;
                        }

                        makeCleaning();

                    } else {
                        requestAndExternalPermission();
                    }
                }
            });


//            Random ran1 = new Random();
//            final int proc1 = ran1.nextInt(20) + 5;
//
//            Random ran2 = new Random();
//            final int proc2 = ran2.nextInt(15) + 10;
//
//            Random ran3 = new Random();
//            final int proc3 = ran3.nextInt(30) + 15;
//
//            Random ran4 = new Random();
//            final int proc4 = ran4.nextInt(25) + 10;
//
//            alljunk=proc1+proc2+proc3+proc4;
//
//            maintext.setText(alljunk+" MB");
//            maintext.setTextColor(Color.parseColor("#F22938"));
//
//            cachetext.setText(proc1+" MB");
//            cachetext.setTextColor(Color.parseColor("#F22938"));
//
//            temptext.setText(proc2+" MB");
//            temptext.setTextColor(Color.parseColor("#F22938"));
//
//            residuetext.setText(proc3+" MB");
//            residuetext.setTextColor(Color.parseColor("#F22938"));
//
//            systemtext.setText(proc4+" MB");
//            systemtext.setTextColor(Color.parseColor("#F22938"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_PERMISSIONA_ALL) {
            Log.d("sdfas", "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            isPermission = true;

            if (cleanCacheAndTemp(getActivity().getApplicationContext(), false) > 0) {
                cleanEnabled = true;
            }

            makeCleaning();

            return;
        }

        Log.e("sdfasd", "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestAndExternalPermission() {
        Log.w("sdfads", "Camera permission is not granted. Requesting permission");

        final String [] permission = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};

        if(!hasPermissions(getActivity().getApplicationContext(), permission)){
            ActivityCompat.requestPermissions(getActivity(), permission, RC_HANDLE_PERMISSIONA_ALL);
            return;
        }

        final Activity thisActivity = getActivity();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!hasPermissions(thisActivity, permission)){
                    ActivityCompat.requestPermissions(thisActivity, permission, RC_HANDLE_PERMISSIONA_ALL);
                }
            }
        };
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void makeCleaning () {
        if (!isPermission) {

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.app_name)
                    .setMessage(R.string.no_permission)
                    .setPositiveButton(R.string.ok, listener)
                    .show();
            return;
        }

        if (cleanEnabled) {

            try {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("isUsedCachedData", false);
                editor.commit();
            } catch (Exception e) {

            }

            cleanCacheAndTemp(getActivity(), true);

            Intent i = new Intent(getActivity(), JunkScan.class);
            i.putExtra("junk", alljunk + "");
            startActivity(i);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms


//                    mainbrush.setImageResource(R.drawable.green_pogo_circle);
                    maintext.setText(getResources().getString(R.string.crystal_clear));
                    maintext.setTextColor(Color.parseColor("#136af6"));

                    cachetext.setText(0 + " MB");
                    cachetext.setTextColor(getResources().getColor(R.color.orange_light));

                    temptext.setText(0 + " MB");
                    temptext.setTextColor(getResources().getColor(R.color.orange_light));

                    residuetext.setText(0 + " MB");
                    residuetext.setTextColor(getResources().getColor(R.color.orange_light));

                    systemtext.setText(0 + " MB");
                    systemtext.setTextColor(getResources().getColor(R.color.orange_light));


                    editor = sharedpreferences.edit();
                    editor.putString("junk", "0");
                    editor.commit();


                    Intent intent = new Intent(getActivity(), JunkAlar.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0,
                            intent, PendingIntent.FLAG_ONE_SHOT);

                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (600 * 1000), pendingIntent);

                }
            }, 2000);

            cleanEnabled = false;
        } else {
//                        Toast.makeText(getActivity(), "No Junk Files ALready Cleaned.", Toast.LENGTH_LONG).show();

            @SuppressLint("RestrictedApi") LayoutInflater inflater = getLayoutInflater(getArguments());
            View layout = inflater.inflate(R.layout.my_toast, null);

            ImageView image = (ImageView) layout.findViewById(R.id.image);

            TextView text = (TextView) layout.findViewById(R.id.textView1);
            text.setText("No Junk Files ALready Cleaned.");

            Toast toast = new Toast(getActivity());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    }

    @Override
    public boolean getUserVisibleHint() {

//        ActivMain.name.setText("Junk Cleaner");
        return getUserVisibleHint();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            ActivMain.name.setText("Junk Cleaner");
        } else {}
    }

    public static int cleanCacheAndTemp(Context Activity, boolean scanAndDelete) {
        int scan = 0;

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = Activity.getPackageManager().queryIntentActivities(mainIntent, 0);

//        String mData = Environment.getExternalStorageDirectory() + File.separator + "Android" + Environment.getDataDirectory() + File.separator;
//        File mFolder, mFolderTemp, mFolderFilesCash;
//
//        for (ResolveInfo resolveInfo : pkgAppsList) {
//            mFolder = new File(mData + resolveInfo.activityInfo.packageName + File.separator + "cache");
//            mFolderTemp = new File(mData + resolveInfo.activityInfo.packageName + File.separator + "tmp");
//            mFolderFilesCash = new File(mData + resolveInfo.activityInfo.packageName + File.separator + "files" + File.separator + "cache");
//            if (!mFolder.exists()) continue;
//
//            if (mFolder.isDirectory()) {
//                String[] children = mFolder.list();
//                for (String child : children) {
//                    if (scanAndDelete) {
//                        new File(mFolder, child).delete();
//                        deleteDirectory(new File(mFolder, child));
//                    } else {
//                        scan++;
//                    }
//                }
//            }
//
//            if (mFolderTemp.isDirectory()) {
//                String[] children = mFolderTemp.list();
//                for (String child : children) {
//                    if (scanAndDelete) {
//                        new File(mFolderTemp, child).delete();
//                        deleteDirectory(new File(mFolderTemp, child));
//                    } else {
//                        scan++;
//                    }
//                }
//            }
//
//            if (mFolderFilesCash.isDirectory()) {
//                String[] children = mFolderFilesCash.list();
//                for (String child : children) {
//                    if (scanAndDelete) {
//                        new File(mFolderTemp, child).delete();
//                        deleteDirectory(new File(mFolderTemp, child));
//                    } else {
//                        scan++;
//                    }
//                }
//            }
//        }

        return scan;
    }

    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }
}
