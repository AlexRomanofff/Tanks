package fieldObjects;

import interfaces.Destroyable;

import java.awt.*;

/**
 * Created by Алекс on 19.11.2015.
 */
public class Rock extends AbstractBFObject implements Destroyable {

    public  Rock (int x,int y) {
        super(x,y);
    }

    @Override
    public void destroy() {
        setY(-100);
        setX(-100);
    }

    @Override
    public void draw(Graphics g) {
       g.setColor(new Color(0,100,100));
        super.draw(g);
    }
}
