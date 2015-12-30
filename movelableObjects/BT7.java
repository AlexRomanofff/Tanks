package movelableObjects;

import fieldObjects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BT7 extends AbstraktTank {
	private final int eagleV = 4;

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

		AbstractBFObject fObj = checkNextQuadrant(getDirection());
		if (getDirection() != Direction.DOWN) {
			if ((checkNextQuadrant(Direction.DOWN) instanceof Brick || (checkNextQuadrant(Direction.DOWN) instanceof Empty))) {
				return Direction.DOWN;
			}
		}
		if (fObj == null) {
			return adaptationDirection();

		} else if (fObj instanceof Water || fObj instanceof Rock) {
			return adaptationDirection();

		} else if (fObj instanceof Brick || fObj instanceof Eagle) {
			return Action.FIRE;

		} else {
			return Action.MOVE;
		}
    }

	private AbstractBFObject checkNextQuadrant(Direction direction) {
		int vert = Integer.parseInt(bf.getQuadrant(getX(), getY()).substring(0, 1));
		int hor = Integer.parseInt(bf.getQuadrant(getX(), getY()).substring(2));

		if (direction == Direction.UP) {
			vert--;
		} else if (direction == Direction.DOWN) {
			vert++;
		} else if (direction == Direction.LEFT) {
			hor--;
		} else {
			hor++;
		} if ((hor>8 || hor<0)|| (vert>8 || vert<0)) {
			return null;
		}
		return bf.scanQuadrant(vert, hor);
	}

	private Direction adaptationDirection () {
		int hor = Integer.parseInt(bf.getQuadrant(getX(), getY()).substring(2));
	     Direction direction=getDirection();
		if ((direction==Direction.DOWN)) {
			if (hor > eagleV) {
				direction = Direction.LEFT;
			} else {
				direction = Direction.RIGHT;
			}
		} else if (direction==Direction.RIGHT)  {
			direction = Direction.DOWN;
            if (checkNextQuadrant(direction) instanceof Rock || checkNextQuadrant(direction) instanceof Water) {
				direction = Direction.LEFT;
			}
		} else if (direction==Direction.LEFT)
			direction = Direction.DOWN;
		    if (checkNextQuadrant(direction) instanceof Rock || checkNextQuadrant(direction) instanceof Water) {
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
