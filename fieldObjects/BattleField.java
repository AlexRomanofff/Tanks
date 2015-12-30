package fieldObjects;


import java.awt.*;

public class BattleField {
	
	private final int BF_WIDTH = 576;
	private final int BF_HEIGHT = 576;
	private boolean COLORDED_MODE = false;

	public String[][] battleField = {
			{ "B", "B", "B", " ", "R", " ", "B", "B", "W" },
			{ "B", "B", "B", "W", "R", " ", "B", "B", "W" },
			{ "B", "B", " ", "W", " ", " ", " ", "B", "W" },
			{ "B", " ", " ", "W", " ", " ", " ", " ", " " },
			{ " ", " ", "B", "B", "B", "B", "R", " ", " " },
			{ "R", " ", "R", "R", "R", "R", "", " ", "R" },
			{ "R", " ", " ", "W", "W", "W", " ", " ", "R" },
			{ "B", "R", " ", " ", " ", " ", " ", " ", "B" },
			{ "B", "B", "B", "B", "E", " ", "B", "B", "W" },

	};
	public AbstractBFObject [][] fieldObjects = new AbstractBFObject[battleField[0].length][battleField.length];

	private void fillGameField () throws Exception  {
		for (int j = 0; j < getDimensionY(); j++) {
			for (int k = 0; k < getDimensionX(); k++) {
					fieldObjects[j][k] = choiseObject(j,k);
			}
		}
	}
	
	public BattleField () throws Exception  {
		fillGameField();
	}

	public int getBF_WIDTH() {		
		return BF_WIDTH;
	}

	public int getBF_HEIGHT() {
		return BF_HEIGHT;
	}
	
	public AbstractBFObject scanQuadrant (int v, int h) {
		return fieldObjects[v][h];
	}
	private String scanQuadrantBF (int v, int h) {
		return battleField[v][h];
	}
	public void updateQuadrant (int v, int h, AbstractBFObject object) {
		fieldObjects[v][h]=object;
		
	}
	public String getQuadrantXY(int v, int h) {

		return (v - 1) * 64 + "_" + (h - 1) * 64;
	}
	public String getQuadrant(int x, int y) {

		return (y / 64) + "_" + (x / 64);
	}
	public int getDimensionX () {
		return battleField[0].length;
	}
	
	public int getDimensionY () {
		return battleField.length;
	}

	public void draw(Graphics g) {

		drawWindow(g);
        drawObjects(g);
	}

	private void drawWindow(Graphics g) {
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
	}

	private AbstractBFObject choiseObject (int x, int y)throws Exception  {
		AbstractBFObject obj;
		if (scanQuadrantBF(x, y).equals("B")) {
			obj = new Brick(y*64, x*64);
		} else if (scanQuadrantBF(x, y).equals("R")) {
			obj = new Rock(y*64, x*64);
		} else if (scanQuadrantBF(x, y).equals("W")) {
			obj = new Water(y*64, x*64);
		} else if (scanQuadrantBF(x, y).equals("E")) {
			obj = new Eagle(y*64, x*64);
		}else {
			obj = new Empty(y * 64, x * 64);
		}
	return obj;
	}

	private void drawObjects (Graphics g) {
		for (int j = 0; j < getDimensionY(); j++) {
			for (int k = 0; k < getDimensionX(); k++) {
				fieldObjects[j][k].draw(g);
			}
		}
	}
}
