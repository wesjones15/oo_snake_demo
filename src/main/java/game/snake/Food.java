package game.snake;

import game.Segment;

import java.util.Random;

public class Food extends Segment {

    public Food(int posX, int posY) {
        super(posX, posY);
    }

    public Food(Segment segment) {
        super(segment.getPosX(), segment.getPosY());
    }

    public Food getRandomPosition(int boardX, int boardY) {
        Random random = new Random();
        return new Food(random.nextInt(boardX), random.nextInt(boardY));
    }
}
