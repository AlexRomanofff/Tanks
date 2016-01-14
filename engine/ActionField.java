package engine;

import fieldObjects.*;
import interfaces.Drawable;
import movelableObjects.Action;
import movelableObjects.Bullet;
import movelableObjects.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class  ActionField extends JPanel {
	
	private BattleField battleField;
	private Bullet bullet;
	private int agressorID;
	private JFrame frame;
	private boolean isRun = false;


	public ActionField() {
		frame = new JFrame("BATTLE FIELD");
		battleField = new BattleField(agressorID);
		bullet = new Bullet(-100, -100, Direction.LEFT, battleField.getAgressor());

		frame.setLocation(750, 150);
		frame.setMinimumSize(new Dimension(battleField.getBF_WIDTH() + 16, battleField.getBF_HEIGHT() + 38));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(startWindow());

		frame.pack();
		frame.setVisible(true);
	}


	void runTheGame() throws InterruptedException {
		while (true) {
			if (isRun) {
				System.out.println("tanks move");
				if ((battleField.getAgressor().isDestroyed() || battleField.getDefender().isDestroyed() || battleField.getEagle().isDestroyed())) {
					Thread.sleep(1000);
					String winner;
					if (battleField.getAgressor().isDestroyed()) {
						winner = "Defender";
					} else {
						winner = "Agressor";
					}
					displayingEndScreen(winner);

				}
				if (!battleField.getAgressor().isDestroyed() && !battleField.getDefender().isDestroyed() && !battleField.getEagle().isDestroyed()) {
					processAction(battleField.getAgressor().setUp(), battleField.getAgressor());
				}
				if (!battleField.getAgressor().isDestroyed() && !battleField.getDefender().isDestroyed() && !battleField.getEagle().isDestroyed()) {
					processAction(battleField.getDefender().setUp(), battleField.getDefender());
				}
			}
			Thread.sleep(10);
		}
	}

	private void displayingEndScreen(String winner) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(endGame(winner));
		frame.setVisible(true);
		frame.pack();
		isRun = false;
	}

	private void processAction(Action a, Tank t) throws InterruptedException {
		if (a == Action.MOVE) {
			processMove(t);
		} else if (a == Action.FIRE) {
			processFire(t.fire());
		} else if (a == Action.TURNING){
			processTurn();
		}
	}
	private boolean processInterception(Bullet bullet)  {

		String quadrantBullet = battleField.getQuadrant(bullet.getX(), bullet.getY());
		int separator = quadrantBullet.indexOf("_");
		int y = Integer.parseInt(quadrantBullet.substring(0, separator));
		int x = Integer.parseInt(quadrantBullet.substring(separator + 1));

		if ((y >= 0 && y < 9) && (x >= 0 && x < 9)) {

              AbstractBFObject fobj = battleField.scanQuadrant(y,x);

			if (fobj instanceof Brick || fobj instanceof Eagle) {
				battleField.updateQuadrant(y, x, new Empty(x*64,y*64));
				fobj.destroy();
				return true;
			}

			if (fobj instanceof Rock) {
				if (bullet.getTank() instanceof T34) {
					battleField.updateQuadrant(y, x,new Empty(x*64,y*64));
					fobj.destroy();
				}return true;
			}

			String tankQuadrant = tankQuadrant(battleField.getAgressor());
			if(checkInterceptionInQuadrant(quadrantBullet, tankQuadrant)) {
				if (bullet.getTank() == battleField.getAgressor()) {
					return false;
				} else {
					battleField.getAgressor().destroy();
					return true;
				}
			}
			tankQuadrant = tankQuadrant(battleField.getDefender());
			if(checkInterceptionInQuadrant(quadrantBullet, tankQuadrant)) {
				if (bullet.getTank() == battleField.getDefender()) {
					return false;
				} else {
					battleField.getDefender().destroy();
					return true;
				}
			}
		}return  false;

	}

	private String tankQuadrant (Tank tank) {
		return battleField.getQuadrant(tank.getX(),tank.getY());
	}


	private boolean checkInterceptionInQuadrant (String quadrantBullet, String destroyableObj) {

		if (destroyableObj.length()>3){
			return false;
		}
		int bulletX = Integer.parseInt(quadrantBullet.substring(2));
		int bulletY = Integer.parseInt(quadrantBullet.substring(0,1));
		int tankX = Integer.parseInt(destroyableObj.substring(2));
		int tankY = Integer.parseInt(destroyableObj.substring(0,1));
		return ((bulletX == tankX)&&(bulletY==tankY));

	}

	public void processFire(Bullet bullet) throws InterruptedException {
		this.bullet = bullet;
		int step = 1;

		while ((bullet.getX() >= -15 && bullet.getX() <= 575)
				&& (bullet.getY() >= -15 && bullet.getY() <= 575)) {

			step = getStepBullet(bullet, step);

			moveBullet(bullet, step);

			if (processInterception(bullet)) {
				bullet.destroy();
				repaint();

				Thread.sleep(bullet.getSpeed());

				if (bullet.isDestroyed()) {
					break;
				}
			}
		}
	}

	private void moveBullet(Bullet bullet, int step) throws InterruptedException {
		if (bullet.getDirection() == Direction.UP || bullet.getDirection() == Direction.DOWN) {

            bullet.updateY(step);

        } else {

            bullet.updateX(step);
        }
		repaint();

		Thread.sleep(bullet.getSpeed());
	}

	private int getStepBullet(Bullet bullet, int step) {
		if (bullet.getDirection() == Direction.UP || bullet.getDirection() == Direction.LEFT) {
            step = -1;
        }
		return step;
	}



	
	public void processMove(Tank tank) throws InterruptedException {

		processTurn();
		int step = getStepTank(tank);

		if (checkRange(tank, tank.getDirection())) {
			return;
		}
		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.DOWN) {

			moveUpDown(step, tank);
			return;
		}
		moveLeftRight(step,tank);

	}

	private int getStepTank(Tank tank) {
		int step;
		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.LEFT) {
			step = -1;
		}else {
			step = 1;
		}
		return step;
	}

	private void moveUpDown(int step, Tank tank) throws InterruptedException {
		int covered = 0;

		while (covered < 64) {

			tank.updateY(step);
			repaint();
			Thread.sleep(tank.getSpeed());

			covered += 1;
		}
	}
	
	private void moveLeftRight(int step, Tank tank) throws InterruptedException  {
		int covered = 0;

		while (covered < 64) {
			tank.updateX(step);
			repaint();
				Thread.sleep(tank.getSpeed());

			covered += 1;
		}

	}
	public Drawable checkNextQuadrant(Tank tank)  {

		String quadrant = battleField.getQuadrant(tank.getX(), tank.getY());

		int step = getStepTank(tank);
		int separator = quadrant.indexOf("_");
		int tankY = Integer.parseInt(quadrant.substring(0, separator));
		int tankX = Integer.parseInt(quadrant.substring(separator + 1));

		if (tank.getDirection() == Direction.UP || tank.getDirection() == Direction.DOWN) {
				tankY = tankY + step;
			} else {
				tankX = tankX + step;
			}

		if (checkTankPresence(tank, tankY, tankX, separator)) return battleField.getDefender();

		return battleField.scanQuadrant(tankY, tankX);

	}

	private boolean checkTankPresence(Tank tank, int tankY, int tankX, int separator) {
		String quadrantOpponent = battleField.getQuadrant(battleField.getAgressor().getX(), battleField.getAgressor().getY());
		if (tank == battleField.getAgressor()) {
			quadrantOpponent = battleField.getQuadrant(battleField.getDefender().getX(), battleField.getDefender().getY());
		}
		if (quadrantOpponent.length()==3) {
			int xOpponent = Integer.parseInt(quadrantOpponent.substring(separator + 1));
			int yOpponent = Integer.parseInt(quadrantOpponent.substring(0, separator));
			if ((tankX == xOpponent) && (tankY == yOpponent)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkRange(Tank tank, Direction direction) {
		return ((tank.getY() <= 0 && direction == Direction.UP)
				|| (tank.getY() >= 512 && direction == Direction.DOWN)
				|| (tank.getX() >= 512 && direction == Direction.RIGHT)
				|| (tank.getX() <= 0 && direction == Direction.LEFT))
				|| !(checkNextQuadrant(tank) instanceof Empty)
				&& !(checkNextQuadrant(tank) instanceof Water);
	}
		
	public void processTurn(){
		repaint();		
	}

	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		battleField.draw(g);
		battleField.getDefender().draw(g);
		battleField.getAgressor().draw(g);
        bullet.draw(g);


	}
	private JPanel startWindow() {
        String[] tanks = {"BT7", "Tiger", "T34"};


        JPanel pan = new JPanel();
        pan.setLayout(new GridBagLayout());

        JLabel choise = new JLabel("Chose the tank");
        pan.add(choise, new GridBagConstraints(0, 0, 1, 1, 0, 0,GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0,0,0,0), 0,0));

        ButtonGroup bg = new ButtonGroup();

        JPanel radioPanel = new JPanel();

        radioPanel.setLayout(new GridLayout(3, 0));
        ActionListener rbListener = new RBListener();

        for(int i=0; i<tanks.length; i++) {
           JRadioButton tank  = new JRadioButton(tanks[i]);

            tank.setActionCommand(String.valueOf(i));
            tank.addActionListener(rbListener);
			if (i==0) {
				tank.setSelected(true);
			}
			bg.add(tank);
			radioPanel.add(tank);

        }

        pan.add(radioPanel, new GridBagConstraints(0, 1, 1, 1, 0, 0,GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0,0,0,0), 0,0));

        JButton ok = new JButton("Start Game");
        pan.add(ok,new GridBagConstraints(0, 2, 1, 1, 0, 0,GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0,0,0,0), 0,0));

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

				frame.getContentPane().removeAll();
				battleField = new BattleField(agressorID);
				isRun = true;
				frame.getContentPane().add(ActionField.this);
//				try {
//					runTheGame();
//				} catch (InterruptedException ex) {
//					ex.printStackTrace();
//				}

				frame.setVisible(true);
				frame.pack();
			}
        });
		return pan;
	}

	private JPanel endGame(String winner) {
		String label = winner+" has won";

		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,30,10,0);
		c.ipadx = 0;
		c.ipady = 0;

		JLabel end = new JLabel(label);
		pan.add(end,c);

		JButton newGame = new JButton("New Game");
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		pan.add(newGame, c);

		JButton exitGame = new JButton("Exit");
		c.gridx = 1;
		pan.add(exitGame ,c);

		exitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agressorID = 0;
				frame.getContentPane().removeAll();
				frame.getContentPane().add(startWindow());
				frame.setVisible(true);
				frame.pack();
			}
		});


		return pan;

	}
    private class RBListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            agressorID = Integer.parseInt(e.getActionCommand());
		}
    }

}


