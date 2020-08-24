package game.snake;

import game.Segment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameGui extends JPanel implements ActionListener {
    private Integer boardLengthX;
    private Integer boardLengthY;
    private Snake snake;
    private SnakeBehavior snakeBehavior;
    private Direction currentSnakeDirection;

    private final int board_grid_len = 10;

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = B_HEIGHT / board_grid_len;
//    private final int ALL_DOTS = 900;
//    private final int gridx[] = new int[ALL_DOTS];
//    private final int gridy[] = new int[ALL_DOTS];
    private Timer timer;
    private final int DELAY = 250;

    private int currentZigRow = 0;


    public GameGui() {
        this.snakeBehavior = new SnakeBehavior(board_grid_len,board_grid_len);
        this.snake = new Snake(0,0);
        this.boardLengthX = this.snakeBehavior.getBoardLengthX();
        this.boardLengthY = this.snakeBehavior.getBoardLengthY();
        this.currentSnakeDirection = new Direction(0,0);
        this.currentSnakeDirection.setDirectionBySimple("down");
        initBoard();
    }

    public GameGui(int boardX, int boardY, int snakeX, int snakeY) {
        this.snakeBehavior = new SnakeBehavior(boardX,boardY);
        this.snake = new Snake(snakeX, snakeY);
        this.boardLengthX = boardX;
        this.boardLengthY = boardY;

    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        initGame();
    }

    private void initGame() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    // This replaces displaySnakeOnBoard()
    private void doDrawing(Graphics g) {
        if (!snake.getGameOver()) {

            for (int i = 0; i < boardLengthY; i++) {
                for (int j = boardLengthX-1; j >= 0; j--) {
                    Segment location = new Segment(j,i);
                    if (location.getOccupied(this.snake.getHeadOfBody())) {
                        g.setColor(Color.GREEN);
                        g.fillRect(location.getPosX()*DOT_SIZE,location.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
                    } else if (snakeBehavior.checkCollisionWithSnake(this.snake, location)){// && this.game.snake.getIndexOfSnakeSegment(location) % 2 == 0){//location.getOccupied(this.game.snake.getHeadOfBody())) {
                        g.setColor(Color.BLUE);
                        g.fillRect(location.getPosX()*DOT_SIZE,location.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
                    } else if (snakeBehavior.getFood().getOccupied(location)) {
                        g.setColor(Color.RED);
                        g.fillRect(location.getPosX()*DOT_SIZE,location.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
                    } else {
                        g.setColor(Color.BLACK);
                        g.fillRect(location.getPosX()*DOT_SIZE,location.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
                    }
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {
            gameOver(g);
        }
    }

    public Direction getDirectionFromAutoGuess() {
        Direction output;

        if (true){//game.snake.getSnakeSize() < boardLengthX){
            output = determineBestPathToLocation(snakeBehavior.getFood());
        } else {
            output = executeZigZagPatternEven();
        }
        return output;
    }

    public Direction executeZigZagPattern() {
        int board_size_diff = (boardLengthX % 2 == 0) ? 1 : 0;
        int y_loc = (currentZigRow % 2 == 0 ? 1-board_size_diff : boardLengthY-1-board_size_diff);
        Segment targetBlock = new Segment(currentZigRow, y_loc);

        if (snake.getHeadOfBody().getOccupied(targetBlock)) {
            currentZigRow += 1;
            if (currentZigRow > boardLengthX-1) {
                currentZigRow = 0;
//                y_loc = (currentZigRow % 2 == 0 ? 1-board_size_diff : boardLengthY-1-board_size_diff);
//                targetBlock.setPosY(y_loc);
                targetBlock.setPosY((boardLengthX % 2 == 0) ? 0 : boardLengthY-1);
//                targetBlock.setPosY(boardLengthY-1-board_size_diff-y_loc);

                return determineBestPathToLocation(targetBlock);
            }
            return executeZigZagPattern();
        }

        return determineBestPathToLocation(targetBlock);

    }

//    private int zigTrips = 0;
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
//            zigTrips = zigTrips + 1;
            return executeZigZagPatternEven();
        }
//        if (zigTrips >= 1) {
//            zigTrips = 0;
//            return determineBestPathToLocation(snakeBehavior.getFood());
//        }
//        zigTrips = 0;
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



    // This replaces run()
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!snake.getGameOver()) {

            currentSnakeDirection = getDirectionFromAutoGuess();
            snake = snakeBehavior.move(snake, currentSnakeDirection);
            if (snake.getSnakeSize() >= (boardLengthY*boardLengthY)-1) {
                snake.setGameOver(true);
            }
        }

        repaint();
    }

    // This replaces getDirectionFromUserInput()
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!currentSnakeDirection.getSimpleDirection().equals("right"))) {
                currentSnakeDirection.setDirectionBySimple("left");
            }

            if ((key == KeyEvent.VK_RIGHT) && (!currentSnakeDirection.getSimpleDirection().equals("left"))) {
                currentSnakeDirection.setDirectionBySimple("right");
            }

            if ((key == KeyEvent.VK_UP) && (!currentSnakeDirection.getSimpleDirection().equals("down"))) {
                currentSnakeDirection.setDirectionBySimple("up");
            }

            if ((key == KeyEvent.VK_DOWN) && (!currentSnakeDirection.getSimpleDirection().equals("up"))) {
                currentSnakeDirection.setDirectionBySimple("down");
            }
        }
    }

    private void gameOver(Graphics g) {
        int finalScore = ((snake.getSnakeSize()+1)*100) / (boardLengthX*boardLengthY);
        String msg = "Game Over: "+finalScore+"%";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
}
