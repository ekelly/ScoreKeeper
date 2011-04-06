package net.erickelly.score;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class PlayerList extends ListActivity {
	
	Integer defplayer;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		defplayer = getIntent().getExtras().getInt("playernum");
		this.setListAdapter(new PlayerArrayAdapter(this, Score.players));
		registerForContextMenu(getListView());
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Intent intent = new Intent();
	    	intent.putExtra("playerid", defplayer);
	    	setResult(RESULT_OK, intent);
	    	finish();
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
