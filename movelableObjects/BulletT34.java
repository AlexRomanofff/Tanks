package movelableObjects;

import java.awt.*;

/**
 * Created by Алекс on 21.11.2015.
 */
public class BulletT34 extends Bullet {

    public BulletT34 (int x, int y, Direction direction) {
        super(x,y,direction);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, 255, 0));
        g.fillRect(this.getX(), this.getY(), 14, 14);
    }
}
