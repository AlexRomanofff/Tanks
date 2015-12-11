package fieldObjects;

import interfaces.*;
import java.awt.*;

public class Eagle extends AbstractBFObject implements Destroyable, Drawable {

    public  Eagle (int x,int y) {
        super(x,y);

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.magenta);
        super.draw(g);
    }


}
