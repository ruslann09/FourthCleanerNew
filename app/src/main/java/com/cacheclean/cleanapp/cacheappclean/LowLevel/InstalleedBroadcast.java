package com.cacheclean.cleanapp.cacheappclean.LowLevel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import java.util.Iterator;
import java.util.List;

public class InstalleedBroadcast extends BroadcastReceiver {
    public InstalleedBroadcast() {
    }

    public void onReceive(Context context, Intent intent) {
        List<ResolveInfo> receivers = context.getPackageManager().queryBroadcastReceivers(new Intent("com.android.vending.INSTALL_REFERRER"), 0);
        Iterator var4 = receivers.iterator();

        while(var4.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo)var4.next();
            String action = intent.getAction();
            if (resolveInfo.activityInfo.packageName.equals(context.getPackageName()) && "com.android.vending.INSTALL_REFERRER".equals(action) && !this.getClass().getName().equals(resolveInfo.activityInfo.name)) {
                try {
                    BroadcastReceiver receiver = (BroadcastReceiver)Class.forName(resolveInfo.activityInfo.name).newInstance();
                    receiver.onReceive(context, intent);
                } catch (Exception var8) {
                    var8.printStackTrace();
                }
            }
        }

        MobPirates.getInstance().saveInstallReferrerFromIntent(intent);
    }
}
