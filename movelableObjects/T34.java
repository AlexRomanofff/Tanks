package movelableObjects;

import fieldObjects.BattleField;
import engine.ActionField;

import java.awt.*;

public class T34 extends AbstraktTank {

    public T34(BattleField bf) {
        super(bf);
        setTankColor(new Color(255, 0, 0));
        setTowerColor(new Color(0, 255, 0));

    }

    public T34(BattleField bf, int x, int y, Direction direction) {
        super(bf, x, y, direction);
        setTankColor(new Color(255, 0, 0));
        setTowerColor(new Color(0, 255, 0));
    }

    @Override
    public Action setUp() {
        return Action.FIRE;
    }
}


