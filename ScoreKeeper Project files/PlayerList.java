package net.erickelly.score;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PlayerList extends ListActivity {
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setListAdapter(new ArrayAdapter<Player>(this,
				R.layout.list, Score.players));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Player p = (Player) this.getListAdapter().getItem(position);
		
//		p.updateScore();
		
		String keyword = p.name;
		Toast.makeText(this, "You selected: " + keyword, Toast.LENGTH_LONG)
				.show();
	}
}
