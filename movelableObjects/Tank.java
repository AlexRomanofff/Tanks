package movelableObjects;

import interfaces.Destroyable;
import interfaces.Drawable;

public interface Tank extends Drawable, Destroyable {

    Action setUp();

    void move();

    Bullet fire();

    int getX();

    int getY();

    void turn(Direction direction);

    Direction getDirection();

    void updateX(int x);

    void updateY(int y);

    int getSpeed();

}