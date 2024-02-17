package battleship;

public enum TypeShip {

    AIRCRAFT_CARRIER("Aircraft Carrier", 5, 1),
    BATTLESHIP("Battleship", 4, 2),
    SUBMARINE("Submarine", 3, 3),
    CRUISER("Cruiser", 3, 4),
    DESTROYER("Destroyer", 2, 5);

    final String name;
    final int length;
    final int index;

    TypeShip(String name, int length, int index) {
        this.name = name;
        this.length = length;
        this.index = index;
    }
}
