package com.salta;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.salta.core.Contact;
import com.salta.service.ServiceReference;

public class ContactListActivity extends SaltaBaseListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int groupId = getIntent().getExtras().getInt("group_id");
		Log.d("ContactList", "Displaying contacts for " + groupId);
		final List<Contact> contacts = ServiceReference.service().contacts(
				groupId);
		ArrayAdapter<Contact> contactListAdapter = new ArrayAdapter<Contact>(
				this, R.layout.contact_item, contacts) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = ((LayoutInflater) getContext()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
							.inflate(R.layout.contact_item, null);
				}
				((TextView) ((RelativeLayout) convertView)
						.findViewById(R.id.contactNameView)).setText(contacts
						.get(position).getFirstName());
				((TextView) ((RelativeLayout) convertView)
						.findViewById(R.id.contactNumberView)).setText(contacts
						.get(position).getPhone());
				return convertView;
			}
		};
		setListAdapter(contactListAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						ContactActivity.class);
				intent.putExtra("contact_id", contacts.get(position).getId());
				startActivity(intent);
			}
		});
	}
}
