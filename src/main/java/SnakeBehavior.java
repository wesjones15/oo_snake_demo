import java.util.Random;

public class SnakeBehavior {
//    private Snake snake;
    private Food food; // random x,y pos powerup
    private Segment direction;
    private Integer boardLengthX;
    private Integer boardLengthY;
    private Integer score;

    public SnakeBehavior(int boardLengthX, int boardLengthY) {
        this.boardLengthX = boardLengthX;
        this.boardLengthY = boardLengthY;
//        this.snake = new Snake(0,0);
        this.food = new Food(1,0);
        this.direction = new Segment(1,0);

        this.score = 0;
    }

    public Snake move(Snake snake, Segment direction) {
        // -1 <= (dirX, dirY) <= +1
        Segment tempSnakeFuturePosition = getTempSnakeFuturePosition(snake.getHeadOfBody(), direction);
        if (checkCollisionWithSnake(snake, tempSnakeFuturePosition)
        || checkCollisionWithBoundary(tempSnakeFuturePosition)) {
            snake.setGameOver(true);
            // Game Over
        } else if (tempSnakeFuturePosition.getOccupied(food)) {    // Future Position is Food
            snake.updateHeadPosition(tempSnakeFuturePosition);
            food = getNewFoodLocation(snake);
//            food = getDebugFood(getTempSnakeFuturePosition(tempSnakeFuturePosition,direction));
            increaseScore();
        } else { // Future Position is Empty/Valid
            snake.updateHeadPosition(tempSnakeFuturePosition);
            snake.removeAndUpdateTailPosition();
        }
        return snake;
    }

    public Segment getTempSnakeFuturePosition(Segment head, Segment direction) {
        int newX = head.getPosX() + direction.getPosX();
        int newY = head.getPosY() + direction.getPosY();
        return new Segment(newX, newY);
    }

    public Boolean checkCollisionWithSnake(Snake snake, Segment futurePos) {
        // returns true if collision
        boolean collide = false;
        for (Segment segment: snake.getBody()) {
            if (segment.getOccupied(futurePos)) {
                collide = true;
                break;
            }
        }
        return collide;
    }

    public Boolean checkCollisionWithBoundary(Segment futurePos) {
        // returns true if oob
        return (futurePos.getPosY() >= boardLengthY ||
        futurePos.getPosX() >= boardLengthX ||
        futurePos.getPosY() < 0 ||
        futurePos.getPosX() < 0);
    }

    public Food getNewFoodLocation(Snake currSnake) {
        boolean invalidPosition = true;
        Segment position;
        do {
            position = getRandomPosition();
            invalidPosition = checkCollisionWithSnake(currSnake, position);
        } while (invalidPosition);
        return new Food(position);
    }
    public Food getDebugFood(Segment future) {
        return new Food(future);
    }

    public Segment getRandomPosition() {
        Random random = new Random();
        return new Segment(random.nextInt(boardLengthX), random.nextInt(boardLengthY));
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Segment getDirection() {
        return direction;
    }

    public void setDirection(Segment direction) {
        this.direction = direction;
    }

    public Integer getBoardLengthX() {
        return boardLengthX;
    }

    public void setBoardLengthX(Integer boardLengthX) {
        this.boardLengthX = boardLengthX;
    }

    public Integer getBoardLengthY() {
        return boardLengthY;
    }

    public void setBoardLengthY(Integer boardLengthY) {
        this.boardLengthY = boardLengthY;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void increaseScore() {
        this.score++;
    }

    public Direction getRelativeFoodDirection(Snake snake) {
        Direction snakeDir;
        if (snake.getHeadOfBody().getPosX() != food.getPosX()) {
            int num = (snake.getHeadOfBody().getPosX() - food.getPosX()) * -1;

            snakeDir = new Direction(num / Math.abs(num), 0);
        } else {
            int num = (snake.getHeadOfBody().getPosY() - food.getPosY()) * -1;
            snakeDir = new Direction(0, num/Math.abs(num));
        }
        return snakeDir;
    }
}
