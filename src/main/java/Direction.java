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
}
