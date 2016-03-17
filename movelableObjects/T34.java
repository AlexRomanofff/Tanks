package movelableObjects;

import fieldObjects.BattleField;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class T34 extends AbstraktTank{


    public T34(BattleField bf) {
        super(bf);
        setImages();
    }


    public T34(BattleField bf, int x, int y, Direction direction) {
        super(bf, x, y, direction);
        setImages();
    }

    private void setImages() {
        images = new Image[4];
        try {
            images[0] = ImageIO.read(new File("T34_up.png").getAbsoluteFile());
            images[1] = ImageIO.read(new File("T34_down.png").getAbsoluteFile());
            images[2] = ImageIO.read(new File("T34_left.png").getAbsoluteFile());
            images[3] = ImageIO.read(new File("T34_right.png").getAbsoluteFile());
        } catch (IOException e) {
            throw new IllegalStateException("Can't find tank image");

        }
    }


    public void setUp() {
    }


    @Override
    public Action getAction() {
        return null;
    }

}