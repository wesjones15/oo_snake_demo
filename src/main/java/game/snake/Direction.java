package game.snake;

import game.Segment;

public class Direction extends Segment {

    public Direction(int dirX, int dirY){
        super(dirX, dirY);
    }


    public Direction getPerpendicularDirection() {
        if (this.getPosX() != 0) {
            this.setPosX(0);
            this.setPosY(1);
        } else {
            this.setPosX(1);
            this.setPosY(0);
        }
        return this;
    }

    public Direction getOppositeDirection() {
        this.setPosX(this.getPosX()*-1);
        this.setPosY(this.getPosY()*-1);
        return this;
    }

    public String getSimpleDirection() {
        if (this.getPosX() != 0) {
            return (this.getPosX() == 1 ? "left" : "right");
        } else {
            return (this.getPosY() == 1 ? "down" : "up");
        }
    }

    public void setDirectionBySimple(String simpleDirection) {
        if (simpleDirection.equals("up")) {
            this.setPosX(0);
            this.setPosY(-1);
        } else if (simpleDirection.equals("down")) {
            this.setPosX(0);
            this.setPosY(1);
        } else if (simpleDirection.equals("left")) {
            this.setPosX(-1);
            this.setPosY(0);
        } else if (simpleDirection.equals("right")) {
            this.setPosX(1);
            this.setPosY(0);
        }
    }
}
