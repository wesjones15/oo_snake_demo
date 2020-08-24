package game.snake;

public class Application {
    public static void main(String[] args) {
        int size = 5;
        Game game = new Game(size,size,2,2);
        game.run();
    }
}
