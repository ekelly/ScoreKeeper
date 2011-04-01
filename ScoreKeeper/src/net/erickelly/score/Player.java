package net.erickelly.score;

import android.util.Log;

public class Player {
	
	final static String separater = "##";
	// static Integer player_num;  somehow add this to help with naming players
	
	String name;
	Integer score;
	
	public Player(String name, Integer score) {
		this.name = name;
		this.score = score;
	}
	
	public String serialize() {
		String s = name + separater + score.toString();
		Log.d("serialize error", s);
		return s;
	}
	
	public static Player unpack(String s) {
		
		String a[] = s.split(separater);
		Log.d("Split length", ((Integer)a.length).toString());
		Log.d("array length",((Integer)a.length).toString());
		Integer i = Integer.parseInt(a[1]);
		return new Player(a[0],i);
	}
	
	public void updateScore(int i) {
		score = score + i;
	}

}
