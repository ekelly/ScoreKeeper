package net.erickelly.score;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;

public class OnItemClickListener extends Activity implements OnClickListener, OnCreateContextMenuListener {
	
	private Integer rPosition;
	
	OnItemClickListener(int position) {
		rPosition = position;
		Log.d("OnItemClickListener", "Engaged with position " + rPosition.toString());
	}
	
	@Override
	public void onClick(View arg0) {
		Log.d("OnItemClickListener", "Activated with position " + ((Integer) rPosition).toString());
		Intent intent = new Intent(arg0.getContext(), Score.class);
		intent.putExtra("playerid", rPosition);
		Log.d("Row", "Clicked");
		((Activity) arg0.getContext()).setResult(RESULT_OK, intent);
		((Activity) arg0.getContext()).finish();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Player Actions");
		menu.add("Set Name");
		menu.add("Set Score");
		menu.add("Remove Player");
		if(Score.players.size() == 1) {
			menu.getItem(2).setEnabled(false);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
//		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item 
//		.getMenuInfo(); 
		Log.d("OnContext", "called");
		/* Switch on the ID of the item, to get what the user selected. */ 
		switch (item.getItemId()) { 
		case 0:
			return true;
		case 1:
			return true;
		case 2:  
			/* Remove it from the list. */ 
			Score.players.remove(rPosition);
			/* Update the List */
			refreshPlayers();
			return true;
		} 
		return false; 
	}
	
	private void refreshPlayers() { 
		ListActivity la = (ListActivity) this.getParent().getParent();
		la.setListAdapter(new PlayerArrayAdapter(la, Score.players)); 
	}
}
