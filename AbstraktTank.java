import java.awt.*;
import java.util.Random;

public abstract class AbstraktTank implements Destroyable, Drawable {

	private int speed = 10;
	private int x;
	private int y;
	private  Direction direction;
	ActionField af;
	BattleField bf;
	String quadrant = randomChoiseQuadrant();
	private Color tankColor;
	private Color towerColor;

	public AbstraktTank(ActionField af, BattleField bf) {
		this(af, bf, 192, 512, Direction.UP);
	}

	public AbstraktTank(ActionField af, BattleField bf, Direction direction) {
		this.af = af;
		this.bf = bf;
		coordTanks(af, quadrant);
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
	private void coordTanks(ActionField af, String quadrant) {
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
		Bullet bullet = new Bullet (x+25, y+25, direction);
		af.processFire(bullet);
		
	}

		
	public void moveRandom () throws Exception {
		
		while (true) {
			int random = (int) (System.nanoTime() % 4+1);
		    
			if (random==1) {
				direction = Direction.UP;
			} 
			else if (random==2) {
				direction = Direction.DOWN;
			}
			else if (random==3) {
				direction = Direction.LEFT;
			}
			else {
				direction = Direction.RIGHT;
			}
			turn(direction);
			
			if (af.checkRange(this,direction)) {
			} else {
				if (af.checkNextQuadrant(this).trim().isEmpty()) {
					move();
				}
			}
			System.out.println(random);

		}

	}
	
	public void moveToQuadrant(int v, int h) throws Exception {

		String quadrant = bf.getQuadrantXY(v, h);

		int separator = quadrant.indexOf("_");
		int y = Integer.parseInt(quadrant.substring(0, separator));
		int x = Integer.parseInt(quadrant.substring(separator + 1));

		if (x > getX()) {
			direction = Direction.RIGHT;

		} else if (x < getX()) {
			direction = Direction.LEFT;
		}
		turn(direction);
		fire();
      		while ( x!=getX()) {

			if (!af.checkNextQuadrant(this).equals(" ")) {
				fire();
			}
			move();
		}
		
		if (y > getY()) { direction = Direction.DOWN;
		} 
		else if (y < getY()) { direction = Direction.UP;
		}
		turn(direction);
		fire();
		while (y != getY()) {

			if (!af.checkNextQuadrant(this).equals(" ")) {
				fire();
			}
			move();
		}

}

	public void destroy() {
		x=-100;
		y=-100;
	}
	
	public void clean() throws Exception {

		for (int i = 1; i <= 9; i++) {

			moveToQuadrant(i, 1);

			turn(Direction.RIGHT);

			for (int x = 1; x < bf.battleField.length + 1; x++) {

				if (bf.scanQuadrant(i - 1, x - 1).trim().isEmpty()) {

				} else {
					fire();

				}
			}
		}
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
