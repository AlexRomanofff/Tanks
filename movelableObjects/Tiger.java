package movelableObjects;

import fieldObjects.BattleField;
import fieldObjects.Empty;

import java.awt.*;


public class Tiger extends AbstraktTank {
	
	private int armor=1;
	
	public Tiger  (BattleField bf) {
		super(bf);
		setTankColor(new Color(0, 255, 0));
		setTowerColor(new Color(255, 0, 0));

	}
	
	public Tiger (BattleField bf, int x, int y, Direction direction) {
		super(bf, x, y, direction);
		setTankColor(new Color(0, 255, 0));
		setTowerColor(new Color(255, 0, 0));
	}
	public Tiger (BattleField bf, Direction direction) {
		super(bf,direction);
		setTankColor(new Color(0, 255, 0));
		setTowerColor(new Color(255, 0, 0));
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
		   return;
	   } else {
		   super.destroy();
	   }
   }

	@Override
	public Action setUp() {

				return Action.MOVE;
			}

}
