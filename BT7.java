import java.awt.*;

public class BT7 extends AbstraktTank {
	
	
	public BT7 (ActionField af, BattleField bf) {
		super(af,bf);
		setSpeed(5);
		setTankColor(new Color(120, 120, 0));
		setTowerColor(new Color(200, 100, 50));

	}
	
	public BT7 (ActionField af, BattleField bf, int x, int y, Direction direction) {
		super(af, bf, x, y, direction);
		setSpeed(5);
		setTankColor(new Color(120, 120, 0));
		setTowerColor(new Color(200, 100, 50));

	}

}
