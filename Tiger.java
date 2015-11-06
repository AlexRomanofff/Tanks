public class Tiger extends Tank {
	
	private int armor=1;
	
	public Tiger  (ActionField af, BattleField bf) {
		super(af,bf);

	}
	
	public Tiger (ActionField af, BattleField bf, int x, int y, Direction direction) {
		super(af, bf, x, y, direction);

	}
	public Tiger (ActionField af, BattleField bf, Direction direction) {
		super(af,bf,direction);

	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public void destroy () {
			if (getArmor()==1){
				setArmor(0);
				return;
			}else {
				super.destroy();
			}

	}
}
