package engine;

import fieldObjects.*;
import interfaces.Drawable;
import movelableObjects.AbstraktTank;
import movelableObjects.Bullet;
import movelableObjects.*;
import javax.swing.*;
import java.awt.*;


public class  ActionField extends JPanel {
	
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

        defender.turn(Direction.RIGHT);
//		defender.move();
//		defender.move();
//		defender.move();
		defender.fire();
		defender.fire();
//		defender.fire();
//		defender.fire();
//		defender.fire();
		defender.move();
		defender.move();


	}
	
	private boolean processInterception(Bullet bullet) throws Exception {

		String quadrantBullet = getQuadrant(bullet.getX(), bullet.getY());
		int separator = quadrantBullet.indexOf("_");
		int y = Integer.parseInt(quadrantBullet.substring(0, separator));
		int x = Integer.parseInt(quadrantBullet.substring(separator + 1));


		if (agressor.getX()<0) {
			Thread.sleep (3000);
			agressor = new Tiger(this, battleField, Direction.UP);}
		if ((y >= 0 && y < 9) && (x >= 0 && x < 9)) {

              AbstractBFObject fobj = battleField.scanQuadrant(y,x);

			if (fobj instanceof Brick) {
				battleField.updateQuadrant(y, x, new Empty(x*64,y*64));
				((Brick) fobj).destroy();
				return true;
			}

		    if (fobj instanceof Eagle) {
				battleField.updateQuadrant(y, x, new Empty(x*64,y*64));
				((Eagle) fobj).destroy();
				return true;
			}

			if (fobj instanceof Rock) {
				if (bullet instanceof BulletT34) {
					battleField.updateQuadrant(y, x,new Empty(x*64,y*64));
					((Rock) fobj).destroy();
				}return true;
			}

			String tankQuadrant = tankQuadrant(agressor);
			if(checkInterceptionInQuadrant(quadrantBullet, tankQuadrant)) {

				agressor.destroy();
				return true;
			}

		}return  false;

	}

	private String tankQuadrant (AbstraktTank tank) {
		return getQuadrant(tank.getX(),tank.getY());
	}


	private boolean checkInterceptionInQuadrant (String quadrantBullet, String destroyableObj) {

		if (destroyableObj.length()>3){
			return false;
		}
		int bulletX = Integer.parseInt(quadrantBullet.substring(2));
		int bulletY = Integer.parseInt(quadrantBullet.substring(0,1));
		int tankX = Integer.parseInt(destroyableObj.substring(2));
		int tankY = Integer.parseInt(destroyableObj.substring(0,1));
		return ((bulletX == tankX)&&(bulletY==tankY));

	}

	public void processFire(Bullet bullet) throws Exception {
		this.bullet = bullet;
		int step = 1;

		while ((bullet.getX() >= -15 && bullet.getX() <= 575)
				&& (bullet.getY() >= -15 && bullet.getY() <= 575)) {

			step = getStepBullet(bullet, step);

			moveBullet(bullet, step);

			if (processInterception(bullet)) {
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
	public Drawable checkNextQuadrant(AbstraktTank tank)  {

		String quadrant = getQuadrant(tank.getX(), tank.getY());

		int step = getStepTank(tank);
		int separator = quadrant.indexOf("_");
		int tankY = Integer.parseInt(quadrant.substring(0, separator));
		int tankX = Integer.parseInt(quadrant.substring(separator + 1));

		if (checkTankPresence(tank, tankY, tankX, separator)) return agressor;

		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.DOWN) {
				tankY = tankY + step;
			} else {
				tankX = tankX + step;
			}


		return battleField.scanQuadrant(tankY, tankX);

	}

	private boolean checkTankPresence(AbstraktTank tank, int tankY, int tankX, int separator) {
		String quadrantOpponent = getQuadrant(agressor.getX(), agressor.getY());
		if (tank == agressor) {
			quadrantOpponent = getQuadrant(defender.getX(), defender.getY());
		}
		if (quadrantOpponent.length()==3) {
			int xOpponent = Integer.parseInt(quadrantOpponent.substring(separator + 1));
			int yOpponent = Integer.parseInt(quadrantOpponent.substring(0, separator));
			if ((tankX == xOpponent) && (tankY == yOpponent)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkRange(AbstraktTank tank, Direction direction) {
		return ((tank.getY() <= 0 && direction == Direction.UP)
				|| (tank.getY() >= 512 && direction == Direction.DOWN)
				|| (tank.getX() >= 512 && direction == Direction.RIGHT)
				|| (tank.getX() <= 0 && direction == Direction.LEFT))
				|| !(checkNextQuadrant(tank) instanceof Empty);
	}
		
	public void processTurn(AbstraktTank tank) throws Exception {
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


