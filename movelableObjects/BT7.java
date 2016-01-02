package movelableObjects;

import fieldObjects.*;
import interfaces.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BT7 extends AbstraktTank {
	private final int eagleH = 4;
	private final String EAGLE_QUADRANT = "8_4";

	public BT7 (BattleField bf) {
		super(bf);
		setSpeed(5);
        setImages();
	}
	
	public BT7 (BattleField bf, int x, int y, Direction direction) {
		super(bf, x, y, direction);
		setSpeed(5);
		setImages();

	}


	public Action setUp() {

        Object o = chooseAction();
		if (o instanceof Direction){
			Direction direction = (Direction)o;
			turn(direction);
			return Action.TURNING;
		} else {
		return (Action) o;}

	}

	private Object chooseAction() {

		if (checkPresenceTankOnLine(EAGLE_QUADRANT)&& abilityFire(EAGLE_QUADRANT)) {
			return setNecessaryDirection();
		}

		Drawable fObj = checkNextQuadrant(getDirection(), getStep());
		if (getDirection() != Direction.DOWN) {
			if ((checkNextQuadrant(Direction.DOWN, getStep()) instanceof Brick || (checkNextQuadrant(Direction.DOWN, getStep()) instanceof Empty))) {
				return Direction.DOWN;
			}
		}
		if (fObj == null) {
			return adaptationDirection();

		} else if (fObj instanceof Water || fObj instanceof Rock) {
			return adaptationDirection();

		} else if (fObj instanceof Brick || fObj instanceof Eagle || fObj instanceof AbstraktTank) {
			return Action.FIRE;

		} else {
			return Action.MOVE;
		}
	}

	private Direction adaptationDirection () {
		int hor = Integer.parseInt(bf.getQuadrant(getX(), getY()).substring(2));
	     Direction direction=getDirection();
		if ((direction==Direction.DOWN)) {
			if (hor > eagleH) {
				direction = Direction.LEFT;
			} else {
				direction = Direction.RIGHT;
			}
		} else if (direction==Direction.RIGHT)  {
			direction = Direction.DOWN;
            if (checkNextQuadrant(direction, getStep()) instanceof Rock || checkNextQuadrant(direction, getStep()) instanceof Water) {
				direction = Direction.LEFT;
			}
		} else if (direction==Direction.LEFT)
			direction = Direction.DOWN;
		    if (checkNextQuadrant(direction, getStep()) instanceof Rock || checkNextQuadrant(direction, getStep()) instanceof Water) {
			    direction = Direction.RIGHT;
		  }

		return direction;
	}
	private void setImages () {
		images = new Image[4];
		try {
			images[0] = ImageIO.read(new File("BT7_up.png").getAbsoluteFile());
			images[1] = ImageIO.read(new File("BT7_down.png").getAbsoluteFile());
			images[2] = ImageIO.read(new File("BT7_left.png").getAbsoluteFile());
			images[3] = ImageIO.read(new File("BT7_right.png").getAbsoluteFile());
		} catch (IOException e) {
			throw new IllegalStateException("Can't find tank image");

		}
	}
}
