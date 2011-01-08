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

public class SaltaClient {
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

	public void login(String email, String password) {
		HttpPost httpRequest = new HttpPost(
				"http://10.12.6.36:3000/user_sessions?user_session[email]="
						+ email + "&user_session[password]=" + password);
		try {
			HttpResponse response = httpClient.execute(httpRequest,
					localContext);
			Log.d("ReviewsSyncService",
					"response " + parseResponse(response.getEntity()));
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<Group> groups() {
		return new ResourceRepository<Group>().getResources("group",
				"http://10.12.6.36:3000/android/groups", Group.class);
	}

	public List<Contact> contacts(int groupId) {
		List<Contact> contacts = new ResourceRepository<Contact>().getResources("contact",
				"http://10.12.6.36:3000/android/groups/" + groupId + "/contacts",
				Contact.class);
		System.out.println(contacts);
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
				StringBuilder finalJsonString = parseResponse(responseEntity);
				JSONArray jsonArray = new JSONArray(finalJsonString.toString());
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

	private StringBuilder parseResponse(HttpEntity responseEntity)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(responseEntity.getContent()));
		StringBuilder finalJsonString = new StringBuilder();
		String eachLine;
		while ((eachLine = bufferedReader.readLine()) != null) {
			finalJsonString.append(eachLine);
		}
		return finalJsonString;
	}
}
