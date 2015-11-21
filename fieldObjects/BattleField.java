package fieldObjects;


import java.awt.*;

public class BattleField {
	
	private int BF_WIDTH = 576;
	private int BF_HEIGHT = 576;
	private boolean COLORDED_MODE = true;


	
	public String[][] battleField = { { "B", "R", "B", "B", "R", "B", "B", "R", "B" },
			{ "B", "B", "B", " ", " ", " ", "B", "B", "B" },
			{ "B", "B", " ", " ", " ", " ", " ", "B", "B" },
			{ "B", " ", " ", " ", " ", " ", " ", " ", "B" },
			{ " ", " ", "B", "B", "B", "B", "B", " ", " " },
			{ " ", " ", "B", "B", "B", "B", "B", " ", " " },
			{ " ", " ", " ", "W", "W", "W", " ", " ", " " },
			{ "B", " ", " ", " ", " ", " ", " ", " ", "B" },
			{ "B", "B", "B", " ", " ", " ", "W", "B", "W" },
			
			

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
	public String getQuadrantXY(int v, int h) {

		return (v - 1) * 64 + "_" + (h - 1) * 64;
	}

	public int getDimensionX () {
		return battleField[0].length;
	}
	
	public int getDimensionY () {
		return battleField.length;
	}

	public void draw(Graphics g) {
		int i = 0;
		Color cc;
		for (int v = 0; v < 9; v++) {
			for (int h = 0; h < 9; h++) {
				if (COLORDED_MODE) {
					if (i % 2 == 0) {
						cc = new Color(252, 241, 177);
					} else {
						cc = new Color(233, 243, 255);
					}
				} else {
					cc = new Color(180, 180, 180);
				}
				i++;
				g.setColor(cc);
				g.fillRect(h * 64, v * 64, 64, 64);
			}
		}

		for (int j = 0; j < getDimensionY(); j++) {
			for (int k = 0; k < getDimensionX(); k++) {
				if (!scanQuadrant(j, k).equals(" ")) {
				choiseObject(j,k).draw(g);
				}
			}
		}
	}
	private AbstractBFObject choiseObject (int x, int y) {
		AbstractBFObject obj;
		if (scanQuadrant(x, y).equals("B")) {
			obj = new Brick(y*64, x*64);
		} else if (scanQuadrant(x, y).equals("R")) {
			obj = new Rock(y*64, x*64);
		} else if (scanQuadrant(x, y).equals("W")) {
			obj = new Water(y*64, x*64);
		} else {
			obj = new Eagle(y*64, x*64);
		}return obj;
	}
}
