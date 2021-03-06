package net.erickelly.score;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
}
