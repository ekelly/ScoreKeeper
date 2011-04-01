package net.erickelly.score;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class PlayerList extends ListActivity {
	
	Integer defplayer;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Log.d("PlayerList", "got this far");
		defplayer = getIntent().getExtras().getInt("playernum");
		Log.d("defplayer", defplayer.toString());
		Log.d("playerList size", ((Integer) Score.players.size()).toString());
		this.setListAdapter(new PlayerArrayAdapter(this, Score.players));
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
