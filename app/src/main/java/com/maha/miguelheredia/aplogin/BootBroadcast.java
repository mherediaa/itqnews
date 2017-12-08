package com.maha.miguelheredia.aplogin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Miguel Heredia on 31/10/2017.
 */

public class BootBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        ctx.startService(new Intent (ctx, ConsultaStatus.class));
    }

}

