package com.hyyft.noteeverything.broadcastreceiver;


import com.hyyft.noteeverything.service.MainService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * ����ϵͳ���������㲥��������һ��service
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
