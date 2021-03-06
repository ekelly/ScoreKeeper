package net.erickelly.score;

import net.erickelly.score.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import static net.erickelly.score.Constants.NAME;
import static net.erickelly.score.Constants.SCORE;
import static net.erickelly.score.Constants.TABLE_NAME;
import static android.provider.BaseColumns._ID;

public class Score extends Activity implements OnClickListener, OnLongClickListener {
    /** Called when the activity is first created. */

	// Reference variables
	TextView current_score;
	TextView player;
	AlertDialog.Builder alert;
	EditText input;
	static SharedPreferences pref;
	Resources res;
	Drawable shape;
	RelativeLayout ll;
	static String background;
	static String separator = "<>";
	Handler handler;
	private DatabaseHelper dbHelper;
	
	// Button Reference Variables
	// Names do not refer to actual values, but their positions
	Button plus1;
	Button plus2;
	Button plus3;
	Button min1;
	Button min2;
	Button min3;
		
	// Number Sets
	static Integer set1;
	static Integer set2;
	static Integer set3;
	
	// Player variables
	static Integer id;
	static String name;
	static Integer score;
	static SQLiteDatabase db;
	
	// Helpful constants
	final String [] columns = new String [] { NAME, SCORE };

		
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	ll = (RelativeLayout) findViewById(R.id.window);
        
        pref = getSharedPreferences("preferences",MODE_WORLD_WRITEABLE);
        res = getResources();
        
        // Set the on screen handlers
        current_score = (TextView) findViewById(R.id.score);
        player = (TextView) findViewById(R.id.player);
        
        // Set up click listeners for all the buttons
        plus1 = (Button) findViewById(R.id.plus1);
        plus1.setOnClickListener(this);
        plus2 = (Button) findViewById(R.id.plus2);
        plus2.setOnClickListener(this);
        plus3 = (Button) findViewById(R.id.plus3);
        plus3.setOnClickListener(this);
        min1 = (Button) findViewById(R.id.min1);
        min1.setOnClickListener(this);
        min2 = (Button) findViewById(R.id.min2);
        min2.setOnClickListener(this);
        min3 = (Button) findViewById(R.id.min3);
        min3.setOnClickListener(this);
        
        // Long click listeners
        current_score.setOnLongClickListener(this);
        player.setOnLongClickListener(this);
        plus1.setOnLongClickListener(this);
        plus2.setOnLongClickListener(this);
        plus3.setOnLongClickListener(this);
        min1.setOnLongClickListener(this);
        min2.setOnLongClickListener(this);
        min3.setOnLongClickListener(this);
        		
		handler = new Handler();
    	
        dbHelper = new DatabaseHelper(this);
    	restorePlayers();
		
    }
    
    private void restorePlayers() {
    	
    	SharedPreferences prefs = getPreferences(MODE_PRIVATE);
    	id = prefs.getInt("current_player", 1);
    	Log.d("id = ", id.toString());
    	
//    	String ORDER_BY = _ID + " ASC";
//    	// Perform a managed query. The Activity will handle closing 
//    	// and re-querying the cursor when needed. 
//    	try {
//    		SQLiteDatabase db = dbHelper.getReadableDatabase();
//        	Cursor cursor = db.query(TABLE_NAME, columns, _ID + "=?", 
//        			new String [] { String.valueOf(id) } , null, null, ORDER_BY);
//        	startManagingCursor(cursor);
//        	
////        	if(numPlayers() != 0) {
////        		while(cursor.moveToNext()) {
////        			id = cursor.getInt(0);
////        			name = cursor.getString(1);
////        			score = cursor.getInt(2);
//////        			players.add(new Player(name,score,id));
////        		} 
////        	}
//        	
//        	
//        } finally { 
//       		dbHelper.close();
//       		getPlayer(id);
//       	}
    	getPlayer(id);
   	}
    
//    private void savePlayers() {    	
//    	SQLiteDatabase db = dbHelper.getWritableDatabase();
//    	db.up
////    	for(int i = 0; i < numPlayers(); i++) {
////    		ContentValues values = new ContentValues();
////    		values.put(NAME, players.get(i).name);
////    		values.put(SCORE, players.get(i).score);
////    		db.insertOrThrow(TABLE_NAME, null, values);
////    	}
//    	db.close();
//	}
    
//    public void savePlayer() {
//		// Save current stuff
//		players.get(id).name = name;
//		players.get(id).score = score;
//	}
    
    public void addPlayer(String name) {
    	Log.d("addPlayer","adding player");
    	Log.d("Current num of players:",numPlayers().toString());
    	SQLiteDatabase db = dbHelper.getWritableDatabase();
    	ContentValues cv = new ContentValues();
    	cv.put(NAME, name); //+ Integer.parseInt(db.rawQuery("SELECT MAX(" + _ID + ") FROM " + TABLE_NAME, null).getString(0)));
    	cv.put(SCORE, 0);
    	try {
    		db.insertOrThrow(TABLE_NAME, null, cv);
    	} catch(Exception e) {
    		Log.d("Couldn't insert", e.toString());
    	}
    	db.close();
    	getPlayer(lastPlayer());

//    	// Get last record
////    	Cursor c = db.rawQuery("SELECT "+NAME+","+SCORE+" WHERE "+_ID
////    			+" = (SELECT MAX("+_ID+") FROM "+TABLE_NAME+");", null);
//    	Cursor c = db.rawQuery("SELECT ?, ? WHERE ? = (SELECT MAX(?) FROM ?);", 
//    				new String [] { NAME, SCORE, _ID, _ID, TABLE_NAME });
//    	startManagingCursor(c);
//    	db.close();
//  		name = c.getString(0);
//  		score = c.getInt(1);
//		
//		// display stuff
//		current_score.setText(score.toString());
//		player.setText(name);
	}
    
    public Integer lastPlayer() {
    	
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
    	Cursor c = db.rawQuery("SELECT MAX("+_ID+") FROM "+TABLE_NAME+";", null);
    	startManagingCursor(c);
    	if(c.moveToFirst()) {
    		Log.d("LastPlayer:",((Integer) c.getInt(0)).toString());
    		int count = c.getInt(0);
    		db.close();
    		return count;
    	}
    	db.close();
    	return -1;
    }
    
    private void restoreBackground() {
    	background = pref.getString("background", "black");
    	if(background.equals("blue")) {
    		shape = res.getDrawable(R.drawable.bgradient);
    	} else if(background.equals("green")) {
    		shape = res.getDrawable(R.drawable.ggradient);
    	} else if(background.equals("red")) {
    		shape = res.getDrawable(R.drawable.rgradient);
    	} else {
    		shape = res.getDrawable(R.drawable.nogradient);
    	}
    	shape.setDither(true);
    	ll.setBackgroundDrawable(shape);
    }
    
    private void switchBackground() {
    	if(background.equals("blue")) {
    		shape = res.getDrawable(R.drawable.ggradient);
    		background = "green";
    		Log.d("background = ", "blue");
    	} else if(background.equals("green")) {
    		shape = res.getDrawable(R.drawable.rgradient);
    		background = "red";
    		Log.d("background = ", "green");
    	} else if(background.equals("red")) {
    		shape = res.getDrawable(R.drawable.nogradient);
    		background = "black";
    		Log.d("background = ", "red");
    	} else {
    		shape = res.getDrawable(R.drawable.bgradient);
    		background = "blue";
    		Log.d("background = ", "black");
    	}
    	ll.setBackgroundDrawable(shape);		
	}

    public void updateScore(int i) {
		//players.get(id).updateScore(i);
		score = score + i;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(SCORE, score);
		db.update(TABLE_NAME, cv, _ID+"=?", new String []{String.valueOf(id)}); 
		db.close();
		current_score.setText((CharSequence) score.toString());
	}
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	Bundle extras = getIntent().getExtras();
    	
    	if(extras != null) {
    		id = extras.getInt("playerid");
    	} else {
        	id = pref.getInt("current_player", 1);
    	}
    	
    	// Get stored value of items
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
    	//Cursor c = db.query(TABLE_NAME, columns, _ID + "=?", new String [] { String.valueOf(id) }, null, null, null);
    	Cursor c = db.rawQuery("SELECT "+NAME+","+SCORE+" FROM "+TABLE_NAME+" WHERE "+_ID+"= "+String.valueOf(id), null);
    	startManagingCursor(c);
    	if(c.moveToFirst()) {
	    	name = c.getString(0);
	    	score = c.getInt(1);
    	} else if(numPlayers() < 1) {
    		Log.d("Num Players", "Thinks there's less than 1");
    		addPlayer("Player 1");
    	} else {
    		getPlayer(firstPlayer());
    	}
    	db.close();
    	
    	//score = players.get(id).score;
        //name = players.get(id).name;
    	restoreBackground();
        setSets();
        
        // display the variables
        player.setText(name);
    	current_score.setText(score.toString());
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	save();
    }
    
    @Override
    public void onClick(View v) {
    	switch(v.getId()) {
	    	case R.id.min1:
	    		updateScore((set1 * -1));
	    		break;
	    	case R.id.min2:
	    		updateScore((set2 * -1));
	    		break;
	    	case R.id.min3:
	    		updateScore((set3 * -1));
	    		break;
	    	case R.id.plus1:
	    		updateScore(set1);
	    		break;
	    	case R.id.plus2:
	    		updateScore(set2);
	    		break;
	    	case R.id.plus3:
	    		updateScore(set3);
	    		break;
    	}
    }

    @Override
    public boolean onLongClick(View v) {
    	switch(v.getId()) {
    		case R.id.score:
	    		createDialog("0", findViewById(R.id.score));
    			break;
    		case R.id.player:
    			createDialog(name, findViewById(R.id.player));
    			break;
    		case R.id.min1:
       			createDialog("1", min1);
       			break;
    		case R.id.min2:
       			createDialog("5", min2);
       			break;
    		case R.id.min3:
       			createDialog("10", min3);
       			break;
    		case R.id.plus1:
       			createDialog("1", plus1);
       			break;
    		case R.id.plus2:
       			createDialog("5", plus2);
       			break;
    		case R.id.plus3:
       			createDialog("10", plus3);
       			break;
    	}
    	
    	return true;
    }
    
    public void createDialog(String startText, final View v) {
    	
    	// Setup dialog
        alert = new AlertDialog.Builder(this);
		input = new EditText(this);
		input.setMinWidth(250);
		if(v.getId() != R.id.player) {
			input.setInputType(InputType.TYPE_CLASS_PHONE);
		}
		input.setText(startText);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String result = input.getText().toString().trim();

				switch(v.getId()) {
					case R.id.score:
						setScore(result);
						break;
					case R.id.player:
						setPlayer(result);
						break;
					case R.id.min1:
						setButton(result, plus1, min1, 1);
						break;
					case R.id.min2:
						setButton(result, plus2, min2, 2);
						break;
					case R.id.min3:
						setButton(result, plus3, min3, 3);
						break;
					case R.id.plus1:
						setButton(result, plus1, min1, 1);
						break;
					case R.id.plus2:
						setButton(result, plus2, min2, 2);
						break;
					case R.id.plus3:
						setButton(result, plus3, min3, 3);
						break;
				}
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();}
				});
    			
    	alert.show();
    	
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	if(numPlayers() <= 1) {
     	   	menu.findItem(R.id.next).setEnabled(false);
     	   	menu.findItem(R.id.prev).setEnabled(false);
     	   	menu.findItem(R.id.sub_player).setEnabled(false);
        } else {
        	menu.findItem(R.id.next).setEnabled(true);
      	   	menu.findItem(R.id.prev).setEnabled(true);
      	   	menu.findItem(R.id.sub_player).setEnabled(true);
        }
    	menu.findItem(R.id.view_all).setEnabled(false);
    	return true;
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);       
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.set_backgound:
    			switchBackground();
    			break;
    		/*case R.id.view_all:
    			Intent intent = new Intent(this, PlayerList.class);
    			intent.putExtra("playernum", id);
    			startActivityForResult(intent, 0);
    			break;*/
    		case R.id.add_player:
    			Integer i = (int) numPlayers();
    			i++;
    			addPlayer("Player " + i.toString());
    			break;
    		case R.id.sub_player:
    			subPlayer();
    			break;
    		case R.id.next:
    			nextPlayer();
    			break;
    		case R.id.prev:
    			prevPlayer();
    			break;
    	}
    	   return true;
    }
    
    private Integer numPlayers() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor c = dbHelper.getReadableDatabase().rawQuery(sql, null);
        startManagingCursor(c);
        c.moveToFirst();
        Integer count = c.getInt(0);
        Log.d("numPlayers()", count.toString());
        return count;
    }

	private void subPlayer() {
		if(!(numPlayers() < 2)) {
			
	    	// Get stored value of items
	    	SQLiteDatabase db = dbHelper.getWritableDatabase();
	    	String [] args = new String [] { String.valueOf(id) };
	    	db.delete(TABLE_NAME, _ID+"=?", args);
	    	db.close();
			
//			players.remove((int) id);
			if(!(id == 0)) {
				id = id - 1;
			} else {
				id = (int) (numPlayers() - 1);
			}
			getPlayer(id);
		}
	}
	
	public void prevPlayer() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
//		String [] columns = new String [] { _ID, NAME, SCORE };
		// c = db.query(TABLE_NAME, columns, "*", null, null, null, _ID);
		Cursor c = db.rawQuery("SELECT MAX("+_ID+") FROM "+ TABLE_NAME + " WHERE " + _ID + " > " + id.toString(), null);
    	startManagingCursor(c);
    	Integer p;
    	if (c.getCount() < 1) {
    		p = lastPlayer();
    		Log.d("prevPlayer","Loop around the back");
    	} else {
    		c.moveToFirst();
    		p = c.getInt(0);
    	}
    	Log.d("prevPlayer", p.toString());
		db.close();
		getPlayer(p);
	}

	public void nextPlayer() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT MIN("+_ID+") FROM "+ 
				TABLE_NAME + " WHERE " + _ID + " < " + id.toString(), null);
    	startManagingCursor(c);
    	Integer p;
    	if (c.getCount() < 1) {
    		p = firstPlayer();
    		Log.d("nextPlayer","Looping to the front");
    	} else {
    		c.moveToFirst();
    		p = c.getInt(0);
    	}
    	Log.d("nextPlayer",p.toString());
		db.close();
		getPlayer(p);
	}

	private Integer firstPlayer() {
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
    	Cursor c = db.rawQuery("SELECT MIN("+_ID+") FROM "+TABLE_NAME+";", null);
    	startManagingCursor(c);
    	if(c.moveToFirst()) {
    		Log.d("firstPlayer:",((Integer) c.getInt(0)).toString());
    		int count = c.getInt(0);
    		db.close();
    		return count;
    	}
    	db.close();
    	return -1;
	}

	public void getPlayer(int player_id) {

		// Get stored value of items
    	SQLiteDatabase db = dbHelper.getReadableDatabase();
    	Cursor c = db.rawQuery("SELECT "+NAME+","+SCORE+","+_ID+" FROM "+TABLE_NAME+" WHERE "+_ID+"="+String.valueOf(player_id), null);
    	Log.d("# of rows = ", ((Integer) c.getCount()).toString());
    	startManagingCursor(c);
    	if(c.moveToFirst()) {
    		name = c.getString(0);
    		score = c.getInt(1);
    		id = c.getInt(2);
    	} else {
    		//addPlayer("Player 1");
    		//name = "Player 1";
    		//score = 0;
    		Log.d("No player found","");
    	}
    	
    	db.close();
    	
		// display stuff
    	player.setText(name);
		current_score.setText(score.toString());
	}

	public void setScore(String s) {
    	if(s.equalsIgnoreCase("")) {
    		Toast toast = Toast.makeText(getApplicationContext(), "Empty values are not allowed.",Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.BOTTOM, 0, 100);
			toast.show();
			createDialog(score.toString(),findViewById(R.id.score));
    	} else {
    		try {
    			int value = Integer.parseInt(s);
    			score = value;
    			current_score.setText((CharSequence) score.toString());
    			
    			// Write the value to the database
    			SQLiteDatabase db = dbHelper.getWritableDatabase();
    			ContentValues cv = new ContentValues();
    			cv.put(SCORE, value);
    			db.update(TABLE_NAME, cv, 
    					_ID + "=?", new String [] { String.valueOf(value) } );
    			db.close();
    			
    		} catch(NumberFormatException nfe) {
    			Log.d("setScore","Could not parse " + nfe);
    			Toast toast = Toast.makeText(getApplicationContext(), "Must be an integer.",Toast.LENGTH_SHORT);
    			toast.setGravity(Gravity.BOTTOM, 0, 100);
    			toast.show();
    			createDialog(score.toString(),findViewById(R.id.score));
    		} catch(Exception e) {
    			Log.d("setScore", "Dataabase troubles" + e);
    		}
    	}
    }
    
    public void setButton(String s, Button pos, Button neg, Integer set) {
    	try {
			int value = Integer.parseInt(s);
			if (!(value == 0)) {
				value = Math.abs(value);
				pos.setText("+" + ((Integer) value).toString());
				neg.setText("-" + ((Integer) value).toString());
	    		switch(set) {
	    		case 1:
	    			set1 = value;
	    			break;
	    		case 2:
	    			set2 = value;
	    			break;
	    		case 3:
	    			set3 = value;
	    			break;
	    		}
				pref.edit().putInt(set.toString(), value).commit();
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "0 is not allowed.",Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.BOTTOM, 0, 100);
				toast.show();
				View v;
				switch(set) {
	    		case 1:
	    			v = findViewById(R.id.min1);
	    			s = "1";
	    			break;
	    		case 2:
	    			v = findViewById(R.id.min2);
	    			s = "5";
	    			break;
	    		default:
	    			v = findViewById(R.id.min3);
	    			s = "10";
	    			break;
	    		}
				createDialog(s,v);
			}
		} catch(NumberFormatException nfe) {
			Log.d("setButton", "Could not parse " + nfe);
			Toast toast = Toast.makeText(getApplicationContext(), "Must be an integer.",Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.BOTTOM, 0, 100);
			toast.show();
			View v;
			switch(set) {
				case 1:
					v = findViewById(R.id.min1);
					break;
				case 2:
					v = findViewById(R.id.min2);
					break;
				default:
					v = findViewById(R.id.min3);
					break;
			}
			createDialog(s,v);
		}
    }
    
    public void setPlayer(String p) {
    	
    	if(!p.equals("")) {
    		player.setText((CharSequence) p);
        	name = p;
        	
        	// Write the value to the database
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();
			cv.put(NAME, p);
			db.update(TABLE_NAME, cv, 
					_ID + "=?", new String [] { String.valueOf(id) } );
			db.close();
        	
//        	players.get(id).name = p;
		} else {
			Toast toast = Toast.makeText(getApplicationContext(), "Empty names are not allowed.",Toast.LENGTH_LONG);
			toast.setGravity(Gravity.BOTTOM, 0, 100);
			toast.show();
			createDialog(p,findViewById(R.id.player));
		}
    }
    
    public void setSets() {
    	set1 = pref.getInt("set1", 1);
        set2 = pref.getInt("set2", 5);
        set3 = pref.getInt("set3", 10);
        plus1.setText("+" + set1.toString());
        plus2.setText("+" + set2.toString());
        plus3.setText("+" + set3.toString());
        min1.setText("-" + set1.toString());
        min2.setText("-" + set2.toString());
        min3.setText("-" + set3.toString());
    }
    
    public void save() {
    	    	
//    	savePlayers();
    	
    	Editor prefedit = pref.edit();
		prefedit.putInt("set1", set1);
		prefedit.putInt("set2", set2);
		prefedit.putInt("set3", set3);
		prefedit.putString("background",background);
//		if(id >= players.size()) {
//			id = players.size() - 1;
//		}
		prefedit.putInt("current_player", id);
		prefedit.commit();
		
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        final Integer i;
        if (extras != null) {
        	i = extras.getInt("playerid");
            Log.d("playerid", i.toString());
//            if (r >= players.size()) {	
//            	r = players.size() - 1;
//            	Log.d("r",r.toString());
//            }
//            
//            // Get the largest ID'd Player
//			SQLiteDatabase db = dbHelper.getReadableDatabase();
//			Cursor c = db.rawQuery("SELECT MAX("+_ID+") FROM "+ TABLE_NAME, null);
//			db.close();
        
        } else {
        	// Get the smallest ID'd player
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT MIN("+_ID+") FROM "+ TABLE_NAME, null);
			startManagingCursor(c);
			db.close();
			i = c.getInt(0);
		}
        
        if(resultCode==RESULT_OK) {            
            
        	handler.post(new Runnable() {
            	@Override
            	public void run() {
            		getPlayer(i);
            	}
            });
                      
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

}