package battleship;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

class Player {
    int fieldHeight, fieldWidth;
    private final String name;
    private final int[][] field;
    private int aliveShips;
    private static final Scanner sc = new Scanner(System.in);

    Player(int fieldHeight, int fieldWidth, String name) {
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        field = new int[fieldHeight][fieldWidth];
        this.name = name;
        initField();
    }

    boolean turn(Player anotherPlayer) {
        System.out.printf("%s, it's your turn:\n", name);
        String shot = sc.nextLine();
        while (!processShot(shot, anotherPlayer)) {
            shot = sc.nextLine();
        }
        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
        return isFinish(anotherPlayer);
    }

    void initShips() {
        System.out.printf("%s , place your ships on the game field\n", name);
        print(false);
        TypeShip[] typeShips = TypeShip.values();
        for (int i = 0; i < typeShips.length; i++) {
            TypeShip typeShip = typeShips[i];
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", typeShip.name, typeShip.length);
            String input = sc.nextLine();
            Ship ship = new Ship();
            if (ship.setCoordinates(input, typeShip)) {
                if (ship.length == typeShip.length) {
                    if (isPossibleLocate(ship)) {
                        fillField(ship);
                        aliveShips++;
                        print(false);
                    } else {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        i--;
                    }
                } else {
                    System.out.printf("Error! Wrong length of the %s! Try again:\n", typeShip.name);
                    i--;
                }
            } else {
                System.out.println("Error! Wrong ship location! Try again:");
                i--;
            }
        }
        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
    }

    void print(boolean fogOfWar) {
        System.out.print("  ");
        for (int i = 0; i < fieldWidth; i++) {
            System.out.printf("%d ", i + 1);
        }
        System.out.println();

        for (int j = 0; j < fieldHeight; j++) {
            System.out.printf("%c ", j + 65);
            for (int i = 0; i < fieldWidth; i++) {
                System.out.printf("%s ", fogOfWar && field[j][i] > 0 ? printCell(0) : printCell(field[j][i]));
            }
            System.out.println();
        }
    }

    private boolean isFinish(Player anotherPlayer) {
        for (int j = 0; j < fieldHeight; j++) {
            for (int i = 0; i < fieldWidth; i++) {
                if (anotherPlayer.field[j][i] > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean processShot(String shot, Player anotherPlayer) {
        boolean result = true;
        try {
            int y = shot.charAt(0) - 65;
            int x = Integer.parseInt(shot.substring(1)) - 1;
            if (anotherPlayer.field[y][x] > 0 || anotherPlayer.field[y][x] == -1) {
                anotherPlayer.field[y][x] = -1;
                if (isFinish(anotherPlayer)) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                } else if (isSankShip(anotherPlayer)) {
                    System.out.println("You sank a ship!");
                } else {
                    System.out.println("You hit a ship!");
                }
            } else if (anotherPlayer.field[y][x] == 0 || anotherPlayer.field[y][x] == -2) {
                anotherPlayer.field[y][x] = -2;
                System.out.println("You missed.");
            }
        } catch (Exception e) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            result = false;
        }
        return result;
    }
    private boolean isSankShip(Player anotherPlayer) {
        Set<Integer> ships = new HashSet<>();
        for (int j = 0; j < fieldHeight; j++) {
            for (int i = 0; i < fieldWidth; i++) {
                if (anotherPlayer.field[j][i] > 0) {
                    ships.add(anotherPlayer.field[j][i]);
                }
            }
        }
        if (ships.size() < anotherPlayer.aliveShips) {
            anotherPlayer.aliveShips--;
            return true;
        } else {
            return false;
        }
    }

    private void initField() {
        for (int j = 0; j < fieldHeight; j++) {
            for (int i = 0; i < fieldWidth; i++) {
                field[j][i] = 0;
            }
        }
    }

    private void fillField(Ship ship) {
        int currentX = ship.x;
        int currentY = ship.y;
        for (int i = 0; i < ship.length; i++) {
            field[currentY][currentX] = ship.typeShip.index;
            if (ship.direction == 0) {
                currentX++;
            } else {
                currentY++;
            }
        }
    }

    private boolean isPossibleLocate(Ship ship) {
        int x = ship.x;
        int y = ship.y;
        for (int i = 0; i < ship.length; i++) {
            if (ship.direction == 0) {
                x = ship.x + i;
            } else {
                y = ship.y + i;
            }
            if (field[max(y - 1, 0)][max(x - 1, 0)] > 0
                    || field[max(y - 1, 0)][x] > 0
                    || field[max(y - 1, 0)][min(x + 1, fieldWidth - 1)] > 0
                    || field[y][max(x - 1, 0)] > 0
                    || field[y][x] > 0
                    || field[y][min(x + 1, fieldWidth - 1)] > 0
                    || field[min(y + 1, fieldHeight - 1)][max(x - 1, 0)] < 0
                    || field[min(y + 1, fieldHeight - 1)][x] > 0
                    || field[min(y + 1, fieldHeight - 1)][min(x + 1, fieldWidth - 1)] > 0) {
                return false;
            }
        }
        return true;
    }

    private String printCell(int value) {
        if (value > 0) {
            return "O";
        } else if (value == 0) {
            return "~";
        } else if (value == -1) {
            return "X";
        } else {
            return "M";
        }
    }
}
