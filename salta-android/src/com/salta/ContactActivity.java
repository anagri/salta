package com.salta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Intents.Insert;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.salta.core.Contact;
import com.salta.service.ServiceReference;

public class ContactActivity extends Activity {
	private Contact contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		int contactId = getIntent().getExtras().getInt("contact_id");
		contact = ServiceReference.service().contact(contactId);
		((TextView) findViewById(R.id.contactNameView)).setText(contact.name());
		((TextView) findViewById(R.id.contactEmailView)).setText(contact
				.getEmail());
		((TextView) findViewById(R.id.contactPhoneView)).setText(contact
				.getPhone());
		((TextView) findViewById(R.id.contactWebsiteView)).setText(contact
				.getWebsite());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contact_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addToContacts:
			Intent in = new Intent(Intent.ACTION_INSERT,
					ContactsContract.Contacts.CONTENT_URI);
			in.putExtra(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.getFirstName());
			in.putExtra(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contact.getLastName());
			in.putExtra(Insert.PHONE, contact.getPhone());
			in.putExtra(Insert.EMAIL, contact.getEmail());
			startActivity(in);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
