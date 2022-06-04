/**
 *
 */
package unsw.automata;

import java.util.Arrays;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Conway's Game of Life on a 10x10 grid.
 *
 * @author Robert Clifton-Everest
 *
 */
public class GameOfLife {

    private BooleanProperty gameArray[][];
    private boolean tempArray[][];

    public GameOfLife() {
        int rows = 10;
        int columns = 10;

        gameArray = new BooleanProperty[rows][columns];
        tempArray = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                BooleanProperty p = new SimpleBooleanProperty();
                p.set(false);
                gameArray[i][j] = p;
                tempArray[i][j] = false;
            }   
        }
    }

    public void ensureAlive(int x, int y) {
        //Set the cell at (x,y) as alive
        gameArray[x][y].set(true);
    }

    public void ensureDead(int x, int y) {
        //Set the cell at (x,y) as dead
        gameArray[x][y].set(false);
    }

    public boolean isAlive(int x, int y) {
        // Return true if the cell is alive
        return gameArray[x][y].get();
    }

    public void tick() {
        // Transition the game to the next generation.

        int rows = gameArray.length;
        int columns = gameArray[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int numLiveNeighbours = 0;
                int wrappedRow = i - 1;
                int wrappedCol = j - 1;
                if (wrappedRow < 0) wrappedRow = rows - 1;
                if (wrappedCol < 0) wrappedCol = columns - 1;

                if (gameArray[wrappedRow][wrappedCol].get()) numLiveNeighbours++;
                if (gameArray[i][wrappedCol].get()) numLiveNeighbours++;
                if (gameArray[(i + 1) % rows][wrappedCol].get()) numLiveNeighbours++;
                if (gameArray[wrappedRow][j].get()) numLiveNeighbours++;
                if (gameArray[(i + 1) % rows][j].get()) numLiveNeighbours++;
                if (gameArray[wrappedRow][(j + 1) % columns].get()) numLiveNeighbours++;
                if (gameArray[i][(j + 1) % columns].get()) numLiveNeighbours++;
                if (gameArray[(i + 1) % rows][(j + 1) % columns].get()) numLiveNeighbours++;

                if (gameArray[i][j].get()) {
                    if (numLiveNeighbours < 2) tempArray[i][j] = false;
                    else if (numLiveNeighbours > 3) tempArray[i][j] = false;
                    else tempArray[i][j] = true;
                } else if (numLiveNeighbours == 3) tempArray[i][j] = true;
                else tempArray[i][j] = false;
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gameArray[i][j].set(tempArray[i][j]);
            }
        }
    }

    public BooleanProperty cellProperty(int x, int y) {
        return gameArray[x][y];
    }
}
