package net.erickelly.score;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlayerArrayAdapter extends ArrayAdapter {

	private final Activity context;
	private final String[] names;

	public PlayerArrayAdapter(Activity context, String[] names) {
		super(context, R.layout.list, names);
		this.context = context;
		this.names = names;
	}

	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		public TextView player_name;
		public TextView player_score;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder will buffer the assess to the individual fields of the row
		// layout

		ViewHolder holder;
		// Recycle existing view if passed as parameter
		// This will save memory and time on Android
		// This only works if the base layout for all classes are the same
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.list, null, true);
			holder = new ViewHolder();
			holder.player_name = (TextView) rowView.findViewById(R.id.p_name);
			holder.player_score = (TextView) rowView.findViewById(R.id.p_score);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.player_name.setText(names[position]);

		return rowView;
	}

}
