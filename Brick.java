import java.awt.*;

/**
 * Created by Алекс on 16.11.2015.
 */
public class Brick implements Destroyable, Drawable {

    public  Brick () {

    }
    @Override
    public void draw (Graphics g) {
        g.setColor(new Color(255, 0, 0));

    }

    public void destroy () {


    }
}
