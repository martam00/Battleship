package battleship;

import java.util.Arrays;

public class Player extends ShipPlacement {

    private char[][] fieldWithShips = new char[10][10];
    private char[][] fieldWithFog = new char[10][10];
    private char[][] fieldWithoutMissed = new char[10][10];

    public final char[][] SUNK_SHIPS = new char[10][10];

    public Player() {

        for (char[] value : fieldWithShips) {
            Arrays.fill(value, '~');
        }

        for (char[] value : fieldWithFog) {
            Arrays.fill(value, '~');
        }

        for (char[] value : fieldWithoutMissed) {
            Arrays.fill(value, '~');
        }
    }

    public char[][] getFieldWithShips() {
        return fieldWithShips;
    }

    public char[][] getFieldWithFog() {
        return fieldWithFog;
    }

    public char[][] getFieldWithoutMissed() {
        return fieldWithoutMissed;
    }

    public char[][] createFieldWithSunkShips(char[][] fieldWithShips) {

        for (int i = 0; i < fieldWithShips.length; i++) {
            for (int j = 0; j < fieldWithShips[i].length; j++) {
                if (fieldWithShips[i][j] == 'O') {
                    SUNK_SHIPS[i][j] = 'X';
                } else {
                    SUNK_SHIPS[i][j] = '~';
                }
            }
        }

        return SUNK_SHIPS;
    }

}
