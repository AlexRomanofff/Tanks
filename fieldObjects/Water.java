package fieldObjects;

import interfaces.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

public class Water extends AbstractBFObject implements Drawable {

    private final static String IMAGE_NAME = "water.png";

    public  Water (int x,int y) throws Exception {
        super(x,y);
        setImage(ImageIO.read(new File(IMAGE_NAME)));
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
