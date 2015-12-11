package fieldObjects;

import java.awt.*;
import interfaces.*;
import movelableObjects.Tank;


public abstract class AbstractBFObject implements BFObject {

    public AbstractBFObject(int x, int y) {
        this.x=x;
        this.y = y;
    }

    private int x;
    private int y;
    private Color color;
    private boolean isDestroyed = false;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    @Override
    public void draw(Graphics g) {
        if (!isDestroyed) {

        g.fillRect(getX(),getY(),64,64);
        }
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void destroy() {
        isDestroyed = true;
    }
}
