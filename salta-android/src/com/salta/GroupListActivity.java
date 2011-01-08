package com.salta;

import java.util.List;

import android.app.ListActivity;
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
import android.widget.TextView;

import com.salta.core.Group;
import com.salta.web.SaltaClient;

public class GroupListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final List<Group> groups = SaltaClient.client().groups();
		ArrayAdapter<Group> groupListAdapter = new ArrayAdapter<Group>(this,
				R.layout.group_item, groups) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = ((LayoutInflater) getContext()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
							.inflate(R.layout.group_item, null);
				}
				((TextView) convertView)
						.setText(groups.get(position).getName());
				return convertView;
			}
		};
		setListAdapter(groupListAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(),
						ContactListActivity.class);
				intent.putExtra("group_id", groups.get(position).getId());
				startActivity(intent);
			}
		});
	}
}
