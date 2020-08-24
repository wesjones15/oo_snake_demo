package game.tetris;

import game.Segment;
import java.util.Calendar;
import java.util.Scanner;

public class Engine {
    // General Game Config
    private Integer boardLengthX;
    private Integer boardLengthY;
    private static final Integer gameTick = 100;

    // Game Specific Config
    private TetrisBehavior tetrisBehavior;
    private TetrisPiece currentTetrisPiece;


    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Engine() {
//        this.snakeBehavior = new SnakeBehavior(8,8);
//        this.snake = new Snake(0,0);
//        this.boardLengthX = this.snakeBehavior.getBoardLengthX();
//        this.boardLengthY = this.snakeBehavior.getBoardLengthY();
    }

//    public Engine(int boardX, int boardY, int snakeX, int snakeY) {
//        this.snakeBehavior = new SnakeBehavior(boardX,boardY);
//        this.snake = new Snake(snakeX, snakeY);
//        this.boardLengthX = boardX;
//        this.boardLengthY = boardY;
//
//    }


    public void run() {
        int currMillis = 0;
        int prevMillis = 0;
        while (true){//!snake.getGameOver()) {
            Calendar calendar = Calendar.getInstance();
            currMillis = (int) calendar.getTimeInMillis();
            if (prevMillis == 0 || currMillis - prevMillis > gameTick) {
                prevMillis = currMillis;

                // Update Display On Screen

                // currentTetrisPiece = tetrisBehavior.applyMovement()

                // currentTetrisPiece.descend

                // Check game over condition
                break;
            }


        }
        // Game Over Action when while loop exits
//        System.out.println("game.snake.Game Over!");
//        int finalScore = (snake.getSnakeSize()*100) / (boardLengthX*boardLengthY);
//        System.out.println("Score: "+finalScore+"%");
    }

    public void displaySnakeOnBoard() {
//        System.out.println(game.snake.toString());
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < boardLengthY; i++) {
            for (int j = boardLengthX-1; j >= 0; j--) {
//                String thing;
//                Segment location = new Segment(j,i);
//                if (snakeBehavior.checkCollisionWithSnake(this.snake, location) && this.snake.getIndexOfSnakeSegment(location) % 2 == 0){//location.getOccupied(this.game.snake.getHeadOfBody())) {
//                    thing = ANSI_GREEN+"S"+ANSI_RESET;
//                } else if (snakeBehavior.checkCollisionWithSnake(this.snake, location) && this.snake.getIndexOfSnakeSegment(location) % 2 != 0) {
//                    thing = ANSI_BLUE+"S"+ANSI_RESET;
//                } else if (snakeBehavior.getFood().getOccupied(location)) {
//                    thing = ANSI_RED+"F"+ANSI_RESET;
//                } else {
//                    thing = ANSI_WHITE+"O"+ANSI_RESET;
//                }
//                out.append(thing+" ");
                //if game.snake in spot: S
                //if food in spot: F
                // if nothing in spot: O
            }
            out.append("\n");
        }
        System.out.println(out);
    }

//    public Segment getDirectionFromUserInput() {
//        Scanner in = new Scanner(System.in);
//        String s = in.nextLine();
//        Segment direction;
//        if (s.equals("w")) {
//            direction = new Segment(0,-1);
//        } else if (s.equals("a")) {
//            direction = new Segment(+1,0);
//        } else if (s.equals("s")) {
//            direction = new Segment(0,+1);
//        } else if (s.equals("d")) {
//            direction = new Segment(-1,0);
//        } else {
//            direction = snake.getSnakeDirection();
//        }
//        return direction;
//    }

}
