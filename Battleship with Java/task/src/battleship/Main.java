package battleship;

public class Main {
    static final int FIELD_WIDTH = 10;
    static final int FIELD_HEIGHT = 10;
    private static Player currentPlayer, anotherPlayer;

    public static void main(String[] args) {
        currentPlayer = new Player(FIELD_HEIGHT, FIELD_WIDTH, "Player 1");
        anotherPlayer = new Player(FIELD_HEIGHT, FIELD_WIDTH, "Player 2");
        currentPlayer.initShips();
        anotherPlayer.initShips();

        boolean isFinish = false;
        while (!isFinish) {
            anotherPlayer.print(true);
            System.out.println("---------------------");
            currentPlayer.print(false);
            isFinish = currentPlayer.turn(anotherPlayer);
            switchPlayer();
        }
    }

    private static void switchPlayer() {
        Player temp = currentPlayer;
        currentPlayer = anotherPlayer;
        anotherPlayer = temp;
    }

}
