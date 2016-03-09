package fieldObjects;


import movelableObjects.*;

import java.awt.*;


public class BattleField {
	
	private static final int BF_WIDTH = 576;
	private static final int BF_HEIGHT = 576;
	private boolean COLORDED_MODE = false;
	private Tank agressor;
	private Tank defender;
	private AbstractBFObject eagle;

	public String[][] battleField = {
			{ " ", "B", "B", " ", " ", " ", "B", "B", "W" },
			{ "B", "B", "B", " ", "B", " ", "B", "B", "W" },
			{ "B", "B", " ", "W", " ", " ", " ", "B", "W" },
			{ "B", "B", " ", "W", "B", " ", " ", " ", " " },
			{ " ", " ", " ", " ", "B", "B", " ", " ", " " },
			{ "R", "R", "R", " ", "R", "R", "R", " ", "R" },
			{ "B", " ", " ", "B", "W", "W", " ", " ", "R" },
			{ "B", "R", " ", " ", " ", " ", " ", " ", "B" },
			{ " ", "B", "B", " ", "E", " ", " ", "B", "W" },

	};


	public AbstractBFObject [][] fieldObjects = new AbstractBFObject[battleField[0].length][battleField.length];

	private void fillGameField ()  {
		for (int j = 0; j < getDimensionY(); j++) {
			for (int k = 0; k < getDimensionX(); k++) {
					fieldObjects[j][k] = choiseObject(j,k);
			}
		}
	}
	

	public BattleField (int agressorId) {

		fillGameField();
		agressor = setAgressor(agressorId);
		defender = new T34(this, 192, 448, Direction.UP);
		eagle = scanQuadrant(8,4);
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

	public AbstractBFObject getEagle() {
		return eagle;
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

	private AbstractBFObject choiseObject (int x, int y)  {
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

	public Tank getAgressor() {
		return agressor;
	}

	public Tank getDefender() {
		return defender;
	}

	private void drawObjects (Graphics g) {
		for (int j = 0; j < getDimensionY(); j++) {
			for (int k = 0; k < getDimensionX(); k++) {
				fieldObjects[j][k].draw(g);
			}
		}
	}
	public Tank setAgressor (int i) {
		if (i==0) {
			return new BT7(this);
		}
		else if (i==1) {
			return new Tiger(this);
		}
		else {
			return new T34(this);
		}
	}
}
