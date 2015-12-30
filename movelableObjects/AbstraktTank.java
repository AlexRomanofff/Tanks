package movelableObjects;

import fieldObjects.BattleField;
import fieldObjects.Water;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public abstract class AbstraktTank implements Tank{

	private int speed = 10;
	private int x;
	private int y;
	private Direction direction;
	protected BattleField bf;
	protected Image[] images;

	private String quadrant = randomChoiseQuadrant();
	private boolean destroyed;

	public AbstraktTank(BattleField bf) {
		this(bf, 192, 0, Direction.DOWN);

		}

	public AbstraktTank(BattleField bf, Direction direction) {

		this.bf = bf;
		coordTanks(quadrant);
		this.direction = direction;

	}

	public AbstraktTank(BattleField bf, int x, int y, Direction direction) {

		this.bf = bf;
		this.x = x;
		this.y = y;
		this.direction = direction;

	}

	public String randomChoiseQuadrant () {
		Random r = new Random();
		String quadrant1 = "9_6";
		String quadrant2 = "8_8";
		String quadrant3 = "4_5";
		int  i;
		i=r.nextInt(3);
		if (i==1) {
			return quadrant1;
		}else if (i==2) {
			return quadrant3;

		}else {
			return quadrant2;
		}

	}
	private void coordTanks(String quadrant) {
		String coord=bf.getQuadrantXY(Integer.parseInt(quadrant.substring(0,1)), Integer.parseInt(quadrant.substring(2)));
		int separator = coord.indexOf("_");
		y = Integer.parseInt(coord.substring(0, separator));
		x = Integer.parseInt(coord.substring(separator + 1));
	}


	public int getSpeed() {
		return speed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Direction getDirection() {
		return direction;
	}

	public void updateX(int x) {
		this.x += x;
	}

	public void updateY(int y) {
		this.y += y;
	}

	public void turn(Direction direction){
		this.direction = direction;
	}

	public void move()  {

	}

	public Bullet fire() {
		int xBullet = getX() + 27;
		int yBullet = getY() + 5;

		if (direction == Direction.UP) {
			xBullet = getX() + 27;
			yBullet = getY() + 5;
		} else if (direction == Direction.DOWN) {
			yBullet = getY() + 59;
		} else if (direction == Direction.LEFT) {
			xBullet = getX() + 5;
			yBullet = getY() + 27;
		} else if (direction == Direction.RIGHT) {
			xBullet = getX() + 59;
			yBullet = getY() + 27;
		}
		return new Bullet(xBullet, yBullet, direction, this);
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void destroy() {
		destroyed = true;
	}

	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		if (!destroyed) {
			String coordTanks = bf.getQuadrant(getX(), getY());
				 if (bf.scanQuadrant(Integer.parseInt(coordTanks.substring(0,1)), Integer.parseInt(coordTanks.substring(2))) instanceof Water) {

					 g2D.setComposite(AlphaComposite.getInstance(
						 AlphaComposite.SRC_OVER, 0.3f));
				 g2D.drawImage(images[getDirection().getID()], getX(), getY(), new ImageObserver() {
					 @Override
					 public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
						 return false;
					 }
				 });
			 } else {
				 g2D.setComposite(AlphaComposite.getInstance(
						 AlphaComposite.SRC_OVER, 1f));
				 g2D.drawImage(images[getDirection().getID()], getX(), getY(), new ImageObserver() {
					 @Override
					 public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
						 return false;
					 }
				 });
			 }
		}
	}
}
