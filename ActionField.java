import javax.swing.*;
import java.awt.*;


public class ActionField extends JPanel {
	
	private BattleField battleField;
	private Bullet bullet;
	private AbstraktTank agressor;
    private AbstraktTank defender;

	public ActionField () throws Exception {

		battleField = new BattleField();
		bullet = new Bullet(-100, -100, Direction.UP);
		defender = new T34(this, battleField);
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

		defender.moveToQuadrant(1,1);
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
		if (agressor.getX()<0) {
			Thread.sleep(3000);
			agressor = new Tiger(this, battleField, Direction.UP);}
		if ((y >= 0 && y < 9) && (x >= 0 && x < 9)) {

			if (battleField.scanQuadrant(y, x).equals("R")) {
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


	public String getQuadrant(int x, int y) {

		return (y / 64) + "_" + (x / 64);
	}
	
	public void processMove(AbstraktTank tank) throws Exception {

		processTurn(tank);
		int step = getStepTank(tank);

		if (checkRange(tank, tank.getDirection())) {
			return;
		}

		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.DOWN) {

			moveUpDown(step, tank);
			return;
		}
		moveLeftRight(step,tank);

	}

	private int getStepTank(AbstraktTank tank) {
		int step;
		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.LEFT) {
			step = -1;
		}else {
			step = 1;
		}
		return step;
	}

	private void moveUpDown(int step, AbstraktTank tank)	throws Exception {
		int covered = 0;

		while (covered < 64) {

			tank.updateY(step);
			repaint();
			Thread.sleep(tank.getSpeed());
			covered += 1;
		}
	}
	
	private void moveLeftRight(int step, AbstraktTank tank) throws Exception {
		int covered = 0;
		
		while (covered < 64) {

			tank.updateX(step);
			repaint();
			Thread.sleep(tank.getSpeed());
			covered += 1;
		}

	}
	public String checkNextQuadrant(AbstraktTank tank)  {

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

	public boolean checkRange(AbstraktTank tank, Direction direction) {
		if ((tank.getY() <= 0 && direction == Direction.UP)
				|| (tank.getY() >= 512 && direction == Direction.DOWN)
				|| (tank.getX() >= 512 && direction == Direction.RIGHT)
				|| (tank.getX() <= 0 && direction == Direction.LEFT)) {
			return true;

		}
		return false;
	}
		
	void processTurn(AbstraktTank tank) throws Exception {
		repaint();		
	}
	
		


	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		battleField.draw(g);
		defender.draw(g);
		agressor.draw(g);
        bullet.draw(g);


	}

}


