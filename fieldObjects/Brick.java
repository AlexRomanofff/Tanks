package fieldObjects;

import interfaces.*;

import java.awt.*;

/**
 * Created by Алекс on 16.11.2015.
 */
public class Brick extends AbstractBFObject implements Destroyable, Drawable {

    public  Brick (int x,int y) {
        super(x,y);

    }
    @Override
    public void draw (Graphics g) {
        g.setColor(Color.BLUE);
        super.draw(g);

    }

    public void destroy () {
        setX(-100);
        setY(-100);
    }
}
