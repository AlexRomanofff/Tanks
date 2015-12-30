package fieldObjects;

import interfaces.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;


public class Brick extends AbstractBFObject implements Destroyable, Drawable {

    private final static String IMAGE_NAME = "brick.png";

    public  Brick (int x,int y) throws Exception {
        super(x,y);
        setImage(ImageIO.read(new File(IMAGE_NAME)));
    }
    @Override
    public void draw (Graphics g) {
       super.draw(g);
    }


}
