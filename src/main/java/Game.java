import java.sql.Time;
import java.util.Calendar;
import java.util.Scanner;

public class Game {
    private Integer boardLengthX;
    private Integer boardLengthY;
    private Snake snake;
    private SnakeBehavior snakeBehavior;
    private static final Integer gameTick = 500;
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Game() {
        this.snakeBehavior = new SnakeBehavior(8,8);
        this.snake = new Snake(0,0);
        this.boardLengthX = this.snakeBehavior.getBoardLengthX();
        this.boardLengthY = this.snakeBehavior.getBoardLengthY();
    }

    public Game(int boardX, int boardY, int snakeX, int snakeY) {
        this.snakeBehavior = new SnakeBehavior(boardX,boardY);
        this.snake = new Snake(snakeX, snakeY);
        this.boardLengthX = boardX;
        this.boardLengthY = boardY;

    }


    public void run() {
        int currMillis = 0;
        int prevMillis = 0;
        while (!snake.getGameOver()) {
            Calendar calendar = Calendar.getInstance();
            currMillis = (int) calendar.getTimeInMillis();
            if (prevMillis == 0 || currMillis - prevMillis > gameTick) {
                prevMillis = currMillis;
                displaySnakeOnBoard();
//                Segment direction = getDirectionFromUserInput();
                Segment direction = getDirectionFromAutoGuess();
                snake = snakeBehavior.move(snake, direction);
            }


        }
        System.out.println("Game Over!");
        System.out.println("Score: "+snake.getSnakeSize());
    }

    public void displaySnakeOnBoard() {
//        System.out.println(snake.toString());
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < boardLengthY; i++) {
            for (int j = boardLengthX-1; j >= 0; j--) {
                String thing;
                Segment location = new Segment (j,i);
                if (snakeBehavior.checkCollisionWithSnake(this.snake, location)) {
                    thing = ANSI_BLUE+"S"+ANSI_RESET;
                } else if (snakeBehavior.getFood().getOccupied(location)) {
                    thing = ANSI_RED+"F"+ANSI_RESET;
                } else {
                    thing = ANSI_WHITE+"O"+ANSI_RESET;
                }
                out.append(thing+" ");
                //if snake in spot: S
                //if food in spot: F
                // if nothing in spot: O
            }
            out.append("\n");
        }
        System.out.println(out);
    }

    public Segment getDirectionFromUserInput() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        Segment direction;
        if (s.equals("w")) {
            direction = new Segment(0,-1);
        } else if (s.equals("a")) {
            direction = new Segment(+1,0);
        } else if (s.equals("s")) {
            direction = new Segment(0,+1);
        } else if (s.equals("d")) {
            direction = new Segment(-1,0);
        } else {
            direction = snake.getSnakeDirection();
        }
        return direction;
    }

    public Segment getDirectionFromAutoGuess() {
        Direction currentDirection = snake.getSnakeDirection();
        Direction foodDirection = snakeBehavior.getRelativeFoodDirection(snake);//snake.getSnakeDirection();
        Segment tempFoodPosition = snakeBehavior.getTempSnakeFuturePosition(snake.getHeadOfBody(),foodDirection);
        Segment tempCurrDirPosition = snakeBehavior.getTempSnakeFuturePosition(snake.getHeadOfBody(), currentDirection);
        if (!snakeBehavior.checkCollisionWithSnake(snake, tempFoodPosition) && !snakeBehavior.checkCollisionWithBoundary(tempFoodPosition)) {
            return foodDirection;
            // food logic
        } else if (!snakeBehavior.checkCollisionWithSnake(snake, tempCurrDirPosition) && !snakeBehavior.checkCollisionWithBoundary(tempCurrDirPosition)) {
            return currentDirection;
        } else {
            currentDirection = currentDirection.getPerpendicularDirection();
            Segment newTempNewPosition = snakeBehavior.getTempSnakeFuturePosition(snake.getHeadOfBody(), currentDirection);
            if (snakeBehavior.checkCollisionWithSnake(snake, newTempNewPosition) || snakeBehavior.checkCollisionWithBoundary(newTempNewPosition)) {
                currentDirection = currentDirection.getOppositeDirection();
            }
            return currentDirection;
        }
    }
    //add support for case where
}
