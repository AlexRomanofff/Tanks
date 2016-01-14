package movelableObjects;

import fieldObjects.*;
import interfaces.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Tiger extends AbstraktTank {
	
	private int armor=1;
	private Direction []directions = new Direction[4];

	
	public Tiger  (BattleField bf) {
		super(bf);
		setImages();

	}
	
	public Tiger (BattleField bf, int x, int y, Direction direction) {
		super(bf, x, y, direction);
		setImages();
	}
	public Tiger (BattleField bf, Direction direction) {
		super(bf,direction);
		setImages();
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

   @Override
   public void destroy() {
	   if (getArmor() == 1) {
		   setArmor(0);
		   return;
	   } else {
		   super.destroy();
	   }
   }

	public Action setUp() {
     return super.setUp();

	}
	private void setImages () {
		images = new Image[4];
		try {
			images[0] = ImageIO.read(new File("Tiger_up.png").getAbsoluteFile());
			images[1] = ImageIO.read(new File("Tiger_down.png").getAbsoluteFile());
			images[2] = ImageIO.read(new File("Tiger_left.png").getAbsoluteFile());
			images[3] = ImageIO.read(new File("Tiger_right.png").getAbsoluteFile());
		} catch (IOException e) {
			throw new IllegalStateException("Can't find tank image");

		}
	}
	private void generalDirection () {

		String coordOpponent = getOpponent();
		int xOpponent = Integer.parseInt(coordOpponent.substring(2));
		int yOpponent = Integer.parseInt(coordOpponent.substring(0,1));

		String myCoord = bf.getQuadrant(getX(),getY());
		int myX = Integer.parseInt(myCoord.substring(2));
		int myY = Integer.parseInt(myCoord.substring(0,1));

		int dx = xOpponent-myX;
		int dy = yOpponent-myY;
		if (Math.abs(dx)>Math.abs(dy)) {
			directions[0] = Direction.RIGHT;
			directions[3] = Direction.LEFT;
			if (dx<0) {
				directions[0] = Direction.LEFT;
				directions[3] = Direction.RIGHT;
			} directions[1] = Direction.DOWN;
			  directions[2] = Direction.UP;
			if (dy<0) {
				directions[1] = Direction.UP;
				directions[2] = Direction.DOWN;
			}
		} else {
			directions[0] = Direction.DOWN;
			directions[3] = Direction.UP;
			if (dy<0) {
				directions[0] = Direction.UP;
				directions[3] = Direction.DOWN;
			} directions[1] = Direction.RIGHT;
			  directions[2] = Direction.LEFT;
			if (dx<0) {
				directions[1] = Direction.LEFT;
				directions[2] = Direction.RIGHT;
			}
		}
	}
	@Override
	public Object getAction() {
		generalDirection();

		if (checkPresenceTankOnLine(getOpponent())&& abilityFire(getOpponent())) {
			return setNecessaryDirection();
		}
		Drawable fObj = checkNextQuadrant(getDirection(), getStep());
		if (getDirection() != directions[0] ) {
			if ((checkNextQuadrant(directions[0], getStep()) instanceof Brick || (checkNextQuadrant(directions[0], getStep()) instanceof Empty))) {
				return directions[0];
			}
		}
		if (fObj == null || fObj instanceof Water || fObj instanceof Rock) {
			return adaptationDirection();

		} else if (fObj instanceof Brick || fObj instanceof Eagle || fObj instanceof AbstraktTank) {
			return Action.FIRE;

		} else {
			return Action.MOVE;
		}
	}

	private Direction adaptationDirection() {
		Direction direction = getDirection();
		if ((direction == directions[0])) {
			direction = directions[1];
		} else if (direction == directions[1]) {
			direction = directions[0];
			if (checkNextQuadrant(direction, getStep()) instanceof Rock || checkNextQuadrant(direction, getStep()) instanceof Water) {
				direction = directions[2];
			}
		} else if (direction == directions[2])
			direction = directions[0];
		if (checkNextQuadrant(direction, getStep()) instanceof Rock || checkNextQuadrant(direction, getStep()) instanceof Water) {
			direction = directions[1];
		}

		return direction;
	}
}
