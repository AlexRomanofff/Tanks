package fieldObjects;

import interfaces.Drawable;

import java.awt.*;

/**
 * Created by Алекс on 19.11.2015.
 */
public class Water extends AbstractBFObject implements Drawable {
    public  Water (int x,int y) {
        super(x,y);

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.cyan);
        super.draw(g);
    }
}
