package com.salta.web;

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
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.google.gson.Gson;
import com.salta.core.Contact;
import com.salta.core.Group;
import com.salta.core.LoginException;

public class SaltaClient {
	private static final String SERVER_URL = "http://10.12.6.36:3000/";
	private static SaltaClient instance = new SaltaClient();
	private DefaultHttpClient httpClient;
	private HttpContext localContext;

	private SaltaClient() {
		httpClient = new DefaultHttpClient();
		CookieStore cookieStore = new BasicCookieStore();
		localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}

	public static SaltaClient client() {
		return instance;
	}

	public final HttpResponse execute(HttpUriRequest request)
			throws IOException, ClientProtocolException {
		return httpClient.execute(request, localContext);
	}

	public void login(String username, String password) {
		HttpPost httpRequest = new HttpPost(SERVER_URL
				+ "user_sessions.json?user_session[username]=" + username
				+ "&user_session[password]=" + password);
		try {
			HttpResponse response = httpClient.execute(httpRequest,
					localContext);
			String json = parseResponse(response.getEntity());
			Log.d("SaltaClient", json);
			if(response.getStatusLine().getStatusCode() == 422) {
				throw new Gson().fromJson(json, LoginException.class);
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<Group> groups() {
		List<Group> groups = new ResourceRepository<Group>().getResources("group", SERVER_URL
				+ "android/groups", Group.class);
		return groups;
	}

	public List<Contact> contacts(int groupId) {
		List<Contact> contacts = new ResourceRepository<Contact>()
				.getResources("contact", SERVER_URL + "android/groups/"
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

	private String parseResponse(HttpEntity responseEntity)
			throws IOException {
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
