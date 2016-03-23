package movelableObjects;

import java.awt.*;

import interfaces.*;
public class Bullet implements Destroyable, Drawable {
	
	private int speed;
	private int x;
	private int y;
	private Direction direction;
	private boolean isDestroyed;
	private Tank tank;
	
	public Bullet (int x, int y, Direction direction, Tank tank) {
		speed = 5;
		this.x = x;
		this.y = y;
		this.direction = direction;
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
		this.x=-100;
		this.y = -100;
		isDestroyed = true;
	}
	public void draw (Graphics g) {

		int x = this.getX();
		int y = this.getY();
		int x1 = this.getX();
		int y1 = this.getY();
		if (!isDestroyed) {
			g.setColor(Color.DARK_GRAY);
			if (tank instanceof T34) {
				if (tank.getDirection() == Direction.LEFT || tank.getDirection() == Direction.RIGHT) {
					x = x - 10;
					y = y - 10;
					x1 = x1 - 10;
					y1 = y1 + 10;
				} else {
					x = x - 10;
					x1 = x1 + 10;
					y1 = y1 - 10;
					y = y - 10;
				}
				g.fillOval(x, y, 10, 10);
				g.fillOval(x1, y1, 10, 10);
			} else {
				g.fillOval(this.getX(), this.getY(), 10, 10);

			}
		}
	}
}