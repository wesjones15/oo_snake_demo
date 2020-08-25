package game.tetris;

import game.Segment;
import game.tetris.piece.LShapeBlockR;

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
//    private TetrisPiece currentTetrisPiece;


    private final int board_grid_len = 40;

    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 400;
    private final int DOT_SIZE = B_HEIGHT / board_grid_len;

    private Timer timer;
    private final int DELAY = 500;



    public EngineGFX() {

        this.tetrisBehavior = new TetrisBehavior();
//        this.currentTetrisPiece = tetrisBehavior.getNewTetrisPiece();//new LShapeBlockR(tetrisBehavior.getBoardSizeX()/2,-4);
        initBoard();
    }

    public EngineGFX(int x_size, int y_size) {
        this.boardLengthX = x_size;
        this.boardLengthY = y_size;

        this.tetrisBehavior = new TetrisBehavior();
//        this.currentTetrisPiece = new LShapeBlockR(boardLengthX/2,-4);
        initBoard();
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
    //TODO reference currentTetrisPiece from within tetrisBehavior
    private void doDrawing(Graphics g) {
        int X_CONST = (board_grid_len - tetrisBehavior.getBoardSizeX()) /2 *DOT_SIZE;

        if (!tetrisBehavior.isGameOver()) {
            g.setColor(Color.GRAY);
            g.fillRect(0+X_CONST,0,tetrisBehavior.getBoardSizeX()*DOT_SIZE, tetrisBehavior.getBoardSizeY()*DOT_SIZE);


            for (Segment segment : tetrisBehavior.getCurrentTetrisPiece().getSegments()) {
                g.setColor(tetrisBehavior.getCurrentTetrisPiece().getColor());
                g.fillRect(segment.getPosX()*DOT_SIZE+X_CONST, segment.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(segment.getPosX()*DOT_SIZE+X_CONST, segment.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
            }
            for (TetrisSegment segment : tetrisBehavior.getPlacedPieces()) {
                g.setColor(segment.getColor());
                g.fillRect(segment.getPosX()*DOT_SIZE+X_CONST, segment.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(segment.getPosX()*DOT_SIZE+X_CONST, segment.getPosY()*DOT_SIZE,DOT_SIZE,DOT_SIZE);
            }
//            g.setColor(Color.BLACK);
//            g.drawRect(currentTetrisPiece.getAnchorPoint().getPosX()*DOT_SIZE+X_CONST,
//                    currentTetrisPiece.getAnchorPoint().getPosY()*DOT_SIZE,4*DOT_SIZE,4*DOT_SIZE);
            Toolkit.getDefaultToolkit().sync();

        } else {
            onGameOver(g);
        }
    }


    // This replaces run()
    @Override
    public void actionPerformed(ActionEvent e) {
//        if (!tetrisBehavior.isGameOver()) {
//
//            // Add game logic that occurs each tick
//
//            // maybe make this occur at different rate than tick to allow more frequent movement
//            currentTetrisPiece.descend();
//
//            // Check for collision with bottom of screen
//            if (tetrisBehavior.checkCollisionWithScreenBottom(currentTetrisPiece)){
//                tetrisBehavior.addPlacedPieces(currentTetrisPiece);
//                currentTetrisPiece = tetrisBehavior.getNewTetrisPiece();
//                tetrisBehavior.checkForRowClear();
//            }
//
//            // Check for collision with placed pieces
//            if (tetrisBehavior.checkCollisionWithPiece(currentTetrisPiece)) {
//                tetrisBehavior.addPlacedPieces(currentTetrisPiece);
//                currentTetrisPiece = tetrisBehavior.getNewTetrisPiece();
//                tetrisBehavior.checkForRowClear();
//            }
//
//            // Check game over condition
//            tetrisBehavior.checkForGameOver();
//        }
        tetrisBehavior.run();
        repaint();
    }

    // This replaces getDirectionFromUserInput()
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();


            if ((key == KeyEvent.VK_Z)) {
                tetrisBehavior.actionRotateCCW();
//                currentTetrisPiece.rotateCounterClockwise();
            }

            if ((key == KeyEvent.VK_X)) {
                tetrisBehavior.actionRotateCW();
//                currentTetrisPiece.rotateClockwise();
            }

            if ((key == KeyEvent.VK_LEFT)) {
                tetrisBehavior.actionLEFT();
//                currentTetrisPiece = tetrisBehavior.moveTetrisPiece(currentTetrisPiece, -1, 0);
//                currentTetrisPiece.updateAnchorPosition(-1,0);
//                currentTetrisPiece.updateSegmentsRelativeToAnchorPoint();
            }

            if ((key == KeyEvent.VK_RIGHT)) {
                tetrisBehavior.actionRIGHT();
//                currentTetrisPiece = tetrisBehavior.moveTetrisPiece(currentTetrisPiece, 1, 0);
//                currentTetrisPiece.updateAnchorPosition(1,0);
//                currentTetrisPiece.updateSegmentsRelativeToAnchorPoint();
            }

            if ((key == KeyEvent.VK_UP)) {
                //move currentTetrisPiece to bottom of screen and lock
                // pass entire tetrispiece into gethighestpieceincolumn and for each unique x get lowest and
//                int dist = tetrisBehavior.getHighestPlacedPieceInColumn(currentTetrisPiece.getLowestSegment().getPosX()) -5- currentTetrisPiece.getAnchorPoint().getPosY();
//                int dist = tetrisBehavior.getHighestPlacedPieceInColumns(currentTetrisPiece.getColumns())
//                        - 5 - currentTetrisPiece.getAnchorPoint().getPosY();
//                currentTetrisPiece.updateAnchorPosition(0,dist);
//                currentTetrisPiece.updateSegmentsRelativeToAnchorPoint();
                tetrisBehavior.actionUP();
            }

            if ((key == KeyEvent.VK_DOWN)) {
//                currentTetrisPiece = tetrisBehavior.moveTetrisPiece(currentTetrisPiece, 0, 1);
                tetrisBehavior.actionDOWN();
//                currentTetrisPiece.updateAnchorPosition(0,1);
//                currentTetrisPiece.updateSegmentsRelativeToAnchorPoint();
            }
        }
    }

    private void onGameOver(Graphics g) {
        String msg = "Game Over: "+tetrisBehavior.getScore();
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }
}
