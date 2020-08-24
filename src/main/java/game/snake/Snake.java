package game.snake;

import game.Segment;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Segment> body;
    private Integer snakeSize;
    private Segment head;
    private Segment tail;
    private Boolean gameOver;

    public Snake() {
        this.body = new ArrayList<Segment>();
        this.body.add(new Segment(1,1));
        this.snakeSize = this.body.size();
        this.head = this.body.get(this.body.size()-1);
        this.tail = this.body.get(0);
        this.gameOver = false;
    }

    public Snake(int segX, int segY) {
        this.body = new ArrayList<Segment>();
        this.body.add(new Segment(segX,segY));
        this.snakeSize = this.body.size();
        this.head = this.body.get(this.body.size()-1);
        this.tail = this.body.get(0);
        this.gameOver = false;
    }

    public ArrayList<Segment> getBody() {
        return body;
    }

    public void setBody(ArrayList<Segment> body) {
        this.body = body;
    }

    public Integer getSnakeSize() {
        return snakeSize;
    }

    public void setSnakeSize(Integer snakeSize) {
        this.snakeSize = snakeSize;
    }

    public Segment getHead() {
        return this.head;
    }

    public void setHead(Segment head) {
        this.head = head;
    }

    public void updateHeadPosition(Segment head) {
        this.body.add(head);
        this.setSnakeSize(this.body.size());
        this.setHead(head);
    }

    public Segment getHeadOfBody() {
        return this.body.get(this.body.size()-1);
    }

    public Segment getTail() {
        return tail;
    }

    public void setTail(Segment tail) {
        this.tail = tail;
    }

    public void removeAndUpdateTailPosition() {
        this.body.remove(0);
        this.setSnakeSize(this.body.size());
        this.setTail(this.body.get(0));
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("game.snake.Snake Positions:\n");
        for (Segment segment: this.body) {
            out.append(segment.toString());
        }
        return out.toString();
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Direction getSnakeDirection() {
        int dirX;
        int dirY;

        if (this.body.size() >= 2) {
            dirX = this.body.get(this.body.size()-1).getPosX() - this.body.get(this.body.size()-2).getPosX();
            dirY = this.body.get(this.body.size()-1).getPosY() - this.body.get(this.body.size()-2).getPosY();
        } else {
            dirX = 1;
            dirY = 0;
        }
        return new Direction(dirX, dirY);
    }

    public Integer getIndexOfSnakeSegment(Segment location) {
        int index = -1;
        for (int i = 0; i < this.body.size(); i++) {
            if (location.getOccupied(this.body.get(i))) {
                index = i;
                break;
            }
        }
        return index;
    }

}
