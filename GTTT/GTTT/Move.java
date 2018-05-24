package GTTT;

public class Move {
	public int teamId;
	public int gameId;
	public int moveId;
	public Position move;
	
	public Move(int teamId,int gameId, int moveId,Position move) {
		this.teamId=teamId;
		this.gameId=gameId;
		this.moveId=moveId;
		this.move=move;
	}
	
	public Move() {
		
	}
	
	public void setPosition(String positionString) throws Exception {
		String[] s=positionString.split(",");
		try {
			Position p=new Position(Integer.parseInt(s[0]),Integer.parseInt(s[1]));
			this.move=p;
		}catch(Exception e) {
			throw e;
		}
	}
}
