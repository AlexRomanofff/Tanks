package fieldObjects;

import java.awt.*;
import java.awt.image.ImageObserver;

import interfaces.*;
import movelableObjects.Tank;


public abstract class AbstractBFObject implements BFObject {

    public AbstractBFObject(int x, int y){
        this.x=x;
        this.y = y;
    }

    private int x;
    private int y;
    private Color color;
    private boolean isDestroyed = false;
    private Image image;
    public final static int SIZE_QUADRANT = 64;

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

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
            g.drawImage(image, getX(),getY(),getX()+SIZE_QUADRANT, getY()+SIZE_QUADRANT, getX(),getY(),getX()+SIZE_QUADRANT, getY()+SIZE_QUADRANT,
                    new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
                    return false;
                }

            });
        }
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void destroy() {
        isDestroyed = true;
    }
}
