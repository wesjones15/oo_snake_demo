package game.tetris;

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

public class EngineGFX extends JPanel implements ActionListener {
    private Integer boardLengthX;
    private Integer boardLengthY;

    private TetrisBehavior tetrisBehavior;
    private TetrisPiece currentTetrisPiece;


    private final int board_grid_len = 10;

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = B_HEIGHT / board_grid_len;
    //    private final int ALL_DOTS = 900;
//    private final int gridx[] = new int[ALL_DOTS];
//    private final int gridy[] = new int[ALL_DOTS];
    private Timer timer;
    private final int DELAY = 250;



    public EngineGFX() {
        this.tetrisBehavior = new TetrisBehavior();
//        this.snakeBehavior = new SnakeBehavior(board_grid_len,board_grid_len);
//        this.snake = new Snake(0,0);
//        this.boardLengthX = this.snakeBehavior.getBoardLengthX();
//        this.boardLengthY = this.snakeBehavior.getBoardLengthY();
//        this.currentSnakeDirection = new Direction(0,0);
//        this.currentSnakeDirection.setDirectionBySimple("down");
        initBoard();
    }

    public EngineGFX(int boardX, int boardY, int snakeX, int snakeY) {
//        this.snakeBehavior = new SnakeBehavior(boardX,boardY);
//        this.snake = new Snake(snakeX, snakeY);
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
        if (!tetrisBehavior.isGameOver()) {

            for (int i = 0; i < boardLengthY; i++) {
                for (int j = boardLengthX-1; j >= 0; j--) {
                    Segment location = new Segment(j,i);
                    // Draw Tetris Blocks on grid
//                    if (location.getOccupied(this.snake.getHeadOfBody())) {
//                        g.setColor(Color.GREEN);
//                        g.fillRect(location.getPosX()*DOT_SIZE,location.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
//                    } else if (snakeBehavior.checkCollisionWithSnake(this.snake, location)){// && this.game.snake.getIndexOfSnakeSegment(location) % 2 == 0){//location.getOccupied(this.game.snake.getHeadOfBody())) {
//                        g.setColor(Color.BLUE);
//                        g.fillRect(location.getPosX()*DOT_SIZE,location.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
//                    } else if (snakeBehavior.getFood().getOccupied(location)) {
//                        g.setColor(Color.RED);
//                        g.fillRect(location.getPosX()*DOT_SIZE,location.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
//                    } else {
//                        g.setColor(Color.BLACK);
//                        g.fillRect(location.getPosX()*DOT_SIZE,location.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
//                    }
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {
            onGameOver(g);
        }
    }


    // This replaces run()
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!tetrisBehavior.isGameOver()) {
            // Add game logic that occurs each tick

            // currentTetrisPiece = tetrisBehavior.applyMovement()

            // currentTetrisPiece.descend

            // Check game over condition
        }

        repaint();
    }

    // This replaces getDirectionFromUserInput()
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

//            if ((key == KeyEvent.VK_LEFT) && (!currentSnakeDirection.getSimpleDirection().equals("right"))) {
//                currentSnakeDirection.setDirectionBySimple("left");
//            }
//
//            if ((key == KeyEvent.VK_RIGHT) && (!currentSnakeDirection.getSimpleDirection().equals("left"))) {
//                currentSnakeDirection.setDirectionBySimple("right");
//            }
//
//            if ((key == KeyEvent.VK_UP) && (!currentSnakeDirection.getSimpleDirection().equals("down"))) {
//                currentSnakeDirection.setDirectionBySimple("up");
//            }
//
//            if ((key == KeyEvent.VK_DOWN) && (!currentSnakeDirection.getSimpleDirection().equals("up"))) {
//                currentSnakeDirection.setDirectionBySimple("down");
//            }
        }
    }

    private void onGameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
}
