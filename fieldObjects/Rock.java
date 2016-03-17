package fieldObjects;



import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import interfaces.*;

import javax.imageio.ImageIO;


public class Rock extends AbstractBFObject implements Destroyable {
    private final static String IMAGE_NAME = "rock.png";

    public  Rock (int x,int y){
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
