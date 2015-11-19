package movelableObjects;

import java.awt.*;
import engine.*;
import fieldObjects.BattleField;

public class Tiger extends AbstraktTank {
	
	private int armor=1;
	
	public Tiger  (ActionField af, BattleField bf) {
		super(af,bf);
		setTankColor(new Color(0, 255, 0));
		setTowerColor(new Color(255, 0, 0));

	}
	
	public Tiger (ActionField af, BattleField bf, int x, int y, Direction direction) {
		super(af, bf, x, y, direction);
		setTankColor(new Color(0, 255, 0));
		setTowerColor(new Color(255, 0, 0));
	}
	public Tiger (ActionField af, BattleField bf, Direction direction) {
		super(af,bf,direction);
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
	public void destroy () {
			if (getArmor()==1){
				setArmor(0);
				return;
			}else {
				super.destroy();
			}

	}

}
