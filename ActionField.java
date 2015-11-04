import javax.swing.*;
import java.awt.*;


public class ActionField extends JPanel {
	
	final boolean COLORDED_MODE = true;
	BattleField battleField = new BattleField();
	Bullet bullet = new Bullet(-100, -100, Direction.UP);
		BT7 bt7 = new BT7(this, battleField, 320, 512, Direction.UP);
	Tank tank = new Tank (this,battleField);
	Tank agressor = new Tank (this, battleField, Direction.UP);
	
	public void runTheGame() throws Exception {
		

		bt7.moveRandom();
//		bt7.turn(Direction.UP);
		bt7.fire();
		bt7.move();

	}
	
	private boolean processInterception() {
		
		String quadrant = getQuadrant(bullet.getX(), bullet.getY());
		int separator = quadrant.indexOf("_");
		int y = Integer.parseInt(quadrant.substring(0, separator));
		int x = Integer.parseInt(quadrant.substring(separator + 1));

		if ((y >= 0 && y < 9) && (x >= 0 && x < 9)) {

			if (battleField.scanQuadrant(y, x).equals(" ")) {
				return false;
			} else {
				battleField.updateQuadrant(y, x, " ");
          	}
		}
		return true;
	}
	
	public void processFire (Bullet bullet) throws Exception {
		this.bullet = bullet;
		int step = 1;

		while ((bullet.getX() >= -15 && bullet.getX() <= 575)
				&& (bullet.getY() >= -15 && bullet.getY() <= 575)) {

			step = getStepBullet(bullet, step);

			moveBullet(bullet, step);
			
			if (processInterception()) {
				bullet.destroy();
				repaint();
				Thread.sleep(bullet.getSpeed());
			}

		}

	}

	private void moveBullet(Bullet bullet, int step) throws InterruptedException {
		if (bullet.getDirection() == Direction.UP || bullet.getDirection() == Direction.DOWN) {

            bullet.updateY(step);

        } else {

            bullet.updateX(step);
        }
		repaint();
		Thread.sleep(bullet.getSpeed());
	}

	private int getStepBullet(Bullet bullet, int step) {
		if (bullet.getDirection() == Direction.UP || bullet.getDirection() == Direction.LEFT) {
            step = -1;
        }
		return step;
	}

	public String getQuadrantXY(int v, int h) {

		return (v - 1) * 64 + "_" + (h - 1) * 64;
	}

	public String getQuadrant(int x, int y) {

		return (y / 64) + "_" + (x / 64);
	}
	
	public void processMove(Tank tank) throws Exception {
        this.tank = tank;
		processTurn(tank);
		int step = getStepTank(tank);

		if (checkRange(tank.getDirection())) {
			return;
		}

		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.DOWN) {

			moveUpDown(step, tank.getDirection(), tank);
			return;
		}
		moveLeftRight(step, tank.getDirection(),tank);

	}

	private int getStepTank(Tank tank) {
		int step;
		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.LEFT) {
			step = -1;
		}else {
			step = 1;
		}
		return step;
	}

	private void moveUpDown(int step, Direction direction, Tank tank)	throws Exception {
		int covered = 0;

		while (covered < 64) {

			tank.updateY(step);
			repaint();
			Thread.sleep(tank.getSpeed());
			covered += 1;
		}
	}
	
	private void moveLeftRight(int step, Direction direction, Tank tank) throws Exception {
		int covered = 0;
		
		while (covered < 64) {

			tank.updateX(step);
			repaint();
			Thread.sleep(tank.getSpeed());
			covered += 1;
		}

	}
	public String checkNextQuadrant()  {

		String quadrant = getQuadrant(tank.getX(), tank.getY());
		String nextQuadrant;

		int step = getStepTank(tank);
		int separator = quadrant.indexOf("_");
		int y = Integer.parseInt(quadrant.substring(0, separator));
		int x = Integer.parseInt(quadrant.substring(separator + 1));

		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.DOWN) {
			if (y==0 || y==9){

			}
			nextQuadrant = battleField.scanQuadrant(y + step, x);

		} else {
			nextQuadrant = battleField.scanQuadrant(y, x + step);

		}
		return nextQuadrant;
	}

	public boolean checkRange(Direction direction) {
		if ((tank.getY() <= 0 && direction == Direction.UP)
				|| (tank.getY() >= 512 && direction == Direction.DOWN)
				|| (tank.getX() >= 512 && direction == Direction.RIGHT)
				|| (tank.getX() <= 0 && direction == Direction.LEFT)) {
			return true;

		}
		return false;
	}
		
	void processTurn(Tank tank) throws Exception {
		repaint();		
	}
	
		
    public ActionField () throws Exception {
    	Tank defender = new Tank(this, battleField);
    	Tank agressor = new Tank(this, battleField);
    	BattleField but = new BattleField();
	
		JFrame frame = new JFrame("BATTLE FIELD, DAY 2");
		frame.setLocation(750, 150);
		frame.setMinimumSize(new Dimension(battleField.getBF_WIDTH() + 16, battleField.getBF_HEIGHT() + 38));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}

	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

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

		for (int j = 0; j < battleField.getDimensionY(); j++) {
			for (int k = 0; k < battleField.getDimensionX(); k++) {
				if (battleField.scanQuadrant(j, k).equals("B")) {
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

		g.setColor(new Color(255, 0, 0));
		g.fillRect(tank.getX(), tank.getY(), 64, 64);

		g.setColor(new Color(0, 255, 0));
		if (tank.getDirection() == Direction.UP) {
			g.fillRect(tank.getX() + 20, tank.getY(), 24, 34);
		} else if (tank.getDirection() == Direction.DOWN) {
			g.fillRect(tank.getX() + 20, tank.getY() + 30, 24, 34);
		} else if (tank.getDirection() == Direction.LEFT) {
			g.fillRect(tank.getX(), tank.getY() + 20, 34, 24);
		} else {
			g.fillRect(tank.getX() + 30, tank.getY() + 20, 34, 24);
		}

		g.setColor(new Color(255, 255, 0));
		g.fillRect(bullet.getX(), bullet.getY(), 14, 14);

		g.setColor(new Color(255, 0, 0));
		g.fillRect(agressor.getX(), agressor.getY(), 64, 64);

		g.setColor(new Color(0, 255, 0));
		if (agressor.getDirection() == Direction.UP) {
			g.fillRect(agressor.getX() + 20, agressor.getY(), 24, 34);
		} else if (agressor.getDirection() == Direction.DOWN) {
			g.fillRect(agressor.getX() + 20, agressor.getY() + 30, 24, 34);
		} else if (agressor.getDirection() == Direction.LEFT) {
			g.fillRect(agressor.getX(), agressor.getY() + 20, 34, 24);
		} else {
			g.fillRect(agressor.getX() + 30, agressor.getY() + 20, 34, 24);
		}

		g.setColor(new Color(255, 255, 0));
		g.fillRect(bullet.getX(), bullet.getY(), 14, 14);
	}

}


