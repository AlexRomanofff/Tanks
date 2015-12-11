package fieldObjects;

import interfaces.Drawable;

import java.awt.*;


public class Empty extends AbstractBFObject implements Drawable{

    public Empty (int x, int y) {
        super(x,y);
    }

    @Override
    public void draw(Graphics g) {
    }
}
