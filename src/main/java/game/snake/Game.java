package game.snake;

import game.Segment;
import java.util.Calendar;
import java.util.Scanner;

public class Game {
    private Integer boardLengthX;
    private Integer boardLengthY;
    private Snake snake;
    private SnakeBehavior snakeBehavior;
    private static final Integer gameTick = 100;
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    private int currentZigRow = 0;

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
//                game.Segment direction = getDirectionFromUserInput();
                Direction direction = getDirectionFromAutoGuess();
                snake = snakeBehavior.move(snake, direction);
                if (snake.getSnakeSize() >= (boardLengthY*boardLengthY)-1) {
                    snake.setSnakeSize(snake.getSnakeSize()+1);
                    snake.setGameOver(true);
                }
            }


        }
        System.out.println("game.snake.Game Over!");
        int finalScore = (snake.getSnakeSize()*100) / (boardLengthX*boardLengthY);
        System.out.println("Score: "+finalScore+"%");
    }

    public void displaySnakeOnBoard() {
//        System.out.println(game.snake.toString());
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < boardLengthY; i++) {
            for (int j = boardLengthX-1; j >= 0; j--) {
                String thing;
                Segment location = new Segment(j,i);
                if (snakeBehavior.checkCollisionWithSnake(this.snake, location) && this.snake.getIndexOfSnakeSegment(location) % 2 == 0){//location.getOccupied(this.game.snake.getHeadOfBody())) {
                    thing = ANSI_GREEN+"S"+ANSI_RESET;
                } else if (snakeBehavior.checkCollisionWithSnake(this.snake, location) && this.snake.getIndexOfSnakeSegment(location) % 2 != 0) {
                    thing = ANSI_BLUE+"S"+ANSI_RESET;
                } else if (snakeBehavior.getFood().getOccupied(location)) {
                    thing = ANSI_RED+"F"+ANSI_RESET;
                } else {
                    thing = ANSI_WHITE+"O"+ANSI_RESET;
                }
                out.append(thing+" ");
                //if game.snake in spot: S
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

    public Direction getDirectionFromAutoGuess() {
        Direction output;

        if (snake.getSnakeSize() < boardLengthX || zigSwitch){
            output = determineBestPathToLocation(snakeBehavior.getFood());
            zigSwitch=false;
        } else {
//            if (boardLengthX % 2 == 0) {
            output = executeZigZagPatternEven();
//            } else {
//                output = executeZigZagPatternOdd();
//            }
        }
        return output;
    }

    private int y_loc;
    private boolean zigSwitch = false;
    private int zigTrips = 0;

    public Direction executeZigZagPatternOdd() {
        int board_size_diff = (boardLengthX % 2 == 0) ? 1 : 0;
        y_loc = (currentZigRow % 2 == 0 ? 1 - board_size_diff : boardLengthY - 1 - board_size_diff);

        //need to only update target block if zig row has changed
        Segment targetBlock = new Segment(currentZigRow, y_loc);

        System.out.println("TARG: "+targetBlock.toString());
        System.out.println("HEAD: "+snake.getHeadOfBody().toString());

        if (snake.getHeadOfBody().getOccupied(targetBlock)) {
            if (!zigSwitch ||boardLengthX % 2 == 0) {
                currentZigRow += 1;
            } else {
                zigSwitch = false;
//                if (y_loc == boardLengthY - 1 - board_size_diff) y_loc = 1 - board_size_diff;
//                else y_loc = boardLengthY - 1 - board_size_diff;
            }

            if (currentZigRow > boardLengthX-1) {
                currentZigRow = 0;
                zigSwitch = true;
//                y_loc = (currentZigRow % 2 == 0 ? 1-board_size_diff : boardLengthY-1-board_size_diff);
//                targetBlock.setPosY(y_loc);
                targetBlock.setPosY((boardLengthX % 2 == 0) ? 0 : boardLengthY-1);
//                targetBlock.setPosY(boardLengthY-1-board_size_diff-y_loc);
                System.out.println("reached end of board exit");
                return determineBestPathToLocation(targetBlock);
            }
            System.out.println("Triggered Recursive Zig");
            zigTrips += 1;
            return executeZigZagPatternOdd();
        }
        System.out.println("standard exit");
        if (zigTrips >= 2 && boardLengthX % 2 != 0) {

            targetBlock.setPosY(boardLengthY-2);
        }
        return determineBestPathToLocation(targetBlock);

    }

    public Direction executeZigZagPatternEven() {
//        System.out.println("# zigTrips: " + zigTrips);
        int board_size_diff = (boardLengthX % 2 == 0) ? 1 : 0;
        int y_loc = (currentZigRow % 2 == 0 ? 1 - board_size_diff : boardLengthY - 1 - board_size_diff);
        Segment targetBlock = new Segment(currentZigRow, y_loc);

        if (snake.getHeadOfBody().getOccupied(targetBlock)) {
            currentZigRow += 1;

            if (currentZigRow > boardLengthX-1) {
                currentZigRow = 0;
                targetBlock.setPosY((boardLengthX % 2 == 0) ? 0 : boardLengthY-1);
                return determineBestPathToLocation(targetBlock);
            }
            zigTrips = zigTrips + 1;
            return executeZigZagPatternEven();
        }
        if (zigTrips >= 1) {
            zigTrips = 0;
            zigSwitch = true;
            return determineBestPathToLocation(snakeBehavior.getFood());
        }
        zigTrips = 0;
        return determineBestPathToLocation(targetBlock);
    }

    public Direction determineBestPathToLocation(Segment targetLocation) {
        Direction output;
        Direction currentDirection = snake.getSnakeDirection();
        Direction targetDirection = snakeBehavior.getRelativeDirection(snake, targetLocation);
        Segment tempTargetPosition = snakeBehavior.getTempSnakeFuturePosition(snake.getHeadOfBody(),targetDirection);
        Segment tempCurrDirPosition = snakeBehavior.getTempSnakeFuturePosition(snake.getHeadOfBody(), currentDirection);
        if (!snakeBehavior.checkCollisionWithSnake(snake, tempTargetPosition) && !snakeBehavior.checkCollisionWithBoundary(tempTargetPosition)) {
            output = targetDirection;
        } else if (!snakeBehavior.checkCollisionWithSnake(snake, tempCurrDirPosition) && !snakeBehavior.checkCollisionWithBoundary(tempCurrDirPosition)) {
            output = currentDirection;
        } else {
            currentDirection = currentDirection.getPerpendicularDirection();
            Segment newTempNewPosition = snakeBehavior.getTempSnakeFuturePosition(snake.getHeadOfBody(), currentDirection);
            if (snakeBehavior.checkCollisionWithSnake(snake, newTempNewPosition) || snakeBehavior.checkCollisionWithBoundary(newTempNewPosition)) {
                currentDirection = currentDirection.getOppositeDirection();
            }
            output = currentDirection;
        }
        return output;
    }
    //add support for case where
}
