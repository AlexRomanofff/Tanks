import java.awt.*;

/**
 * Created by Алекс on 19.11.2015.
 */
public abstract class AbstractBFObject implements Drawable {

    public AbstractBFObject(int x, int y) {
        this.x=x;
        this.y = y;
    }

    private int x;
    private int y;
    private Color color;

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
        g.fillRect(getX(),getY(),64,64);

    }
}
