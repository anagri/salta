package com.salta.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;

public class LoginService extends Service {
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				doService();
				Looper.loop();
			}
		}.start();
	}
	
	public void doService() {
		SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
