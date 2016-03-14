package movelableObjects;

import fieldObjects.*;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Tiger extends AbstraktTank {
	
	private int armor=1;
	private Direction []directions = new Direction[4];
	Stack<String> way;

	
	public Tiger  (BattleField bf) {
		super(bf);
		setImages();

	}
	
	public Tiger (BattleField bf, int x, int y, Direction direction) {
		super(bf, x, y, direction);
		setImages();
	}
	public Tiger (BattleField bf, Direction direction) {
		super(bf,direction);
		setImages();
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

   @Override
   public void destroy() {
	   if (getArmor() == 1) {
		   setArmor(0);
	   } else {
		   super.destroy();
	   }
   }

	public void setUp() {
		bf.aggressorActions.add(getAction());
	}
	@Override
	public void startThread () {
		Thread tigerThread = new Thread(this);
		tigerThread.start();

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

	private void setImages () {
		images = new Image[4];
		try {
			images[0] = ImageIO.read(new File("Tiger_up.png").getAbsoluteFile());
			images[1] = ImageIO.read(new File("Tiger_down.png").getAbsoluteFile());
			images[2] = ImageIO.read(new File("Tiger_left.png").getAbsoluteFile());
			images[3] = ImageIO.read(new File("Tiger_right.png").getAbsoluteFile());
		} catch (IOException e) {
			throw new IllegalStateException("Can't find tank image");

		}
	}

	@Override
	public Action getAction() {

		if (checkPresenceTankOnLine(getOpponent())&& abilityFire(getOpponent())) {
			return setNecessaryDirection();
		} else {

			way = choseShortestWay(getOpponent());
			return moveToNextQuadrant(way.peek());

		}
	}
}
