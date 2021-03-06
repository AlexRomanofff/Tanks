package engine;

import fieldObjects.*;
import interfaces.Drawable;
import movelableObjects.Action;
import movelableObjects.Bullet;
import movelableObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;



public class  ActionField extends JPanel {
	
	private BattleField battleField;
	private Bullet bullet;
	private Bullet bulletAgressor;
	private int agressorID;
	private JFrame frame;
	private File file;
	private List<Action> recordingActions;
	private List<Action> readingActions;

	public ActionField() {
		frame = new JFrame("BATTLE FIELD");
		battleField = new BattleField(agressorID);
		bullet = new Bullet(-100, -100, Direction.UP, battleField.getAgressor());
		bulletAgressor = new Bullet(-100, -100, Direction.LEFT, battleField.getAgressor());

        recordingActions = new ArrayList<>();
        readingActions = new ArrayList<>();
		bulletAgressor.destroy();
		bullet.destroy();

		frame.setLocation(750, 150);
		frame.setMinimumSize(new Dimension(battleField.getBF_WIDTH() + 16, battleField.getBF_HEIGHT() + 38));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(startWindow());



		frame.pack();
		frame.setVisible(true);


		file = new File("recordAction.txt");
		if(file.exists()) {
			createRecordFile();
		}
	}

	private void createRecordFile() {
		file.delete();
		try {
        file.createNewFile();}
        catch (IOException ex) {
            ex.printStackTrace();
        }
	}


	void runTheGame() {
		frame.addKeyListener(createKeyListener());
		frame.setFocusable(true);
		frame.requestFocusInWindow();

		Thread graphicThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isNotDestroy()) {
					sleep(16);
					repaint();


				}

				sleep(500);
				frame.setFocusable(false);
				String winner;
				if (battleField.getAgressor().isDestroyed()) {
					battleField.getDefender().destroy();
					winner = "Defender has won";
				} else {
					battleField.getAgressor().destroy();
					winner = "Agressor has won";
				}
				displayingEndScreen(winner);
			}
		});
		battleField.getAgressor().startThread();

		graphicThread.start();
		agressorThread().start();
		defenderThread();

	}
//			Thread.sleep(10);
//			while (isRecord) {
//				battleField = new BattleField(agressorID);
//				while (isNotDestroy()) {
//					processAction(readingActions.get(0), battleField.getAgressor());
//					readingActions.remove(0);
//					if (isNotDestroy()) {
//						processAction(readingActions.get(0), battleField.getDefender());
//						readingActions.remove(0);

//				}
//				isRecord = false;
//				displayingEndScreen(" ");
//			}
//		}
//	}


	private Thread agressorThread() {

		Thread threadAgressor = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isNotDestroy()) {
					sleep(50);
					if (battleField.aggressorActions.size()>0) {
							try {

								for (int i=0; i<battleField.aggressorActions.size()-1; i++){
									battleField.aggressorActions.remove();
								}
								Action a = battleField.aggressorActions.poll();

								processAction(a, battleField.getAgressor());

							} catch (InterruptedException ex) {
								//ignore
						}
					}
				}
			}
		});
		return threadAgressor;
	}

	private void defenderThread() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (isNotDestroy()) {
                    sleep(16);
					if (battleField.defenderActions.size() > 0) {

						try {

							Action a = battleField.defenderActions.poll();
							processAction(a, battleField.getDefender());

						} catch (InterruptedException ex) {
                           //ignore
						}
					}
				}
			}
		}).start();




	}

	private KeyListener createKeyListener() {
		KeyListener kl = new KeyAdapter() {


			@Override
			public void keyPressed(KeyEvent e) {

				int keyAction = e.getKeyCode();

					if (keyAction == KeyEvent.VK_UP) {
						if (battleField.getDefender().getDirection() == Direction.UP) {
							battleField.defenderActions.add(Action.MOVE);
					} else {
						battleField.defenderActions.add(Action.TURN_UP);
					}


					} else if (keyAction == KeyEvent.VK_DOWN) {
						if (battleField.getDefender().getDirection() == Direction.DOWN) {
							battleField.defenderActions.add(Action.MOVE);
						} else {
							battleField.defenderActions.add(Action.TURN_DOWN);
						}

					} else if (keyAction == KeyEvent.VK_LEFT) {
						if (battleField.getDefender().getDirection() == Direction.LEFT) {
							battleField.defenderActions.add(Action.MOVE);
						} else {
							battleField.defenderActions.add(Action.TURN_LEFT);
						}

					} else if (keyAction == KeyEvent.VK_RIGHT) {
						if (battleField.getDefender().getDirection() == Direction.RIGHT) {
							battleField.defenderActions.add(Action.MOVE);
						} else {
							battleField.defenderActions.add(Action.TURN_RIGHT);
						}

					} else if (keyAction == KeyEvent.VK_SPACE) {
						battleField.defenderActions.add(Action.FIRE);
					}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				int keyAction = e.getKeyCode();

					battleField.defenderActions.clear();
				if (keyAction==KeyEvent.VK_SPACE) {
					battleField.defenderActions.add(Action.FIRE);
				}

			}
		};

		return kl;
	}

	private boolean isNotDestroy() {
		return !battleField.getAgressor().isDestroyed() && !battleField.getDefender().isDestroyed() && !battleField.getEagle().isDestroyed();
	}

	private void displayingEndScreen(String winner) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(endGame(winner));
		frame.setVisible(true);
		frame.pack();

	}


	private void processAction(Action a, Tank t) throws InterruptedException {

		if (a == Action.MOVE) {
			processMove(t);

		} else if (a == Action.FIRE) {
			processFire(t.fire());

		} else { setTurn(a, t);
		}

	}

	private void setTurn(Action a, Tank t) {
		if (a == Action.TURN_UP){
			t.turn(Direction.UP);
//			processTurn();
		}else if (a == Action.TURN_DOWN){
			t.turn(Direction.DOWN);
//			processTurn();
		}else if (a == Action.TURN_LEFT){
			t.turn(Direction.LEFT);
//			processTurn();
		}else if (a == Action.TURN_RIGHT){
			t.turn(Direction.RIGHT);
//			processTurn();
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

	private void processFire(Bullet bullet) {

		if (bullet.getTank() == battleField.getAgressor()) {
			if (this.bulletAgressor.isDestroyed()) {
				bulletAgressor = bullet;
				bulletAgressorThread(bullet).start();
			}
		} else {
			if (this.bullet.isDestroyed()) {
				this.bullet = bullet;
				new Thread(new Runnable() {
					@Override
					public void run() {
						moveBullet(bullet);
					}
				}).start();
			}
		}
	}

	private Thread bulletAgressorThread(final Bullet bullet) {
		return new Thread(new Runnable() {
            @Override
            public void run() {

                moveBullet(bullet);
            }
        });
	}

	private void moveBullet(Bullet bullet) {
		if (bullet!= null) {
			while ((bullet.getX() >= -15 && bullet.getX() <= 575)
					&& (bullet.getY() >= -15 && bullet.getY() <= 575)) {

				int step = getStepBullet(bullet);

				moveBullet(bullet, step);

				if (processInterception(bullet)) {
					bullet.destroy();
					if (bullet.isDestroyed())


					break;
				}
			}
			bullet.destroy();
		}
	}

	private void moveBullet(Bullet bullet, int step){
		if (bullet.getDirection() == Direction.UP || bullet.getDirection() == Direction.DOWN) {

            bullet.updateY(step);

        } else {

            bullet.updateX(step);
        }
//		repaint();

		sleep(bullet.getSpeed());
	}
	private void sleep(long timeout) {
		try {
			Thread.currentThread().sleep(timeout);
		} catch (InterruptedException ex) {

		}
	}

	private int getStepBullet(Bullet bullet) {
		int step  = 1;
		if (bullet.getDirection() == Direction.UP || bullet.getDirection() == Direction.LEFT) {
            step = -1;
        }
		return step;
	}



	
	public void processMove(Tank tank) throws InterruptedException {

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
//			repaint();
			Thread.sleep(tank.getSpeed());

			covered += 1;
		}
	}
	
	private void moveLeftRight(int step, Tank tank) throws InterruptedException  {
		int covered = 0;

		while (covered < 64) {
			tank.updateX(step);
//			repaint();
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
				|| !(checkNextQuadrant(tank) instanceof Empty);
	}
		
	public void processTurn(){
//		repaint();
	}

	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		battleField.draw(g);
		battleField.getDefender().draw(g);
		battleField.getAgressor().draw(g);
		bullet.draw(g);
		bulletAgressor.draw(g);


	}
	private JPanel startWindow() {
        String[] tanks = {"BT7", "Tiger"};


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
				frame.getContentPane().add(ActionField.this);
				frame.setVisible(true);
				frame.pack();
				runTheGame();
			}
        });
		return pan;
	}

	private JPanel endGame(String winner) {
		String label = winner;

		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,10,0);
		c.ipadx = 0;
		c.ipady = 0;

		JLabel end = new JLabel(label);
		pan.add(end,c);

		JButton newGame = new JButton("New Game");
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		pan.add(newGame, c);

		JButton exitGame = new JButton("Exit");
		c.gridx = 2;
		pan.add(exitGame ,c);

		JButton replayGame= new JButton("Replay Game");
		c.gridx = 1;
		pan.add(replayGame, c);

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

        replayGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				recordAction();
				readActions();
				System.out.println(readingActions);
				System.out.println(recordingActions);
				recordingActions.clear();
				frame.getContentPane().removeAll();
				frame.getContentPane().add(ActionField.this);
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
	private void recordAction () {

		try {
			createRecordFile();
			FileOutputStream fos = new FileOutputStream("record_Actions");
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			ObjectOutputStream os = new ObjectOutputStream(bos);
			for (Action action: recordingActions) {
				os.writeObject(action);
			}
			os.flush();
			os.close();

		} catch (IOException iox) {
			iox.printStackTrace();
		}

	}
	private void readActions() {

		try {
			FileInputStream fis = new FileInputStream("record_Actions");
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(bis);

			try {while (bis.available()>0) {
				Action action = (Action) ois.readObject();
				readingActions.add(action);
			  }

				ois.close();

			}catch (ClassNotFoundException ex1) {
                 ex1.printStackTrace();
			}

		} catch (IOException ex){
              ex.printStackTrace();
		}
	}
}


