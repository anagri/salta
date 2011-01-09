package com.salta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.salta.core.LoginException;
import com.salta.web.SaltaClient;

public class HomeActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void login(View view) {
		String username = ((TextView) findViewById(R.id.usernameInput)).getText()
				.toString();
		String password = ((TextView) findViewById(R.id.passwordInput))
				.getText().toString();
		getPreferences(MODE_PRIVATE).edit()
				.putString(SaltaPreference.USERNAME, username)
				.putString(SaltaPreference.PASSWORD, password).commit();
		SaltaClient client = SaltaClient.client();
		try {
			client.login(username, password);
			startActivity(new Intent(this, GroupListActivity.class));
		} catch (LoginException e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.preference:
			startActivity(new Intent(this, SaltaPreferenceActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
