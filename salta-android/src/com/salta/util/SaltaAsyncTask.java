package com.salta.util;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SaltaAsyncTask extends AsyncTask<String, Integer, JSONArray> {
	private Context parent;
	private ProgressDialog progressDialog;

	public SaltaAsyncTask(Context parent) {
		super();
		this.parent = parent;
	}

	@Override
	protected JSONArray doInBackground(String... params) {
		return null;
	}

	@Override
	protected void onPostExecute(JSONArray result) {
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(parent);
		progressDialog.setMessage("Processing your request");
		progressDialog.show();
	}
}

