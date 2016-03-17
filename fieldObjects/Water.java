package fieldObjects;


import interfaces.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Water extends AbstractBFObject implements Drawable {

    private final static String IMAGE_NAME = "water.png";

    public  Water (int x,int y) {
        super(x,y);
        try {
            setImage(ImageIO.read(new File(IMAGE_NAME)));}
        catch (IOException ex) {
            System.out.println( "Image hasn't been found");
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
