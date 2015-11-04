import java.util.Random;

public class Tank {

	protected int speed = 10;
	private int x;
	private int y;
	private  Direction direction;
	ActionField af;
	BattleField bf;
	String quadrant = randomChoiseQuadrant();

	public Tank (ActionField af, BattleField bf) {
		this(af, bf, 192, 512, Direction.UP);
	}

	public Tank(ActionField af, BattleField bf, Direction direction) {
		this.af = af;
		this.bf = bf;
		coordTanks(af, quadrant);
		this.direction = direction;
	}

	public Tank(ActionField af, BattleField bf, int x, int y, Direction direction) {
		this.af = af;
		this.bf = bf;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public String randomChoiseQuadrant () {
		Random r = new Random();
		String quadrant1 = "9_6";
		String quadrant2 = "8_8";
		String quadrant3 = "5_1";
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
		String coord=af.getQuadrantXY(Integer.parseInt(quadrant.substring(0,1)), Integer.parseInt(quadrant.substring(2)));
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
			
			if (af.checkRange(direction)) {
			} else {
				if (af.checkNextQuadrant().trim().isEmpty()) {
					move();
				}
			}
			System.out.println(random);

		}

	}
	
	public void moveToQuadrant(int v, int h) throws Exception {

		String quadrant = af.getQuadrantXY(v, h);

		int separator = quadrant.indexOf("_");
		int y = Integer.parseInt(quadrant.substring(0, separator));
		int x = Integer.parseInt(quadrant.substring(separator + 1));

		if (x > getX()) {
			direction = Direction.RIGHT;

		} else if (x < getX()) {
			direction = Direction.LEFT;
		}
		turn(direction);

		while (x != getX()) {

			if (af.checkNextQuadrant().equals("B")) {
				fire();
			}
			move();
		}
		
		if (y > getY()) { direction = Direction.DOWN;
		} 
		else if (y < getY()) { direction = Direction.UP;
		}
		turn(direction);

		while (y != getY()) {

			if (af.checkNextQuadrant().equals("B")) {
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
}
