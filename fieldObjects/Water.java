package fieldObjects;

import interfaces.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

public class Water extends AbstractBFObject implements Drawable {

    private final static String IMAGE_NAME = "water.png";
    private Image water;
    public  Water (int x,int y) throws Exception {
        super(x,y);
        water = ImageIO.read(new File(IMAGE_NAME));
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(water, getX(),getY(),new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
                return false;
            }

        });
    }
}
