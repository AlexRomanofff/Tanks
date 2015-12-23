package fieldObjects;



import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

import interfaces.*;

import javax.imageio.ImageIO;


public class Rock extends AbstractBFObject implements Destroyable {
    private final static String IMAGE_NAME = "rock.png";
    private Image rock;
    public  Rock (int x,int y) throws Exception {
        super(x,y);
        rock = ImageIO.read(new File(IMAGE_NAME));
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(rock, getX(),getY(),new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
                return false;
            }

        });
    }
}
