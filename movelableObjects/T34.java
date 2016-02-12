package movelableObjects;

import fieldObjects.BattleField;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class T34 extends AbstraktTank {


    public T34(BattleField bf) {
        super(bf);
        setImages();

    }


    public T34(BattleField bf, int x, int y, Direction direction) {
        super(bf, x, y, direction);
        setImages();
    }

    private void setImages() {
        images = new Image[4];
        try {
            images[0] = ImageIO.read(new File("T34_up.png").getAbsoluteFile());
            images[1] = ImageIO.read(new File("T34_down.png").getAbsoluteFile());
            images[2] = ImageIO.read(new File("T34_left.png").getAbsoluteFile());
            images[3] = ImageIO.read(new File("T34_right.png").getAbsoluteFile());
        } catch (IOException e) {
            throw new IllegalStateException("Can't find tank image");

        }
    }

    public Action setUp() {
        return super.setUp();
    }
    @Override
    public Action getAction() {


        if (checkPresenceTankOnLine(getOpponent())&& abilityFire(getOpponent())) {
            return setNecessaryDirection();
        } else {
            Direction dir = getDirection();
            return getActionForT34(dir);
        }
    }

    private Action getActionForT34(Direction dir) {
        if (getY() == 8 * quadrantSize) {
            if (!(dir == Direction.UP)) {
                dir = Direction.UP;
                return setActionDirection(dir);
            } else {
                return Action.MOVE;
            }
        } else {
            if (dir == Direction.UP) {
                if (getX() < 4 * quadrantSize) {
                    dir = Direction.RIGHT;
                } else {
                    dir = Direction.LEFT;
                }
                return setActionDirection(dir);
            } else {
                if (getX()==5*quadrantSize && (dir==Direction.RIGHT)) {
                    dir = Direction.DOWN;
                return setActionDirection(dir);}
                else if (getX()==3*quadrantSize && (dir==Direction.LEFT)) {
                    dir= Direction.DOWN;
                    return setActionDirection(dir);
                } else {

                return Action.MOVE; }
            }
        }
//        return Action.FIRE;
    }

}