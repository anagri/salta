package com.salta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.salta.core.Group;

public class HomeActivity extends Activity {
	private DefaultHttpClient httpClient;
	private CookieStore cookieStore;
	private HttpContext localContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		httpClient = new DefaultHttpClient();
		cookieStore = new BasicCookieStore();
		localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
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
		HttpPost httpRequest = new HttpPost("http://10.12.6.36:3000/user_sessions?user_session[email]="+email+"&user_session[password]="+password);
		try {
			HttpResponse response = httpClient.execute(httpRequest, localContext);
			Log.d("ReviewsSyncService","response "+ parseResponse(response.getEntity()));
			HttpGet groupsRequest = new HttpGet("http://10.12.6.36:3000/home");
			HttpResponse groupsResponse = httpClient.execute(groupsRequest, localContext);
	        HttpEntity responseEntity = groupsResponse.getEntity();
	         
	        StringBuilder finalJsonString = parseResponse(responseEntity);

	        JSONArray jsonArray = new JSONArray(finalJsonString.toString());
	        List<Group> groups = new ArrayList<Group>();
	        for (int i = 0; i < jsonArray.length(); i++) {
	        	String groupJsonString = jsonArray.getJSONObject(i).getJSONObject("group").toString();
				groups.add(new Gson().fromJson(groupJsonString, Group.class));
			}
	        Log.d("HomeActivity", "Groups:"+ groups.toString());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private StringBuilder parseResponse(HttpEntity responseEntity)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
		StringBuilder finalJsonString = new StringBuilder();
		String eachLine;
		while ((eachLine = bufferedReader.readLine()) != null) {
		    finalJsonString.append(eachLine);
		}
		return finalJsonString;
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
