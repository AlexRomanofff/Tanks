package movelableObjects;

import fieldObjects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class BT7 extends AbstraktTank {
	private static final int eagleH = 4;
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
			return super.setUp();
	}

    @Override
	public Object getAction() {

		if (checkPresenceTankOnLine(EAGLE_QUADRANT)&& abilityFire(EAGLE_QUADRANT)) {
			return setNecessaryDirection();
		}
		return getActionForBT7();
	}

	private Object getActionForBT7() {
//		Drawable fObj = checkNextQuadrant(getDirection(), getStep());
//		if (getDirection() != Direction.DOWN) {
//			if ((checkNextQuadrant(Direction.DOWN, getStep()) instanceof Brick || (checkNextQuadrant(Direction.DOWN, getStep()) instanceof Empty))) {
//				return Direction.DOWN;
//			}
//		}
//		if (fObj == null) {
//			return adaptationDirection();
//
//		} else if (fObj instanceof Water || fObj instanceof Rock) {
//			return adaptationDirection();
//
//		} else if (fObj instanceof Brick || fObj instanceof Eagle || fObj instanceof AbstraktTank) {
//			return Action.FIRE;
//
//		} else {
//			return Action.MOVE;
//		}

		Stack<String> way = choseShortestWay(EAGLE_QUADRANT);
		return moveToNextQuadrant(way.peek());
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
