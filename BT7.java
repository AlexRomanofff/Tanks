public class BT7 extends AbstraktTank {
	
	
	public BT7 (ActionField af, BattleField bf) {
		super(af,bf);
		setSpeed(5);
	}
	
	public BT7 (ActionField af, BattleField bf, int x, int y, Direction direction) {
		super(af, bf, x, y, direction);
		setSpeed(5);
	}

}
