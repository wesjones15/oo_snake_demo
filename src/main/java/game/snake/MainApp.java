package game.snake;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class MainApp extends JFrame {

    public MainApp() {

        initUI();
    }

    private void initUI() {

        add(new GameGui());

        setResizable(false);
        pack();

        setTitle("game.snake.Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new MainApp();
            ex.setVisible(true);
        });
    }
}