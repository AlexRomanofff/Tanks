package movelableObjects;

import fieldObjects.BattleField;

import java.awt.*;
import java.util.Random;

public abstract class AbstraktTank implements Tank{

	private int speed = 10;
	private int x;
	private int y;
	private Direction direction;
	BattleField bf;

	String quadrant = randomChoiseQuadrant();
	private Color tankColor;
	private Color towerColor;
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

	public Color getTankColor() {
		return tankColor;
	}

	public void setTankColor(Color tankColor) {
		this.tankColor = tankColor;
	}

	public Color getTowerColor() {
		return towerColor;
	}

	public void setTowerColor(Color towerColor) {
		this.towerColor = towerColor;
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
	
	public Bullet fire () {

		return new Bullet (x+25, y+25, direction, this);

	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void destroy() {
		destroyed = true;
	}

	public void draw(Graphics g) {
		if (!destroyed) {

			g.setColor(tankColor);
			g.fillRect(this.getX(), this.getY(), 64, 64);

			g.setColor(towerColor);
			if (this.getDirection() == Direction.UP) {
				g.fillRect(this.getX() + 20, this.getY(), 24, 34);
			} else if (this.getDirection() == Direction.DOWN) {
				g.fillRect(this.getX() + 20, this.getY() + 30, 24, 34);
			} else if (this.getDirection() == Direction.LEFT) {
				g.fillRect(this.getX(), this.getY() + 20, 34, 24);
			} else {
				g.fillRect(this.getX() + 30, this.getY() + 20, 34, 24);
			}
		}
	}
}
