package net.erickelly.score;


public class Player {
	
	final static String separator = "##";
	// static Integer player_num;  somehow add this to help with naming players
	
	String name;
	Integer score;
	// for database lookups
	Integer id;
	
	public Player(String name, Integer score, Integer id) {
		this.name = name;
		this.score = score;
		this.id = id;
	}
	
//	public String serialize() {
//		String s = name + separator + score.toString();
//		return s;
//	}
//	
//	public static Player unpack(String s) {
//		
//		String a[] = s.split(separator);
//		Integer i = Integer.parseInt(a[1]);
//		return new Player(a[0],i);
//	}
	
	public void updateScore(int i) {
		score = score + i;
	}

}
