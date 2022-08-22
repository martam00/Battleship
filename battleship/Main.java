package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static ShipPlacement ship = new ShipPlacement();
    public static Scanner scanner = new Scanner(System.in);

    public static String[] AIRCRAFT_COORDINATES_PLAYER1 = new String[5];
    public static String[] BATTLESHIP_COORDINATES_PLAYER1 = new String[4];
    public static String[] SUBMARINE_COORDINATES_PLAYER1 = new String[3];
    public static String[] CRUISER_COORDINATES_PLAYER1 = new String[3];
    public static String[] DESTROYER_COORDINATES_PLAYER1 = new String[2];

    public static String[] AIRCRAFT_COORDINATES_PLAYER2 = new String[5];
    public static String[] BATTLESHIP_COORDINATES_PLAYER2 = new String[4];
    public static String[] SUBMARINE_COORDINATES_PLAYER2 = new String[3];
    public static String[] CRUISER_COORDINATES_PLAYER2 = new String[3];
    public static String[] DESTROYER_COORDINATES_PLAYER2 = new String[2];

    public static Player player1 = new Player();
    public static Player player2 = new Player();

    public static void main(String[] args) {


        System.out.println("Player 1, place your ships to the game field");
        saveCoordinates(1, player1.getFieldWithShips());

        clearScreen();

        System.out.println("Player 2, place your ships to the game field");
        saveCoordinates(2, player2.getFieldWithShips());

        clearScreen();

        char[][] sunkShipsPlayer1 = player1.createFieldWithSunkShips(player1.getFieldWithShips());
        char[][] sunkShipsPlayer2 = player2.createFieldWithSunkShips(player2.getFieldWithShips());

        while (true) {

            ship.printGame(player2.getFieldWithFog(), player1.getFieldWithShips());
            if (game(1, 2, player2.getFieldWithShips(), player2.getFieldWithFog(), player2.getFieldWithoutMissed(), sunkShipsPlayer2)) {
                break;
            }
            clearScreen();

            ship.printGame(player1.getFieldWithFog(), player2.getFieldWithShips());
            if (game(2, 1, player1.getFieldWithShips(), player1.getFieldWithFog(), player1.getFieldWithoutMissed(), sunkShipsPlayer1)) {
                break;
            }
            clearScreen();
        }

    }

    public static String[] putShip(String name, char[][] field, int size) {
        String firstCoordinate;
        String secondCoordinate;
        String[] coordinates = new String[2];

        // prompt user for an input
        System.out.println("Enter the coordinates of the " + name + " (" + size + " cells): ");
        boolean flag = false;
        while (!flag) {
            System.out.print("> ");
            // save coordinate provided by user input
            firstCoordinate = scanner.next();
            secondCoordinate = scanner.next();

            // quit loop if user input is valid
            if (ship.positionShip(field, size, firstCoordinate, secondCoordinate)) {
                coordinates[0] = firstCoordinate;
                coordinates[1] = secondCoordinate;
                flag = true;
            }
        }
        ship.printField(field);
        return coordinates;
    }

    public static boolean game(int player, int anotherPlayer, char[][] fieldWithShips, char[][] fieldWithFog, char[][] fieldWithoutMissed, char[][] SUNK_SHIPS) {

        String coordinate;
        System.out.println("Player " + player + ", it's your turn");
        boolean flag = false;
        while (!flag) {
            System.out.print("> ");
            // save coordinate provided by user input
            coordinate = scanner.next();

            int coordinateLetter = coordinate.charAt(0) - 65;
            int coordinateNumber = coordinate.charAt(1) - 49;

            // check if the length of the coordinate is 3 and number is 10 (F10)
            if (coordinate.length() == 3) {
                if(coordinate.charAt(1) == '1' && coordinate.charAt(2) == '0') {
                    coordinateNumber = 9;
                } else {
                    System.out.println("Error! You entered the wrong coordinates! Try again: ");
                    continue;
                }
            }

            // check if the coordinates are valid
            if (!ship.validInput(fieldWithShips, coordinateLetter, coordinateNumber)) {
                System.out.println("Error! You entered the wrong coordinates! Try again: ");
                continue;
            }

            if (fieldWithShips[coordinateLetter][coordinateNumber] == 'O'  || fieldWithShips[coordinateLetter][coordinateNumber] == 'X') {
                fieldWithShips[coordinateLetter][coordinateNumber] = 'X';
                fieldWithFog[coordinateLetter][coordinateNumber] = 'X';
                fieldWithoutMissed[coordinateLetter][coordinateNumber] = 'X';
                printIfSunkShip(fieldWithFog, anotherPlayer);
            } else {
                fieldWithShips[coordinateLetter][coordinateNumber] = 'M';
                fieldWithFog[coordinateLetter][coordinateNumber] = 'M';
                System.out.println("You missed!");
            }

            if (Arrays.deepEquals(SUNK_SHIPS, fieldWithoutMissed)) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                return true;
            }
            flag = true;
        }
        return false;

    }

    public static boolean isSunk(String[] coordinates, char[][] fieldWithFog) {

        String firstCoordinate = coordinates[0];
        String secondCoordinate = coordinates[1];

        int firstCoordinateLetter = firstCoordinate.charAt(0) - 65;
        int firstCoordinateNumber = firstCoordinate.charAt(1) - 49;

        // check if the length of the coordinate is 3 and number is 10 (F10)
        if (firstCoordinate.length() == 3) {
            if(firstCoordinate.charAt(1) == '1' && firstCoordinate.charAt(2) == '0') {
                firstCoordinateNumber = 9;
            }
        }

        int secondCoordinateLetter = secondCoordinate.charAt(0) - 65;
        int secondCoordinateNumber = secondCoordinate.charAt(1) - 49;
        if (secondCoordinate.length() == 3) {
            if(secondCoordinate.charAt(1) == '1' && secondCoordinate.charAt(2) == '0') {
                secondCoordinateNumber = 9;
            }
        }

        // check if first letter is bigger or smaller and replace them if necessary
        if (firstCoordinateLetter > secondCoordinateLetter) {
            int temp = firstCoordinateLetter;
            firstCoordinateLetter = secondCoordinateLetter;
            secondCoordinateLetter = temp;
        }

        if (firstCoordinateNumber > secondCoordinateNumber) {
            int temp = firstCoordinateNumber;
            firstCoordinateNumber = secondCoordinateNumber;
            secondCoordinateNumber = temp;
        }
        int count = 0;

        if (firstCoordinateLetter == secondCoordinateLetter) {
            int size = (secondCoordinateNumber - firstCoordinateNumber) + 1;
            for (int i = firstCoordinateNumber; i <= secondCoordinateNumber; i++) {
                if (fieldWithFog[firstCoordinateLetter][i] == 'X') {
                    count++;
                }
            }
            return count == size;
        } else if (firstCoordinateNumber == secondCoordinateNumber) {
            int size = (secondCoordinateLetter - firstCoordinateLetter) + 1;
            for (int i = firstCoordinateLetter; i <= secondCoordinateLetter; i++) {
                if (fieldWithFog[i][firstCoordinateNumber] == 'X') {
                    count++;
                }
            }
            return count == size;
        }
        return false;

    }

    public static void printIfSunkShip(char[][] fieldWithFog, int player) {

        if (player == 1) {
            if (isSunk(AIRCRAFT_COORDINATES_PLAYER1, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else if (isSunk(BATTLESHIP_COORDINATES_PLAYER1, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else if (isSunk(SUBMARINE_COORDINATES_PLAYER1, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else if (isSunk(CRUISER_COORDINATES_PLAYER1, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else if (isSunk(DESTROYER_COORDINATES_PLAYER1, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You hit a ship!");
            }
        } else if (player == 2) {
            if (isSunk(AIRCRAFT_COORDINATES_PLAYER2, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else if (isSunk(BATTLESHIP_COORDINATES_PLAYER2, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else if (isSunk(SUBMARINE_COORDINATES_PLAYER2, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else if (isSunk(CRUISER_COORDINATES_PLAYER2, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else if (isSunk(DESTROYER_COORDINATES_PLAYER2, fieldWithFog)) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You hit a ship!");
            }
        }


    }

    public static void saveCoordinates(int player, char[][] fieldWithShips) {
        ship.printField(fieldWithShips);

        if (player == 1) {
            AIRCRAFT_COORDINATES_PLAYER1 = putShip("Aircraft Carrier", fieldWithShips, 5);

            BATTLESHIP_COORDINATES_PLAYER1 = putShip("Battleship", fieldWithShips, 4);

            SUBMARINE_COORDINATES_PLAYER1 = putShip("Submarine", fieldWithShips, 3);

            CRUISER_COORDINATES_PLAYER1 = putShip("Cruiser", fieldWithShips, 3);

            DESTROYER_COORDINATES_PLAYER1 = putShip("Destroyer",fieldWithShips, 2);

        } else if (player == 2) {

            AIRCRAFT_COORDINATES_PLAYER2 = putShip("Aircraft Carrier", fieldWithShips, 5);

            BATTLESHIP_COORDINATES_PLAYER2 = putShip("Battleship", fieldWithShips, 4);

            SUBMARINE_COORDINATES_PLAYER2 = putShip("Submarine", fieldWithShips, 3);

            CRUISER_COORDINATES_PLAYER2 = putShip("Cruiser", fieldWithShips, 3);

            DESTROYER_COORDINATES_PLAYER2 = putShip("Destroyer",fieldWithShips, 2);
        }
    }

    public static void clearScreen() {
        scanner.nextLine();

        System.out.println("Press Enter and pass the move to another player");
        if (scanner.nextLine().equals("")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

}

