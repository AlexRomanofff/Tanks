package fieldObjects;


import interfaces.*;
import java.awt.*;

/**
 * Created by Алекс on 19.11.2015.
 */
public class Eagle extends AbstractBFObject implements Destroyable {

    public  Eagle (int x,int y) {
        super(x,y);

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.magenta);
        super.draw(g);
    }

    @Override
    public void destroy() {
        setY(-100);
        setX(-100);
    }
}
