package net.erickelly.score;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PlayerCursorAdapter extends SimpleCursorAdapter implements OnClickListener {

	private Context context;
	public AlertDialog.Builder alert;
	EditText input;
	private DatabaseHelper dbHelper;

	public PlayerCursorAdapter(Context context, int layout, 
			Cursor c, String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context = context;
		//this.dbHelper = new DatabaseHelper(context);
	}
	
	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		public TextView player_name;
		public TextView player_score;
		public Button plus;
		public Button min;
	}
/*
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
			holder.min = (Button) rowView.findViewById(R.id.min);
			holder.min.setOnClickListener(this);
			holder.min.setTag(position);
			holder.plus = (Button) rowView.findViewById(R.id.plus);
			holder.plus.setOnClickListener(this);
			holder.plus.setTag(position);
			rowView.setTag(holder);
			rowView.setOnClickListener(new OnItemClickListener(position));
			rowView.setOnCreateContextMenuListener(new OnItemClickListener(position));
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.player_name.setText(playerList.get(position).name + ":");
		holder.player_score.setText(playerList.get(position).score.toString());

		return rowView;
	}
*/
	@Override
	public void onClick(View v) {
		Log.d("ListView", "Button clicked");
		Integer p = (Integer) v.getTag();
		Log.d("Button tag", p.toString());
		RelativeLayout parent = (RelativeLayout) v.getParent();
		switch(v.getId()) {
		case R.id.min:
//			playerList.get(p).updateScore(-1 * Score.set1);
			break;
		case R.id.plus:
//			playerList.get(p).updateScore(Score.set1);
			break;
		}
		Log.d("onClick", "Trying to set score");
		//((TextView) parent.getChildAt(1)).setText(playerList.get(p).score.toString());
		Log.d("onClick", "Score set!");
	}


}
