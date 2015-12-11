package movelableObjects;

import java.awt.*;

import interfaces.*;
public class Bullet implements Destroyable, Drawable {
	
	private int speed = 5;
	private int x;
	private int y;
	private Direction direction;
	private boolean isDestroyed;
	private Tank tank;
	
	public Bullet (int x, int y, Direction direction, Tank tank) {
		this.x = x;
		this.y = y;
		this.direction = tank.getDirection();
		this.tank = tank;
		isDestroyed = false;
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
	public Direction getDirection() {
		return direction;
	}
	public void updateX  (int x) { 
		this.x+=x;
	}
	public void updateY  (int y) { 
		this.y+=y;
	}

	public Tank getTank() {
		return tank;
	}

	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void destroy() {
		isDestroyed = true;
	}
	public void draw (Graphics g) {
		if (!isDestroyed) {
			g.setColor(new Color(255, 255, 0));
			g.fillRect(this.getX(), this.getY(), 14, 14);
		}
	}
}