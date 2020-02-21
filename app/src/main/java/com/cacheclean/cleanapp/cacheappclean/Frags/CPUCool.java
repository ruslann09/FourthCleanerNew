package com.cacheclean.cleanapp.cacheappclean.Frags;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cacheclean.cleanapp.cacheappclean.ActivMain;
import com.cacheclean.cleanapp.cacheappclean.Clas.Apes;
import com.cacheclean.cleanapp.cacheappclean.R;
import com.cacheclean.cleanapp.cacheappclean.RecyclerAdapt;
import com.cacheclean.cleanapp.cacheappclean.ScannerCP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class CPUCool extends Fragment {

    TextView batterytemp, showmain, showsec, nooverheating;
    float temp;
    ImageView coolbutton, tempimg,ivtemping;
    RecyclerView recyclerView;
    RecyclerAdapt mAdapter;
    public static List<Apes> apps;
    List<Apes> apps2;
    int check = 0;

    private LinearLayout tools, myPage;

    BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            makeStabilityScanning(intent);
        }
    };

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cpu_cooler, container, false);

        try {
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

            ivtemping= (ImageView) view.findViewById(R.id.iv_tempimg);
            tempimg = (ImageView) view.findViewById(R.id.tempimg);
            showmain = (TextView) view.findViewById(R.id.showmain);
            showsec = (TextView) view.findViewById(R.id.showsec);
            coolbutton = (ImageView) view.findViewById(R.id.coolbutton);
            nooverheating = (TextView) view.findViewById(R.id.nooverheating);

//            showmain.setText("NORMAL");
//            showsec.setText("CPU Temperature is GOOD");
//            nooverheating.setText("Currently No Aplicaton causing Overheating");
//            coolbutton.setImageResource(R.mipmap.ic_cooled_btn_hover);


            showmain.setText(R.string.normal);
            showmain.setTextColor(Color.parseColor("#fff"));
            showsec.setText(R.string.temperature_is_norm);
            showsec.setTextColor(Color.parseColor("#4e5457"));
            nooverheating.setText(R.string.no_apps_to_closing);
            nooverheating.setTextColor(Color.parseColor("#4e5457"));

            coolbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @SuppressLint("RestrictedApi") LayoutInflater inflater = getLayoutInflater(getArguments());
                    View layout = inflater.inflate(R.layout.my_toast, null);

                    ImageView image = (ImageView) layout.findViewById(R.id.image);

                    TextView text = (TextView) layout.findViewById(R.id.textView1);
                    text.setText("CPU Temperature is Already Normal.");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            });

//            tempimg.setImageResource(R.drawable.blue_cooler);
            batterytemp = (TextView) view.findViewById(R.id.batterytemp);

            if (!((System.currentTimeMillis() - getActivity().getSharedPreferences("APPS_CONFIGS", Context.MODE_PRIVATE).getLong("COOLER_LAST_UPDATE", 0)) < 1200000)) {
                makeStabilityScanning(null);
            }

            Log.e("Temperrature", temp + "");
        } catch (Exception e) {

        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {

            getActivity().unregisterReceiver(batteryReceiver);
        } catch (Exception e) {

        }
    }

    public void getAllICONS() {

        PackageManager pm = getActivity().getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        if (packages != null) {
            for (int k = 0; k < packages.size(); k++) {
                // String pkgName = app.getPackageName();
                String packageName = packages.get(k).packageName;
                Log.e("packageName-->", "" + packageName);

                if (!packageName.equals("fast.cleaner.battery.saver")) {

//                String size = packages.get(k).metaData.size()+"";
//                Log.e("Size-->", "" + packageName);
                    Drawable ico = null;
                    try {
                        String pName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                        Apes app = new Apes();

//                    app.setSize("" + pName);

                        File file = new File(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA).publicSourceDir);
                        long size = file.length();

                        Log.e("SIZE", size / 1000000 + "");
                        app.setSize(size / 1000000 + 20 + "MB");

                        ApplicationInfo a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                        app.setImage(ico = getActivity().getPackageManager().getApplicationIcon(packages.get(k).packageName));
                        getActivity().getPackageManager();
                        Log.e("ico-->", "" + ico);

                        if (((a.flags & ApplicationInfo.FLAG_SYSTEM) == 0)) {
//                        System.out.println(">>>>>>packages is system package"+pi.packageName);

                            if (check <= 5) {
                                check++;
                                apps.add(app);
                            } else {
                                getActivity().unregisterReceiver(batteryReceiver);
//                            batterytemp.setText("25.3" + "°C");
                                break;
                            }

                        }
                        mAdapter.notifyDataSetChanged();


                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("ERROR", "Unable to find icon for package '"
                                + packageName + "': " + e.getMessage());
                    }
                    // icons.put(processes.get(k).topActivity.getPackageName(),ico);
                }
            }

        }

        if (apps.size() > 1) {
            mAdapter = new RecyclerAdapt(apps);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void makeStabilityScanning (Intent intent) {
        try {
            if (intent == null)
                intent = getActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            temp = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 21)) / 10;

            batterytemp.setText(temp + "°C");

            if (temp >= 30.0) {
                apps = new ArrayList<>();
                apps2 = new ArrayList<>();
//                showmain.setText(getResources().getText(R.string.overheated));
                showmain.setTextColor(Color.parseColor("#F63030"));
                showsec.setText(R.string.apps_to_close);
                nooverheating.setText("");

//                    coolbutton.setImageResource(R.drawable.cool_button_blue);
                coolbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("APPS_CONFIGS", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong("COOLER_LAST_UPDATE", System.currentTimeMillis());
                        editor.commit();

                        Intent i = new Intent(getActivity(), ScannerCP.class);
                        startActivity(i);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

//                                    getActivity().unregisterReceiver(batteryReceiver);
                                nooverheating.setText(getResources().getString(R.string.no_apps_to_closing));
                                nooverheating.setTextColor(Color.parseColor("#4e5457"));
                                showmain.setText(getResources().getString(R.string.normal));
                                showmain.setTextColor(Color.parseColor("#fff"));
                                showsec.setText(getResources().getString(R.string.temperature_is_norm));
                                showsec.setTextColor(Color.parseColor("#4e5457"));
                                batterytemp.setText("25.3" + "°C");
                                recyclerView.setAdapter(null);

                            }
                        }, 2000);


                        coolbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Toast.makeText(getActivity(), "CPU Temperature is Already Normal", Toast.LENGTH_SHORT).show();

                                @SuppressLint("RestrictedApi") LayoutInflater inflater = getLayoutInflater(getArguments());
                                View layout = inflater.inflate(R.layout.my_toast, null);

                                ImageView image = (ImageView) layout.findViewById(R.id.image);

                                TextView text = (TextView) layout.findViewById(R.id.textView1);
                                text.setText(getResources().getString(R.string.temperature_is_norm));

                                Toast toast = new Toast(getActivity());
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();
                            }
                        });
                    }
                });

//                    if (Build.VERSION.SDK_INT < 23) {
//
//                        showsec.setTextAppearance(context, android.R.style.TextAppearance_Medium);
//                        showsec.setTextColor(Color.parseColor("#F22938"));
//
//                    } else {
//
//                        showsec.setTextAppearance(android.R.style.TextAppearance_Small);
//                        showsec.setTextColor(Color.parseColor("#F22938"));
//                    }


                recyclerView.setItemAnimator(new SlideInLeftAnimator());
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
//                recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

                recyclerView.getItemAnimator().setAddDuration(10000);

                mAdapter = new RecyclerAdapt(apps);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
                recyclerView.computeHorizontalScrollExtent();
                recyclerView.setAdapter(mAdapter);
                getAllICONS();

//                recyclerView.getItemAnimator().setRemoveDuration(1000);
//                recyclerView.getItemAnimator().setMoveDuration(1000);
//                recyclerView.getItemAnimator().setChangeDuration(1000);
            }
        }
        catch(Exception e) {}
    }

    @Override
    public boolean getUserVisibleHint() {

        ActivMain.name.setText("CPU Cooler");
        return getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            ActivMain.name.setText("CPU Cooler");
        } else {

        }
    }
}
