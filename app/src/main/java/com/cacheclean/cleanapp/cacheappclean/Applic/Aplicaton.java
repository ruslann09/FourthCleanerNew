package com.cacheclean.cleanapp.cacheappclean.Applic;

import android.app.Application;

import com.cacheclean.cleanapp.cacheappclean.R;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class Aplicaton extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Создание расширенной конфигурации библиотеки.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(getString(R.string.yandex_metrik)).build();
        // Инициализация AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Отслеживание активности пользователей.
        YandexMetrica.enableActivityAutoTracking(this);

//            MobPirates.getInstance().init(this);

//        try {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    MobPirates.getInstance().getClientId();
//                }
//            }, 1000);
//        } catch (Exception e) {
//
//        }
    }
}