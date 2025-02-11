
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
            mineLocations[x][y] = true;
        }

        // 3. Set numbers for adjacent mines
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!mineLocations[i][j]) {
                    int totalMines = minesAroundCell(i, j);
                    if (totalMines == 0) {
                        grid[i][j] = " ";
                    } else {
                        grid[i][j] = totalMines + ""; // Converting int to String
                    }
                }
            }
        }
    }

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

   
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!mineLocations[i][j] && !trackRevealedCells[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

}