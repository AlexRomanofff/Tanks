import java.awt.*;

public class BattleField {
	
	private int BF_WIDTH = 576;
	private int BF_HEIGHT = 576;
	final boolean COLORDED_MODE = true;

	
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
	public String getQuadrantXY(int v, int h) {

		return (v - 1) * 64 + "_" + (h - 1) * 64;
	}

	public int getDimensionX () {
		return battleField[0].length;
	}
	
	public int getDimensionY () {
		return battleField.length;
	}

    public void draw (Graphics g) {
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
				if (scanQuadrant(j, k).equals("B")) {
					String coordinates = getQuadrantXY(j+1, k+1);
					int separator = coordinates.indexOf("_");
					int y = Integer.parseInt(coordinates
							.substring(0, separator));
					int x = Integer.parseInt(coordinates
							.substring(separator + 1));
					g.setColor(new Color(0, 0, 255));
					g.fillRect(x, y, 64, 64);
				}
			}
		}

	}

}
