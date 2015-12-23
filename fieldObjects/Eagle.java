package fieldObjects;

import interfaces.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

public class Eagle extends AbstractBFObject implements Destroyable, Drawable {
    private final static String IMAGE_NAME =  "eagle1.png";
    private Image eagle;

    public  Eagle (int x,int y)throws Exception{
        super(x,y);
        eagle = ImageIO.read(new File(IMAGE_NAME));
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(eagle, getX(),getY(),new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
                return false;
            }

        });
    }


}
