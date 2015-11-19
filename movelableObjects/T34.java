package movelableObjects;
import engine.*;
import fieldObjects.BattleField;

import java.awt.*;

public class T34 extends AbstraktTank {

    public T34(ActionField af, BattleField bf) {
        super(af, bf);
        setTankColor(new Color(255, 0, 0));
        setTowerColor(new Color(0, 255, 0));

    }

    public T34(ActionField af, BattleField bf, int x, int y, Direction direction) {
        super(af, bf, x, y, direction);
        setTankColor(new Color(255, 0, 0));
        setTowerColor(new Color(0, 255, 0));

    }

}


