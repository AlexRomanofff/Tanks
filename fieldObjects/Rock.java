package fieldObjects;



import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

import interfaces.*;

import javax.imageio.ImageIO;


public class Rock extends AbstractBFObject implements Destroyable {
    private final static String IMAGE_NAME = "rock.png";

    public  Rock (int x,int y) throws Exception {
        super(x,y);
        setImage(ImageIO.read(new File(IMAGE_NAME)));
    }

    @Override
    public void draw(Graphics g) {
      super.draw(g);
    }
}
