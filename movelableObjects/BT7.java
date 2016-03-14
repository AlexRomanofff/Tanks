package movelableObjects;

import fieldObjects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class BT7 extends AbstraktTank implements Runnable{

	private final String EAGLE_QUADRANT = "8_4";
	Stack<String> way;

	public BT7 (BattleField bf) {
		super(bf);
		setSpeed(7);
        setImages();
	}
	
	public BT7 (BattleField bf, int x, int y, Direction direction) {
		super(bf, x, y, direction);
		setSpeed(5);
		setImages();

	}


	public void setUp() {

		bf.aggressorActions.add(getAction());

	}

	@Override
	public void run() {
		while(!isDestroyed()||!bf.getDefender().isDestroyed()) {

			if(bf.aggressorActions.size()==0) {
					setUp();
					System.out.println(bf.aggressorActions.toString());
			}
		}
	}
	@Override
	public void startThread () {
		Thread bt7Thread = new Thread(this);
		bt7Thread.start();

	}

	@Override
	public Action getAction() {

		if ((checkPresenceTankOnLine(EAGLE_QUADRANT)&& abilityFire(EAGLE_QUADRANT))||(checkPresenceTankOnLine(getOpponent())&& abilityFire(getOpponent()))) {
			return setNecessaryDirection();
		}
		return getActionForBT7();
	}

	private Action getActionForBT7() {
		way = choseShortestWay(EAGLE_QUADRANT);
		return moveToNextQuadrant(way.pop());
	}

	private void setImages () {
		images = new Image[4];
		try {
			images[0] = ImageIO.read(new File("BT7_up.png").getAbsoluteFile());
			images[1] = ImageIO.read(new File("BT7_down.png").getAbsoluteFile());
			images[2] = ImageIO.read(new File("BT7_left.png").getAbsoluteFile());
			images[3] = ImageIO.read(new File("BT7_right.png").getAbsoluteFile());
		} catch (IOException e) {
			throw new IllegalStateException("Can't find tank image");

		}
	}
}
