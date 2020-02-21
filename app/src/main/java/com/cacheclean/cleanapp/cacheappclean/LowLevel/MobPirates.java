package com.cacheclean.cleanapp.cacheappclean.LowLevel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MobPirates {
    private static final String UTM_SOURCE = "utm_source=";
    private static final String SHARED_PREF_NAME = "mob_pref";
    private static final String KEY_INSTALL_REFERRER = "key_install_referrer";
    private static final String KEY_FACEBOOK_REF = "key_facebook_ref";
    private static final String KEY_FACEBOOK_URI = "key_facebook_uri";
    private static final String EXCEPTION_NOT_INITIALIZED = "MobPirates must be initialized firstly by call init() method";
    private static final String REFERRER_PREF = "referrer";
    private static final String KEY_FACEBOOK_APP_ID = "2040099052732713";
    private static MobPirates INSTANCE;
    private SharedPreferences prefs;
    private String referrerUrl;
    private String facebookRef;
    private String facebookUri;
    private String utmSource;

    private MobPirates() {
    }

    public static MobPirates getInstance() {
        if (INSTANCE == null) {
            Class var0 = MobPirates.class;
            synchronized(MobPirates.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MobPirates();
                }
            }
        }

        return INSTANCE;
    }

    public void init(Context context) {
        Context appContext = context.getApplicationContext();
        String sharedPrefName = appContext.getPackageName() + "mob_pref";
        this.prefs = appContext.getSharedPreferences(sharedPrefName, 0);
        this.referrerUrl = this.prefs.getString("key_install_referrer", (String)null);
        this.utmSource = this.tryCatchUtmSource(this.referrerUrl);
        this.facebookRef = this.prefs.getString("key_facebook_ref", (String)null);
        this.facebookUri = this.prefs.getString("key_facebook_uri", (String)null);
//        if (TextUtils.isEmpty(this.facebookRef) || TextUtils.isEmpty(this.facebookUri)) {
//            FacebookSdk.setApplicationId("2040099052732713");
//            FacebookSdk.sdkInitialize(appContext);
//            AppLinkData.fetchDeferredAppLinkData(appContext, "2040099052732713", new CompletionHandler() {
//                public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
//                    if (appLinkData != null) {
//                        String ref = appLinkData.getRef();
//                        if (!TextUtils.isEmpty(ref)) {
//                            ru.mail.aslanisl.mobpirate.MobPirates.this.prefs.edit().putString("key_facebook_ref", ref).apply();
//                            ru.mail.aslanisl.mobpirate.MobPirates.this.facebookRef = ref;
//                        }
//
//                        Uri uri = appLinkData.getTargetUri();
//                        if (uri != null) {
//                            ru.mail.aslanisl.mobpirate.MobPirates.this.prefs.edit().putString("key_facebook_uri", uri.toString()).apply();
//                            ru.mail.aslanisl.mobpirate.MobPirates.this.facebookUri = uri.toString();
//                        }
//
//                    }
//                }
//            });
//        }
    }

    String tryCatchUtmSource(String referrerUrl) {
        String source = null;

        try {
            String valueWithAp = referrerUrl.replace("utm_source=", "");
            source = valueWithAp.split("&")[0];
        } catch (Exception var4) {
            ;
        }

        return source;
    }

    void saveInstallReferrerFromIntent(Intent intent) {
        if (this.prefs == null) {
            throw new IllegalStateException("MobPirates must be initialized firstly by call init() method");
        } else {
            String url = intent.getStringExtra("referrer");
            if (url != null) {
                this.prefs.edit().putString("key_install_referrer", url).apply();
                this.referrerUrl = url;
                this.utmSource = this.tryCatchUtmSource(url);
            }
        }
    }

    public String getClientId() {
        return this.utmSource;
    }
}
