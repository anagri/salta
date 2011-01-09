package com.salta;

import com.salta.service.SaltaService;
import com.salta.service.SaltaServiceHost;

import android.app.ListActivity;
import android.os.Bundle;

public class SaltaBaseListActivity extends ListActivity {
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
