package game;

public class Segment<Integer> {
    private int posX;
    private int posY;

    public Segment(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

//    public Segment updateSegmentPosition(int deltaX, int deltaY) {
//        this.posX += deltaX;
//        this.posY += deltaY;
//        return this;
//    }

    public boolean getOccupied(Segment other) {
        return (other.getPosX() == this.getPosX()) && (other.getPosY() == this.getPosY());
    }

    public boolean isWithinOneOf(Segment other) {
        return ((other.getPosY() - this.getPosY()) ==1) && (other.getPosX() == this.getPosX());
    }

    public String toString() {
        return String.format("x: %s\ty: %s\n", getPosX(), getPosY());
    }
}