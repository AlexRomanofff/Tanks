public class Tiger extends Tank {
	
	private int armor;
	
	public Tiger  (ActionField af, BattleField bf) {
		super(af,bf);
		armor = 1;
	}
	
	public Tiger (ActionField af, BattleField bf, int x, int y, Direction direction, int armor) {
		super(af, bf, x, y, direction);
		this.armor = armor;
	}
}
