package com.salta;

import java.util.List;

import com.salta.core.Group;
import com.salta.web.SaltaClient;

import android.app.Activity;
import android.os.Bundle;

public class GroupListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Group> groups = SaltaClient.client().groups();
	}
}
