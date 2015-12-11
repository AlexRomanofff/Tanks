package fieldObjects;

import interfaces.*;

import java.awt.*;


public class Brick extends AbstractBFObject implements Destroyable, Drawable {

    public  Brick (int x,int y) {
        super(x,y);

    }
    @Override
    public void draw (Graphics g) {
        g.setColor(Color.BLUE);
        super.draw(g);

    }


}
