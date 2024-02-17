package battleship;

import static java.lang.Math.*;

class Ship {

    int x, y, length, direction;
    TypeShip typeShip;

    public boolean setCoordinates(String input, TypeShip typeShip) {
        try {
            String[] parts = input.split(" ");
            int y1 = parts[0].charAt(0) - 65;
            int y2 = parts[1].charAt(0) - 65;
            int x1 = Integer.parseInt(parts[0].substring(1)) - 1;
            int x2 = Integer.parseInt(parts[1].substring(1)) - 1;

            if (abs(x2 - x1) > 0 && abs(y2 - y1) > 0) {
                return false;
            } else if (x1 < 0 || x1 > Main.FIELD_WIDTH - 1) {
                return false;
            } else if (x2 < 0 || x2 > Main.FIELD_WIDTH - 1) {
                return false;
            } else if (y1 < 0 || y1 > Main.FIELD_HEIGHT - 1) {
                return false;
            } else if (y2 < 0 || y2 > Main.FIELD_HEIGHT - 1) {
                return false;
            } else {
                x = min(x1, x2);
                y = min(y1, y2);
                length = max(abs(x2 - x1), abs(y2 - y1)) + 1;
                direction = (y1 == y2) ? 0 : 1;
                this.typeShip = typeShip;
                return true;
            }

        } catch (Exception ignored) {
            return false;
        }
    }
}
