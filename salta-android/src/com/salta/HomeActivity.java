package com.salta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

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
		String email = ((TextView) findViewById(R.id.emailInput)).getText()
				.toString();
		String password = ((TextView) findViewById(R.id.passwordInput))
				.getText().toString();
		boolean remember = ((CheckBox) findViewById(R.id.rememberMeCheckBox))
				.isChecked();
		getPreferences(MODE_PRIVATE).edit()
				.putString(SaltaPreference.USER_EMAIL, email)
				.putString(SaltaPreference.PASSWORD, password)
				.putBoolean(SaltaPreference.REMEMBER, remember)
				.commit();
		SaltaClient client = SaltaClient.client();
		client.login(email, password);
		startActivity(new Intent(this, GroupListActivity.class));
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
