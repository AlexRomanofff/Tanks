import java.awt.*;

public class Tiger extends AbstraktTank {
	
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

   @Override
	public void destroy () {
			if (getArmor()==1){
				setArmor(0);
				return;
			}else {
				super.destroy();
			}

	}
	public void draw(Graphics g) {
		g.setColor(new Color(0, 255, 0));
		g.fillRect(this.getX(), this.getY(), 64, 64);

		g.setColor(new Color(255, 0, 0));
		if (this.getDirection() == Direction.UP) {
			g.fillRect(this.getX() + 20, this.getY(), 24, 34);
		} else if (this.getDirection() == Direction.DOWN) {
			g.fillRect(this.getX() + 20, this.getY() + 30, 24, 34);
		} else if (this.getDirection() == Direction.LEFT) {
			g.fillRect(this.getX(), this.getY() + 20, 34, 24);
		} else {
			g.fillRect(this.getX() + 30, this.getY() + 20, 34, 24);
		}
	}
}
