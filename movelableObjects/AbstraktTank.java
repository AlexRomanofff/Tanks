package movelableObjects;

import engine.PathWay;
import fieldObjects.*;
import interfaces.Drawable;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;
import java.util.Stack;

public abstract class AbstraktTank implements Tank{

	private int speed = 10;
	private int x;
	private int y;
	private Direction direction;
	protected BattleField bf;
	protected Image[] images;

	private String quadrant = randomChoiseQuadrant();
	private boolean destroyed;

	private int quadrantX;
	private int quadrantY;
	private int quadrantXEnemy;
	private int quadrantYEnemy;
	private int step = 1;
	public final int quadrantSize  = 64;


	public int getStep() {
		return step;
	}

	public AbstraktTank(BattleField bf) {

		this(bf, 0, 0, Direction.DOWN);

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

	public Object getAction () {
		return null;
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
	public Action setUp() {

		Object o = getAction();
		if (o instanceof Direction){
			Direction direction = (Direction)o;
			turn(direction);
			return Action.TURNING;
		} else {
			return (Action) o;}


	}
	public String getOpponent () {
		String quadrantOpponent = bf.getQuadrant(bf.getAgressor().getX(), bf.getAgressor().getY());
		if (this == bf.getAgressor()) {
			quadrantOpponent = bf.getQuadrant(bf.getDefender().getX(), bf.getDefender().getY());
		}
		return quadrantOpponent;
	}

	public Drawable checkNextQuadrant(Direction direction, int step) {
		int vert = Integer.parseInt(bf.getQuadrant(getX(), getY()).substring(0, 1));
		int hor = Integer.parseInt(bf.getQuadrant(getX(), getY()).substring(2));

		if (direction == Direction.UP) {
			vert=vert-step;
		} else if (direction == Direction.DOWN) {
			vert=vert+step;
		} else if (direction == Direction.LEFT) {
			hor=hor-step;
		} else {
			hor=hor+step;
		}
		String coordinates = (Integer.toString(vert) + "_" + Integer.toString(hor));
		if (coordinates.equals(getOpponent())) {
			return getEnemy();
		}
		if (checkBorders(vert, hor)) return null;
		return bf.scanQuadrant(vert, hor);
	}

	private boolean checkBorders(int vert, int hor) {
		return ((hor > 8 || hor < 0) || (vert > 8 || vert < 0));
	}

	private Drawable getEnemy() {
		if (this == bf.getAgressor()) {
            return bf.getDefender();
        } else {
            return bf.getAgressor();
        }
	}

	public Object setNecessaryDirection() {

		if (getDirection()== necessaryDirection()) {
				return Action.FIRE;
			} else {
				direction= necessaryDirection();
				return direction;

			}
	}

	public boolean abilityFire(String enemyCoord) {

		int distance = quadrantX - Integer.parseInt(enemyCoord.substring(2));
		if (distance == 0) {
			distance = quadrantY - Integer.parseInt(enemyCoord.substring(0,1));
		}
		int step = 1;
		while (!(distance == 0)) {
            if ((this instanceof T34 && checkEagleOnFireLine(step))
			|| (!(this instanceof T34) && checkRockOnFireLine(step))){
					return false;

			}else {
				step++;
				if (distance < 0) {
					distance++;
				} else {
					distance--;
				}
			}
		}
		return true;
	}

	private boolean checkRockOnFireLine(int step) {
		return checkNextQuadrant(necessaryDirection(), step) instanceof Rock;
	}

	private boolean checkEagleOnFireLine(int step) {
		return checkNextQuadrant(necessaryDirection(), step) instanceof Eagle;
	}

	public boolean checkPresenceTankOnLine (String enemyCoord) {

		quadrantXEnemy = Integer.parseInt(enemyCoord.substring(2));
		quadrantYEnemy = Integer.parseInt(enemyCoord.substring(0, 1));

		String myCoord = bf.getQuadrant(getX(), getY());
		quadrantX = Integer.parseInt(myCoord.substring(2));
		quadrantY = Integer.parseInt(myCoord.substring(0, 1));

		return (quadrantX == quadrantXEnemy || quadrantY == quadrantYEnemy);
	}

	private Direction necessaryDirection() {

		int distance = quadrantX - quadrantXEnemy;
		Direction dir = Direction.LEFT;
		if (distance < 0) {
			dir = Direction.RIGHT;
		} else if (distance == 0) {
			distance = quadrantY - quadrantYEnemy;
			dir = Direction.UP;
			if (distance < 0) {
				dir = Direction.DOWN;
			}
		}return dir;
	}
	public Stack choseShortestWay (String enemy) {
		String myCoord = bf.getQuadrant(getX(), getY());
		PathWay pathWay = new PathWay(bf.battleField, myCoord, enemy);
		return pathWay.getPath();
	}

	public Object moveToNextQuadrant (String coord) {
		int coordY = Integer.parseInt(coord.substring(0,1));
		int coordX = Integer.parseInt(coord.substring(2));
		String myCoord = bf.getQuadrant(getX(), getY());
		int myX = Integer.parseInt(myCoord.substring(2));
		int myY = Integer.parseInt(myCoord.substring(0, 1));

		Direction dir = choseDirection(coordY, coordX, myX, myY);

		if (getDirection() == dir) {

			return checkNextQuadrant(coordY, coordX);

		} else {
		  return dir;
		}
	}

	private Object checkNextQuadrant(int coordY, int coordX) {
		if (bf.scanQuadrant(coordY, coordX) instanceof Empty) {
            return Action.MOVE;
        } else {
            return Action.FIRE;
        }
	}

	private Direction choseDirection(int coordY, int coordX, int myX, int myY) {
		Direction nessecaryDirection;

		if (coordX - myX < 0) {
			nessecaryDirection = Direction.LEFT;
		} else if (coordX - myX > 0) {
			nessecaryDirection = Direction.RIGHT;
		} else if (coordY - myY < 0) {
			nessecaryDirection = Direction.UP;
		} else {
			nessecaryDirection = Direction.DOWN;
		} return nessecaryDirection;
	}
}
