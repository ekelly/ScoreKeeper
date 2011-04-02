package net.erickelly.score;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayerArrayAdapter extends ArrayAdapter<Player> implements OnClickListener {

	private ArrayList<Player> playerList;
	private Activity context;

	public PlayerArrayAdapter(Activity context, ArrayList<Player> playerList) {
		super(context, R.layout.list, playerList);
		Log.d("playerList size", ((Integer) playerList.size()).toString());
		this.context = context;
		Log.d("Got this far","");
		this.playerList = playerList;
	}

	// static to save the reference to the outer class and to avoid access to
	// any members of the containing class
	static class ViewHolder {
		public TextView player_name;
		public TextView player_score;
		public Button plus;
		public Button min;
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
			holder.min = (Button) rowView.findViewById(R.id.min);
			holder.min.setOnClickListener(this);
			holder.min.setTag(position);
			holder.plus = (Button) rowView.findViewById(R.id.plus);
			holder.plus.setOnClickListener(this);
			holder.plus.setTag(position);
			rowView.setTag(holder);
			rowView.setOnClickListener(new OnItemClickListener(position));
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.player_name.setText(playerList.get(position).name + ":");
		holder.player_score.setText(playerList.get(position).score.toString());

		return rowView;
	}
	
	@Override
	public void onClick(View v) {
		Log.d("ListView", "Button clicked");
		Integer p = (Integer) v.getTag();
		Log.d("Button tag", p.toString());
		RelativeLayout parent = (RelativeLayout) v.getParent();
		switch(v.getId()) {
		case R.id.min:
			playerList.get(p).updateScore(-1 * Score.set1);
			Log.d("Player's new score",playerList.get(p).score.toString());
			break;
		case R.id.plus:
			playerList.get(p).updateScore(Score.set1);
			Log.d("Player's new score",playerList.get(p).score.toString());
			break;
		}
		Log.d("onClick", "Trying to set score");
		((TextView) parent.getChildAt(1)).setText(playerList.get(p).score.toString());
		Log.d("onClick", "Score set!");
	}
}