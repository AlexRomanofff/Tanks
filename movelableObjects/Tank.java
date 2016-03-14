package movelableObjects;

import interfaces.Destroyable;
import interfaces.Drawable;

public interface Tank extends Drawable, Destroyable, Runnable {

    void setUp();

    void run();

    void move();

    Bullet fire();

    void startThread();

    int getX();

    int getY();

    void turn(Direction direction);

    Direction getDirection();

    void updateX(int x);

    void updateY(int y);

    int getSpeed();

}