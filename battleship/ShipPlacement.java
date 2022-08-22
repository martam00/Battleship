package battleship;

public class ShipPlacement {

    public boolean positionShip(char[][] field, int size, String firstCoordinate, String secondCoordinate) {

        // cast string coordinates into int (starting with 0)
        int firstCoordinateLetter = firstCoordinate.charAt(0) - 65;
        int firstCoordinateNumber = firstCoordinate.charAt(1) - 49;

        // check if the length of the coordinate is 3 and number is 10 (F10)
        if (firstCoordinate.length() == 3) {
            if(firstCoordinate.charAt(1) == '1' && firstCoordinate.charAt(2) == '0') {
                firstCoordinateNumber = 9;
            }
        }

        // check if the coordinates are valid
        if (!validInput(field, firstCoordinateLetter, firstCoordinateNumber)) {
            System.out.println("Error! Wrong ship location! Try again: ");
            return false;
        }

        int secondCoordinateLetter = secondCoordinate.charAt(0) - 65;
        int secondCoordinateNumber = secondCoordinate.charAt(1) - 49;
        if (secondCoordinate.length() == 3) {
            if(secondCoordinate.charAt(1) == '1' && secondCoordinate.charAt(2) == '0') {
                secondCoordinateNumber = 9;
            }
        }
        if (!validInput(field, secondCoordinateLetter, secondCoordinateNumber)) {
            System.out.println("Error! Wrong ship location! Try again: ");
            return false;
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

        // check if user want to put vertical or horizontal ship
        if (firstCoordinateLetter == secondCoordinateLetter) {
            if (secondCoordinateNumber - firstCoordinateNumber == (size - 1)) {
                int count = 0;
                for (int i = firstCoordinateNumber; i <= secondCoordinateNumber; i++) {
                    if (checkPositions(field, firstCoordinateLetter, i)) {
                        count++;
                    }
                }
                // replace ~ with O to show ship you entered
                if (count == size) {
                    for (int i = firstCoordinateNumber; i <= secondCoordinateNumber; i++) {
                        field[firstCoordinateLetter][i] = 'O';
                    }
                } else {
                    System.out.println("Error! You placed it too close to another one! Try again: ");
                    return false;
                }
                return true;
            } else {
                System.out.println("Error! Wrong length of the ship! Try again: ");
                return false;
            }
        } else if (firstCoordinateNumber == secondCoordinateNumber) {
            if (secondCoordinateLetter - firstCoordinateLetter == (size - 1)) {
                int count = 0;
                for (int i = firstCoordinateLetter; i <= secondCoordinateLetter; i++) {
                    if (checkPositions(field, i, firstCoordinateNumber)) {
                        count++;
                    }
                }

                if (count == size) {
                    for (int i = firstCoordinateLetter; i <= secondCoordinateLetter; i++) {
                        field[i][firstCoordinateNumber] = 'O';
                    }
                } else {
                    System.out.println("Error! You placed it too close to another one! Try again: ");
                    return false;
                }
                return true;
            }else {
                System.out.println("Error! Wrong ship location! Try again: ");
                return false;
            }
        }
        System.out.println("Error! Wrong ship location! Try again: ");
        return false;

    }

    public boolean validInput (char[][] field, int firstIndex, int secondIndex) {
        if(firstIndex > field.length - 1 || firstIndex < 0) {
            return false;
        }

        return secondIndex <= field[firstIndex].length - 1 && secondIndex >= 0;
    }

    public static boolean checkPositions (char[][] field, int firstIndex, int secondIndex) {

        if (secondIndex > 0 && firstIndex > 0 && firstIndex < field.length - 1 && secondIndex < field[firstIndex].length - 1) {
            char left = field[firstIndex][secondIndex - 1];
            char right = field[firstIndex][secondIndex + 1];
            char up = field[firstIndex - 1][secondIndex];
            char down = field[firstIndex + 1][secondIndex];

            return left != 'O' && right != 'O' && up != 'O' && down != 'O';
        } else if (firstIndex == 0 && secondIndex == 0) {
            char right = field[firstIndex][secondIndex + 1];
            char down = field[firstIndex + 1][secondIndex];

            return right != 'O' && down != 'O';
        } else if (firstIndex == field.length - 1 && secondIndex == 0) {
            char right = field[firstIndex][secondIndex + 1];
            char up = field[firstIndex - 1][secondIndex];

            return right != 'O' && up != 'O';
        } else if (firstIndex == 0 && secondIndex == field[firstIndex].length - 1) {
            char left = field[firstIndex][secondIndex - 1];
            char down = field[firstIndex + 1][secondIndex];

            return left != 'O' && down != 'O';
        } else if (firstIndex == field.length - 1 && secondIndex == field[firstIndex].length - 1) {
            char up = field[firstIndex - 1][secondIndex];
            char left = field[firstIndex][secondIndex - 1];

            return up != 'O' && left != 'O';
        } else if (firstIndex == 0 && secondIndex > 0 && secondIndex < field[firstIndex].length - 1) {
            char left = field[firstIndex][secondIndex - 1];
            char right = field[firstIndex][secondIndex + 1];
            char down = field[firstIndex + 1][secondIndex];

            return left != 'O' && right != 'O' && down != 'O';
        } else if (firstIndex == field.length - 1 && secondIndex > 0 && secondIndex < field[firstIndex].length - 1) {
            char left = field[firstIndex][secondIndex - 1];
            char right = field[firstIndex][secondIndex + 1];
            char up = field[firstIndex - 1][secondIndex];

            return left != 'O' && right != 'O' && up != 'O';
        } else if (secondIndex == 0 && firstIndex > 0 && firstIndex < field.length - 1) {
            char right = field[firstIndex][secondIndex + 1];
            char up = field[firstIndex - 1][secondIndex];
            char down = field[firstIndex + 1][secondIndex];

            return down != 'O' && right != 'O' && up != 'O';
        } else if (secondIndex == field[firstIndex].length - 1 && firstIndex > 0 && firstIndex < field.length - 1) {
            char up = field[firstIndex - 1][secondIndex];
            char down = field[firstIndex + 1][secondIndex];
            char left = field[firstIndex][secondIndex - 1];

            return down != 'O' && left != 'O' && up != 'O';
        }

        return false;
    }

    public void printField(char[][] field) {

        int columnNumber = 0;
        for (int i = 0; i <= 10; i++) {
            if (columnNumber == 0) {
                System.out.print(" ");
            } else {
                System.out.print(columnNumber + " ");
            }
            columnNumber++;
        }
        System.out.println();

        // print first column as Letters
        char rowLetter = 'A';
        for (int i = 0; i < field.length; i++) {
            System.out.print(rowLetter + " ");
            rowLetter++;
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printGame(char[][] player2FieldWithFog, char[][] myFieldWithShips) {

        // print another player field with fog
        int columnNumber = 0;
        for (int i = 0; i <= 10; i++) {
            if (columnNumber == 0) {
                System.out.print(" ");
            } else {
                System.out.print(columnNumber + " ");
            }
            columnNumber++;
        }
        System.out.println();

        // print first column as Letters
        char rowLetter = 'A';
        for (int i = 0; i < player2FieldWithFog.length; i++) {
            System.out.print(rowLetter + " ");
            rowLetter++;
            for (int j = 0; j < player2FieldWithFog[i].length; j++) {
                System.out.print(player2FieldWithFog[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("--------------------");

        // print my field with ships
        columnNumber = 0;
        for (int i = 0; i <= 10; i++) {
            if (columnNumber == 0) {
                System.out.print(" ");
            } else {
                System.out.print(columnNumber + " ");
            }
            columnNumber++;
        }
        System.out.println();

        // print first column as Letters
        rowLetter = 'A';
        for (int i = 0; i < myFieldWithShips.length; i++) {
            System.out.print(rowLetter + " ");
            rowLetter++;
            for (int j = 0; j < myFieldWithShips[i].length; j++) {
                System.out.print(myFieldWithShips[i][j] + " ");
            }
            System.out.println();
        }
    }

}
