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
            int L_X_CONST = X_CONST / 2;
            if (null != tetrisBehavior.getHeldTetrisPiece()) {
                //TODO diaplay held piece to the left of the board
                drawText(g, "Held:", (tetrisBehavior.getHeldTetrisPiece().getAnchorPoint().getPosX()*DOT_SIZE)+L_X_CONST,
                        (tetrisBehavior.getHeldTetrisPiece().getAnchorPoint().getPosY() +7)*DOT_SIZE);
                for (Segment segment : tetrisBehavior.getHeldTetrisPiece().getSegments()) {
                    g.setColor(tetrisBehavior.getHeldTetrisPiece().getColor());
                    g.fillRect((segment.getPosX()*DOT_SIZE)+L_X_CONST, (segment.getPosY()+8)*DOT_SIZE,DOT_SIZE,DOT_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect((segment.getPosX()*DOT_SIZE)+L_X_CONST, (segment.getPosY()+8)*DOT_SIZE,DOT_SIZE,DOT_SIZE);
                }
            }
            //TODO: display next three pieces
            int R_X_CONST = X_CONST * 3 / 2;
            if (tetrisBehavior.getNextThreePieces() != null) {
                for (int i = 0; i < tetrisBehavior.getNextThreePieces().size(); i++) {
                    for (Segment segment : tetrisBehavior.getNextThreePieces().get(i).getSegments()) {
                        drawText(g, "Next up:", (tetrisBehavior.getNextThreePieces().get(i).getAnchorPoint().getPosX()*DOT_SIZE)+R_X_CONST,
                                (tetrisBehavior.getNextThreePieces().get(i).getAnchorPoint().getPosY() +7)*DOT_SIZE);

                        g.setColor(tetrisBehavior.getNextThreePieces().get(i).getColor());
                        g.fillRect((segment.getPosX()*DOT_SIZE)+R_X_CONST, (segment.getPosY() + 8 + (i * 5)) * DOT_SIZE, DOT_SIZE, DOT_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect((segment.getPosX()*DOT_SIZE)+R_X_CONST, (segment.getPosY() + 8 + (i * 5)) * DOT_SIZE, DOT_SIZE, DOT_SIZE);
                    }
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
        tetrisBehavior.run();
        repaint();
    }

    // This replaces getDirectionFromUserInput()
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_SPACE)) {
                tetrisBehavior.actionHold();
            }

            if ((key == KeyEvent.VK_Z)) {
                tetrisBehavior.actionRotateCCW();
            }

            if ((key == KeyEvent.VK_X)) {
                tetrisBehavior.actionRotateCW();
            }

            if ((key == KeyEvent.VK_LEFT)) {
                tetrisBehavior.actionLEFT();
            }

            if ((key == KeyEvent.VK_RIGHT)) {
                tetrisBehavior.actionRIGHT();
            }

            if ((key == KeyEvent.VK_UP)) {
                tetrisBehavior.actionUP();
            }

            if ((key == KeyEvent.VK_DOWN)) {
                tetrisBehavior.actionDOWN();
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

    private void drawText(Graphics g, String text, int x, int y) {
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(text, x, y);
    }
}
