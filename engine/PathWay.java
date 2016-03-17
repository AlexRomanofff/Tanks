package engine;

import fieldObjects.*;

import java.util.Stack;

public class PathWay {
    String endCoord;
    String startCoord;
    int startX ;
    int startY;
    int endX ;
    int endY;
    private final static int FREE_WAY = -1;
    private final static int STEP = 1;
    private int startPoint = 1;
    private int[][] field = new int[9][9];


    public PathWay(AbstractBFObject[][]bf, String myCoord, String coordOpponent) {
        fillArray(bf);
        startCoord = myCoord;
        endCoord = coordOpponent;
        setTankCoord(startCoord);
        setOpponentCoord(endCoord);
        field[startY][startX]= startPoint;

    }


    private void setOpponentCoord(String coordOpponent) {
        endY = Integer.parseInt(coordOpponent.substring(0,1));
        endX = Integer.parseInt(coordOpponent.substring(2));
    }

    private void setTankCoord(String myCoord) {
        startY = Integer.parseInt(myCoord.substring(0,1));
        startX=Integer.parseInt(myCoord.substring(2));
    }


    private void fillArray(AbstractBFObject[][] bf) {
        for(int i=0; i<bf.length; i++) {
            for (int k=0; k<bf.length; k++) {
                if (bf[i][k] instanceof Brick || bf[i][k] instanceof Empty|| bf[i][k] instanceof Eagle) {
                    field[i][k]=-1;
                }else {
                    field[i][k]=0;
                }
            }
        }
    }

    public void setPathWay() {

        int start = startPoint;

        while (field[endY][endX]==-1) {

            for (int v = 0; v < 9; v++) {
                for (int h = 0; h < 9; h++) {

                    if (field[v][h]==start) {
                        try {
                            if (field[v+ STEP][h] == FREE_WAY) {
                                field[v+ STEP][h] = start+ STEP;
                            }
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            //ignore
                        }
                        try {
                            if (field[v][h + STEP] == FREE_WAY) {
                                field[v][h + STEP] = start+ STEP;
                            }
                        } catch (ArrayIndexOutOfBoundsException ex) {
                             //ignore
                        }
                        try {
                            if (field[v - STEP][h] == FREE_WAY) {
                                field[v - STEP][h] = start+ STEP;
                            }
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            //ignore
                        }
                        try {
                            if (field[v][h - STEP] == FREE_WAY) {
                                field[v][h - STEP] = start+ STEP;
                            }
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            //ignore
                        }

                    }
                }

            }start++;
        }
    }
    public Stack<String> getPath () {
        setPathWay();
        Stack<String> way = new Stack<>();
        String endPoint = endCoord;
        String startPoint = startCoord;

        while (!endPoint.equals(startPoint)) {
            int endValue = field[endY][endX];

            endPoint = getWayPoint(endValue);
            way.add(endPoint);
        }
        System.out.println(way.toString());
        way.remove(way.size()-1);

        return way;

    }

    private String getWayPoint(int endValue) {
        String endPoint;
        if (endY<8 && field[endY + 1][endX] == endValue-1) {
            endY = endY + 1;
        } else if ( endY>0 && field[endY - 1][endX] == endValue-1) {
            endY = endY - 1;
        } else if (endX<8 && field[endY][endX + 1] == endValue-1) {
            endX = endX + 1;
        } else if (endX>0 && field[endY][endX - 1] == endValue-1) {
            endX = endX - 1;
        }
        endPoint = getCoord(endY, endX);
        return endPoint;
    }


    private String getCoord (int y, int x) {
        return String.valueOf(y)+"_"+String.valueOf(x);
    }
}

