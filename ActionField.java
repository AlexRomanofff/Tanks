import javax.swing.*;
import java.awt.*;


public class ActionField extends JPanel {
	
	final boolean COLORDED_MODE = true;
	BattleField battleField;
	Bullet bullet;

	Tiger agressor;
    Tank defender;

	public ActionField () throws Exception {

		battleField = new BattleField();
		bullet = new Bullet(-100, -100, Direction.UP);
		defender = new Tank(this, battleField);
		agressor = new Tiger(this, battleField, Direction.UP);


		JFrame frame = new JFrame("BATTLE FIELD, DAY 2");
		frame.setLocation(750, 150);
		frame.setMinimumSize(new Dimension(battleField.getBF_WIDTH() + 16, battleField.getBF_HEIGHT() + 38));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void runTheGame() throws Exception {

//		defender.moveToQuadrant(1,1);
		defender.fire();
		defender.fire();
		defender.fire();
		defender.fire();
		defender.fire();
		defender.fire();





	}
	
	private boolean processInterception() throws Exception {

		String quadrantBullet = getQuadrant(bullet.getX(), bullet.getY());
		int separator = quadrantBullet.indexOf("_");
		int y = Integer.parseInt(quadrantBullet.substring(0, separator));
		int x = Integer.parseInt(quadrantBullet.substring(separator + 1));
		if ((y >= 0 && y < 9) && (x >= 0 && x < 9)) {

			if (battleField.scanQuadrant(y, x).equals("B")) {
				battleField.updateQuadrant(y, x, " ");

				return true;}

			String quadrantAgressor = getQuadrant(agressor.getX(),agressor.getY());
			if(checkInterceptionInQuadrant(quadrantBullet, quadrantAgressor)) {

				agressor.destroy();

				return true;
			}

		}return  false;



	}

	private boolean checkInterceptionInQuadrant (String quadrantBullet, String quadrantTank) {

		if (quadrantTank.length()>3){
			return false;
		}
		int bulletX = Integer.parseInt(quadrantBullet.substring(2));
		int bulletY = Integer.parseInt(quadrantBullet.substring(0,1));
		int tankX = Integer.parseInt(quadrantTank.substring(2));
		int tankY = Integer.parseInt(quadrantTank.substring(0,1));
		if ((bulletX == tankX)&&(bulletY==tankY)) {
			return true;
		}

		return false;
	}

	public void processFire(Bullet bullet) throws Exception {
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

		processTurn(tank);
		int step = getStepTank(tank);

		if (checkRange(tank, tank.getDirection())) {
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
	public String checkNextQuadrant(Tank tank)  {

		String quadrant = getQuadrant(tank.getX(), tank.getY());
		String nextQuadrant;

		int step = getStepTank(tank);
		int separator = quadrant.indexOf("_");
		int y = Integer.parseInt(quadrant.substring(0, separator));
		int x = Integer.parseInt(quadrant.substring(separator + 1));

		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.DOWN) {

			nextQuadrant = battleField.scanQuadrant(y + step, x);

		} else {
			nextQuadrant = battleField.scanQuadrant(y, x + step);

		}

		return nextQuadrant;
	}

	public boolean checkRange(Tank tank, Direction direction) {
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
		g.fillRect(defender.getX(), defender.getY(), 64, 64);

		g.setColor(new Color(0, 255, 0));
		if (defender.getDirection() == Direction.UP) {
			g.fillRect(defender.getX() + 20, defender.getY(), 24, 34);
		} else if (defender.getDirection() == Direction.DOWN) {
			g.fillRect(defender.getX() + 20, defender.getY() + 30, 24, 34);
		} else if (defender.getDirection() == Direction.LEFT) {
			g.fillRect(defender.getX(), defender.getY() + 20, 34, 24);
		} else {
			g.fillRect(defender.getX() + 30, defender.getY() + 20, 34, 24);
		}

		g.setColor(new Color(255, 255, 0));
		g.fillRect(bullet.getX(), bullet.getY(), 14, 14);

		g.setColor(new Color(0, 255, 0));
		g.fillRect(agressor.getX(), agressor.getY(), 64, 64);

		g.setColor(new Color(255, 0, 0));
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


