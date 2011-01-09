package com.salta;

import android.app.Activity;
import android.os.Bundle;

import com.salta.service.SaltaService;
import com.salta.service.SaltaServiceHost;

public class SaltaBaseActivity extends Activity {
	private SaltaServiceHost saltaServiceHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saltaServiceHost = new SaltaServiceHost(this);
	}
	
	public SaltaService service() {
		return saltaServiceHost.service();
	}
}
