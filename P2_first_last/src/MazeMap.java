import java.util.LinkedList;
import java.util.Queue;
import java.util.*;

public class MazeMap {
	private Tile[][][] map;  // 3D map to store multiple rooms
    private int numRows, numCols, numRooms;
    private int[] wolverineRow; // Array to store Wolverine's row in each room
    private int[] wolverineCol; // Array to store Wolverine's col in each room

    // Constructor to initialize the map and room info
    public MazeMap(int rows, int cols, int rooms) {
        this.numRows = rows;
        this.numCols = cols;
        this.numRooms = rooms;
        map = new Tile[rooms][rows][cols];
        wolverineRow = new int[rooms];
        wolverineCol = new int[rooms];
    }

    // Set the tile for a given row, col, and room
    public void setTile(int row, int col, char type, int roomIndex) {
        map[roomIndex][row][col] = new Tile(row, col, type);
        if (type == 'W') {
            wolverineRow[roomIndex] = row;
            wolverineCol[roomIndex] = col;
        }
    }

    // Get the tile at the specified row, col, and room
    public Tile getTile(int row, int col, int roomIndex) {
        return map[roomIndex][row][col];
    }

    public int getWolverineRow(int roomIndex) {
        return wolverineRow[roomIndex];
    }

    public int getWolverineCol(int roomIndex) {
        return wolverineCol[roomIndex];
    }

    // Method to find the shortest path to the coin ('$')
    public void findShortestPath(int wolverineRow, int wolverineCol, int currentRoom) {
        Queue<Tile> queue = new LinkedList<>();
        queue.add(map[currentRoom][wolverineRow][wolverineCol]);  // Start BFS from Wolverine's position

        boolean[][] visited = new boolean[numRows][numCols];  // Track visited tiles
        Tile[][] parent = new Tile[numRows][numCols];  // Track paths
        visited[wolverineRow][wolverineCol] = true;

        // Movement directions (North, South, East, West)
        int[] rowMoves = {-1, 1, 0, 0};
        int[] colMoves = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Tile current = queue.poll();
            int rowCur = current.getRow();
            int colCur = current.getCol();

            if (current.getType() == '$') {
                System.out.println("Coin found at (" + rowCur + ", " + colCur + ")");
                printPath(parent, map[currentRoom][wolverineRow][wolverineCol], current);
                return;
            }

            // Check all four possible moves
            for (int i = 0; i < 4; i++) {
                int newRow = rowCur + rowMoves[i];
                int newCol = colCur + colMoves[i];

                if (isValid(newRow, newCol) && !visited[newRow][newCol]) {
                    Tile nextTile = map[currentRoom][newRow][newCol];
                    if (nextTile.getType() == '|') {
                        nextTile = findOtherDoor(nextTile, currentRoom);
                    }
                    queue.add(nextTile);
                    visited[nextTile.getRow()][nextTile.getCol()] = true;
                    parent[nextTile.getRow()][nextTile.getCol()] = current;
                }
            }
        }
        System.out.println("Coin not reachable!");
    }

    // Method to find the corresponding tile in the next room
    private Tile findOtherDoor(Tile door, int currentRoom) {
        // Find the room that the door leads to (next room)
        int nextRoom = (currentRoom + 1) % numRooms;  // Assuming rooms are 0-indexed and cyclic

        // Get Wolverine's position in the next room
        int wolverineRowInNextRoom = wolverineRow[nextRoom];
        int wolverineColInNextRoom = wolverineCol[nextRoom];

        // Return the tile corresponding to Wolverine's position in the next room
        return map[nextRoom][wolverineRowInNextRoom][wolverineColInNextRoom];
    }

    // Helper method to print the path from start to goal
    private void printPath(Tile[][] parent, Tile start, Tile end) {
        char[][] mapCopy = new char[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                mapCopy[r][c] = map[0][r][c].getType();  // Start with the map from the first room
            }
        }

        Tile step = end;
        while (step != null && step != start) {
            if (step.getType() != '$') {
                mapCopy[step.getRow()][step.getCol()] = '+';
            }
            step = parent[step.getRow()][step.getCol()];
        }

        mapCopy[start.getRow()][start.getCol()] = 'W';
        System.out.println("Map with Shortest Path:");
        for (char[] row : mapCopy) {
            System.out.println(new String(row));
        }
    }

    // Helper method to check if the coordinates are valid
    private boolean isValid(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }
}