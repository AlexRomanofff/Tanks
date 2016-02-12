package movelableObjects;

import interfaces.Destroyable;
import interfaces.Drawable;

public interface Tank extends Drawable, Destroyable {

    public Action setUp();

    public void move();

    public Bullet fire();

    public int getX();

    public int getY();

    public void turn(Direction direction);

    public Direction getDirection();

    public void updateX(int x);

    public void updateY(int y);

    public int getSpeed();

//    public int getMovePath();

}