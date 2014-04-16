package com.hyyft.noteeverything.broadcastreceiver;


import com.hyyft.noteeverything.service.MainService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 接受系统重新启动广播，并启动一个service
 * @author hyyft
 *
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent intent2 = new Intent(context , MainService.class);
		context.startService(intent2);
	}

}
