public class BattleField {
	
	private int BF_WIDTH = 576;
	private int BF_HEIGHT = 576;
	
	String[][] battleField = { { "B", "B", "B", "B", " ", "B", "B", "B", "B" },
			{ "B", "B", "B", " ", " ", " ", "B", "B", "B" },
			{ "B", "B", " ", " ", " ", " ", " ", "B", "B" },
			{ "B", " ", " ", " ", " ", " ", " ", " ", "B" },
			{ " ", " ", "B", "B", "B", "B", "B", " ", " " },
			{ " ", " ", "B", "B", "B", "B", "B", " ", " " },
			{ " ", " ", " ", "B", "B", "B", " ", " ", " " },
			{ "B", " ", " ", " ", " ", " ", " ", " ", "B" },
			{ "B", "B", "B", " ", " ", " ", "B", "B", "B" },
			
			

	};
	
	public BattleField () {
		
	}
	
    public BattleField (String [][] battleField) {
    	this.battleField = battleField;		
	}
	
	public int getBF_WIDTH() {		
		return BF_WIDTH;
	}

	public int getBF_HEIGHT() {
		return BF_HEIGHT;
	}
	
	public String scanQuadrant (int v, int h) {
		return battleField[v][h];
	}
	
	public void updateQuadrant (int v, int h, String object) { 
		battleField[v][h]=object;
		
	}

	public int getDimensionX () {
		return battleField[0].length;
	}
	
	public int getDimensionY () {
		return battleField.length;
	}



}
