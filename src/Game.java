
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int size = 10; // grid size 10*10
    private int mines = 50; // number of bombs hidden
    private String[][] grid;
    private boolean[][] mineLocations;
    private boolean[][] trackRevealedCells;
    //constructor
    public Game() {
        grid = new String[size][size];
        mineLocations = new boolean[size][size];
        trackRevealedCells = new boolean[size][size];
        setUpGrid();
    }

    // 1. empty grid initially..
    private void setUpGrid() {
        //initialize the grid to
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = "[ ]";
                mineLocations[i][j] = false;
                trackRevealedCells[i][j] = false;
            }
        }

        // 2. randomly placing bombs..
        Random rand = new Random();
        for (int i = 0; i < mines; i++) {
            int x, y;
            //do {
  // code block to be executed
// }
// while (condition);
            
//keep generating untill we find an occupied cell 
            do {
                x = rand.nextInt(size);
                y = rand.nextInt(size);
            } while (mineLocations[x][y]); 
            mineLocations[x][y] = true;//bombs..
        }

        // 3. Set numbers for adjacent mines
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!mineLocations[i][j]) //we skip the cells with mines in it.
                {
                    int totalMines = minesAroundCell(i, j);
                    if (totalMines == 0) {
                        // grid[i][j] = "null";
                        grid[i][j]= "";
                    } else {
                        grid[i][j] = totalMines + ""; // Converting int to String
                    }
                }
            }
        }
    }

    // Method to calculate number of mines around cell(x,y)
    private int minesAroundCell(int x, int y) {
        int totalMines = 0;
        for (int i = -1; i <= 1; i++) { //for rows coords
            for (int j = -1; j <= 1; j++)//for cols coords
             {
                // Calculate the coordinates of the adjacent cell for the given coords..
                int adjacentRows = x + i;
                int adjacentCols = y + j;

                // Check if the cell is within range and if it contains a mine
                if (adjacentRows >= 0 && adjacentRows < size && adjacentCols >= 0 && adjacentCols < size && mineLocations[adjacentRows][adjacentCols]) {
                    totalMines++;
                }
            }
        }
        return totalMines;
    }

    private void revealCell(int x, int y) {
        if (trackRevealedCells[x][y]) return; 
        trackRevealedCells[x][y] = true;

        if (grid[x][y].equals(" ")) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newX = x + i;
                    int newY = y + j;
                    if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
                        revealCell(newX, newY);
                    }
                }
            }
        }
    }

    private void showGrid() {
        System.out.print("   "); 
        for (int i = 0; i < size; i++) {
            System.out.print(i + "  "); // Display column numbers( 0 -9 to choose coords)
        }
        System.out.println();
    
        for (int i = 0; i < size; i++) {
            System.out.print(i + "  "); // Print row number to choose rows from 0-9
            for (int j = 0; j < size; j++) {
                if (trackRevealedCells[i][j]) {
                    if (mineLocations[i][j]) {
                        System.out.print("[*] "); // Show bomb symbol for mines
                    } else {
                        System.out.print(grid[i][j] + "  "); // Show numbers or empty spaces
                    }
                } else {
                    System.out.print("[ ] "); // Hidden cells
                }
            }
            System.out.println();
        }
    }
    
    private boolean gameRes() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!mineLocations[i][j] && !trackRevealedCells[i][j]) {
                    return false; // unfinished game
                }
            }
        }
        return true;
    }

    public void letsPlayTheGame() {
        Scanner scanner = new Scanner(System.in); // Take coordinates from user

        while (true) {
            showGrid();
            System.out.print("Please enter coordinates eg: 2 4 to choose a cell in the grid.....  ");

            String input = scanner.nextLine().trim(); // Read full line
            String[] coords = input.split("\\s+"); // Split by spaces in to array 

            // Validate input length
            if (coords.length != 2) {
                System.out.println("Invalid input! Enter exactly two numbers (row column).");
                continue;
            }

            // Check if both coords are numbers
            //numbers of 0-9 only need to be accepted here
            if (!coords[0].matches("\\d+") || !coords[1].matches("\\d+")) {
                System.out.println("Invalid coords! Please enter valid numeric values.");
                continue;
            }

            // Convert to integers **only after** ensuring they are numbers
            int row = Integer.parseInt(coords[0]);
            int col = Integer.parseInt(coords[1]);

            // Check range before accessing the array
            if (row < 0 || col < 0 || row >= size || col >= size) {
                System.out.println("Invalid coordinates! Please enter values between 0 and " + (size - 1));
                continue;
            }

            // Check if the cell is already trackRevealedCells
            if (trackRevealedCells[row][col]) {
                System.out.println("You have already revealed this cell. Choose another one.");
                continue;
            }

            // Check if the player hit a mine
            if (mineLocations[row][col]) {
                System.out.println("Sorry..You hit a mine ******, Better luck next time! Play again :)");

                // Reveal all mines 
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (mineLocations[i][j]) {
                            trackRevealedCells[i][j] = true;
                        }
                    }
                }
                showGrid(); // Show final grid
                break;
            }

            // Reveal the selected cell
            revealCell(row, col);

            // Check if the game is won
            if (gameRes()) {
                System.out.println("Yay.....You win....!!! ");
                break;
            }
        }
        scanner.close();
    }


    // private void showGrid() {
    //     System.out.print("   "); // Extra space for row numbers
    //     for (int i = 0; i < size; i++) {
    //         System.out.print("%2d  ", i); //  spacing for column numbers
    //     }
    //     System.out.println();

    //     for (int i = 0; i < size; i++) {
    //         System.out.print("%2d  ", i); // Print row number
    //         for (int j = 0; j < size; j++) {
    //             if (trackRevealedCells[i][j]) {
    //                 if (mineLocations[i][j]) {
    //                     System.out.print("[*] "); // Show bomb symbol for mines
    //                 } else {
    //                     System.out.print("%2s  ", grid[i][j]); // Show numbers or empty spaces
    //                 }
    //             } else {
    //                 System.out.print("[ ] ");
    //             }
    //         }
    //         System.out.println();
    //     }
    // }

 

    // private boolean gameRes() {
    //     for (int i = 0; i < size; i++) {
    //         for (int j = 0; j < size; j++) {
    //             if (!mineLocations[i][j]) {
    //                 return false;
    //             }
    //         }
    //     }
    //     return true;
    // }
  

}