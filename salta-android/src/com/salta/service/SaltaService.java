package com.salta.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.salta.SaltaPreference;
import com.salta.core.Contact;
import com.salta.core.Group;
import com.salta.core.LoginException;

public class SaltaService extends Service {
	private SaltaServiceBinder binder;
	private DefaultHttpClient httpClient;
	private HttpContext localContext;

	@Override
	public void onCreate() {
		super.onCreate();
		binder = new SaltaServiceBinder();
		httpClient = new DefaultHttpClient();
		CookieStore cookieStore = new BasicCookieStore();
		localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_REDELIVER_INTENT;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	private String serverUrl() {
		return getPreferences().getString(SaltaPreference.SERVER_URL, null);
	}

	private SharedPreferences getPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(this);
	}

	public class SaltaServiceBinder extends Binder {
		public SaltaService service() {
			return SaltaService.this;
		}
	}

	public void login(String username, String password) {
		String loginUrl = serverUrl()
				+ "user_sessions.json?user_session[username]=" + username
				+ "&user_session[password]=" + password;
		Log.d("SaltaService", loginUrl);
		HttpPost httpRequest = new HttpPost(loginUrl);
		try {
			HttpResponse response = httpClient.execute(httpRequest,
					localContext);
			String json = parseResponse(response.getEntity());
			Log.d("SaltaClient", json);
			if (response.getStatusLine().getStatusCode() == 422) {
				throw new Gson().fromJson(json, LoginException.class);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<Group> groups() {
		List<Group> groups = new ResourceRepository<Group>().getResources(
				"group", serverUrl() + "android/groups", Group.class);
		return groups;
	}

	public List<Contact> contacts(int groupId) {
		List<Contact> contacts = new ResourceRepository<Contact>()
				.getResources("contact", serverUrl() + "android/groups/"
						+ groupId + "/members", Contact.class);
		return contacts;
	}

	private class ResourceRepository<T> {
		public List<T> getResources(String resourceName, String url,
				Class resourceClass) {
			try {
				HttpGet request = new HttpGet(url);
				HttpResponse response = httpClient.execute(request,
						localContext);
				HttpEntity responseEntity = response.getEntity();
				String json = parseResponse(responseEntity);
				Log.d("SaltaClient", json);
				JSONArray jsonArray = new JSONArray(json);
				List<T> resources = new ArrayList<T>();
				for (int i = 0; i < jsonArray.length(); i++) {
					String groupJsonString = jsonArray.getJSONObject(i)
							.toString();
					resources.add((T) new Gson().fromJson(groupJsonString,
							resourceClass));
				}
				return resources;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private String parseResponse(HttpEntity responseEntity) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(responseEntity.getContent()));
		StringBuilder json = new StringBuilder();
		String read;
		while ((read = bufferedReader.readLine()) != null) {
			json.append(read);
		}
		return json.toString();
	}
}
