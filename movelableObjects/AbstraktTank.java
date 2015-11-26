package movelableObjects;

import fieldObjects.BattleField;
import interfaces.*;
import java.awt.*;
import engine.*;
import java.util.Random;

public abstract class AbstraktTank implements Destroyable, Drawable {

	private int speed = 10;
	private int x;
	private int y;
	private Direction direction;
	ActionField af;
	BattleField bf;
	String quadrant = randomChoiseQuadrant();
	private Color tankColor;
	private Color towerColor;

	public AbstraktTank(ActionField af, BattleField bf) {
		this(af, bf, 256, 512, Direction.UP);
		}

	public AbstraktTank(ActionField af, BattleField bf, Direction direction) {
		this.af = af;
		this.bf = bf;
		coordTanks(quadrant);
		this.direction = direction;

	}

	public AbstraktTank(ActionField af, BattleField bf, int x, int y, Direction direction) {
		this.af = af;
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
		String quadrant3 = "4_4";
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

	public void turn(Direction direction) throws Exception {
		this.direction = direction;
		af.processTurn(this);
	}

	public void move() throws Exception {
		af.processMove(this);

	}
	
	public void fire () throws Exception {
		Bullet bullet = new Bullet(x+25, y+25, direction);
		af.processFire(bullet);
		
	}

	public void destroy() {
		x=-100;
		y=-100;
	}
	


	public void draw (Graphics g) {
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
