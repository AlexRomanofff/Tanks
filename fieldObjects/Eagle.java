package fieldObjects;

import interfaces.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Eagle extends AbstractBFObject implements Destroyable, Drawable {
    private final static String IMAGE_NAME =  "eagle1.png";

    public  Eagle (int x,int y){
        super(x,y);
        try {
            setImage(ImageIO.read(new File(IMAGE_NAME)));}
        catch (IOException ex) {
            System.out.println( "Image hasn't been found");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), getX(), getY(),
                new ImageObserver() {
                    @Override
                    public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
                        return false;
                    }

                });
    }


}
