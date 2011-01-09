package com.salta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

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
		ArrayAdapter<Contact> contactListAdapter = new ContactListAdapter(this,
				R.layout.contact_item, contacts);
		setListAdapter(contactListAdapter);
		ListView listView = getListView();
		listView.setFastScrollEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						ContactActivity.class);
				intent.putExtra("contact_id", contacts.get(position).getId());
				startActivity(intent);
			}
		});
	}

	public class ContactListAdapter extends ArrayAdapter<Contact> implements
			SectionIndexer {

		private final List<Contact> contacts;
		private Map<String, Integer> alphaIndexer;
		private String[] sections;

		public ContactListAdapter(Context context, int textViewResourceId,
				List<Contact> contacts) {
			super(context, textViewResourceId, contacts);
			this.contacts = contacts;
			alphaIndexer = new HashMap<String, Integer>();
			int size = contacts.size();

			for (int x = 0; x < size; x++) {
				Contact contact = contacts.get(x);
				String ch = contact.getFirstName().substring(0, 1)
						.toUpperCase();
				;
				alphaIndexer.put(ch, x);
			}

			Set<String> sectionLetters = alphaIndexer.keySet();

			ArrayList<String> sectionList = new ArrayList<String>(
					sectionLetters);
			Collections.sort(sectionList);
			sections = new String[sectionList.size()];
			sectionList.toArray(sections);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = ((LayoutInflater) getContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE)).inflate(
						R.layout.contact_item, null);
			}
			((TextView) ((RelativeLayout) convertView)
					.findViewById(R.id.contactNameView)).setText(contacts.get(
					position).getFirstName());
			((TextView) ((RelativeLayout) convertView)
					.findViewById(R.id.contactNumberView)).setText(contacts
					.get(position).getPhone());
			return convertView;
		}

		public int getPositionForSection(int section) {
			return alphaIndexer.get(sections[section]);
		}

		public int getSectionForPosition(int position) {
			return 1;
		}

		public Object[] getSections() {
			return sections;
		}
	};
}
