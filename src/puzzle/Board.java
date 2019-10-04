package puzzle;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private static final String NL = "\n";
    private static final String SPACE = " ";

    private final int[][] tiles;
    private final int n;
    private int zeroRow;
    private int zeroCol;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException("Tiles are null in Board constructor");
        this.tiles = copyOf(tiles);
        this.n = tiles.length;

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (tiles[row][col] == 0) {
                    zeroRow = row;
                    zeroCol = col;
                    return;
                }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append(NL);
        for (int[] temp: tiles) {
            for (int tempint: temp) {
                sb.append(String.format("%2d ", tempint));
            }
            sb.append(NL);
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0)
                    continue;
                if (manhattan(row, col) != 0) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0)
                    continue;
                manhattan += manhattan(row, col);
            }
        }
        return manhattan;
    }
    private int manhattan(int row, int col) {
        int destValue = tiles[row][col] - 1;
        int destRow = destValue / n;
        int destCol = destValue % n;
        return Math.abs(destRow - row) + Math.abs(destCol - col);
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;
        Board that = (Board) y;
        if (dimension() != that.dimension())
            return false;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();

        if (zeroRow > 0) {
            int[][] north = copyOf(tiles);
            swap(north, zeroRow, zeroCol, zeroRow - 1, zeroCol);
            neighbors.add(new Board(north));
        }
        if (zeroRow < n - 1) {
            int[][] south = copyOf(tiles);
            swap(south, zeroRow, zeroCol, zeroRow + 1, zeroCol);
            neighbors.add(new Board(south));
        }
        if (zeroCol > 0) {
            int[][] west = copyOf(tiles);
            swap(west, zeroRow, zeroCol, zeroRow, zeroCol - 1);
            neighbors.add(new Board(west));
        }
        if (zeroCol < n - 1) {
            int[][] east = copyOf(tiles);
            swap(east, zeroRow, zeroCol, zeroRow, zeroCol + 1);
            neighbors.add(new Board(east));
        }
        return neighbors;
    }

    private int[][] copyOf(int[][] target) {
        int[][] clone = new int[target.length][];
        for (int row = 0; row < target.length; row++)
            clone[row] = target[row].clone();
        return clone;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin = copyOf(tiles);
        if (zeroRow != 0)
            swap(twin, 0, 0, 0, 1);
        else
            swap(twin, 1, 0, 1, 1);
        return new Board(twin);
    }

    private void swap(int[][] array, int fRow, int fCol, int sRow, int sCol) {
        int swapInt = array[fRow][fCol];
        array[fRow][fCol] = array[sRow][sCol];
        array[sRow][sCol] = swapInt;
    }

}
