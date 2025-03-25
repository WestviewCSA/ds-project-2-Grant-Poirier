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
        map = new Tile[rooms][rows][cols]; // 3D array: rooms -> rows -> cols
        wolverineRow = new int[rooms];
        wolverineCol = new int[rooms];
    }

    // Set the tile for a given row, col, and room
    public void setTile(int row, int col, char type, int roomIndex) {
        map[roomIndex][row][col] = new Tile(row, col, type);
    }

    // Get the tile at the specified row, col, and room
    public Tile getTile(int row, int col, int roomIndex) {
        return map[roomIndex][row][col];
    }

    // Helper method to check if the coordinates are valid
    public boolean isValid(int row, int col, int roomIndex) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols && map[roomIndex][row][col].getType() != '@';
    }

    // Method to find the shortest path to the coin ('$')
    public void findShortestPath(int wolverineRow, int wolverineCol, int currentRoom) {
        Queue<Tile> queue = new LinkedList<>();
        queue.add(map[currentRoom][wolverineRow][wolverineCol]); // Start BFS from Wolverine's position

        boolean[][] visited = new boolean[numRows][numCols]; // Track visited tiles
        Tile[][] parent = new Tile[numRows][numCols]; // Track paths
        visited[wolverineRow][wolverineCol] = true;

        // Movement directions (North, South, East, West)
        int[] rowMoves = {-1, 1, 0, 0};
        int[] colMoves = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Tile current = queue.poll();
            int rowCur = current.getRow();
            int colCur = current.getCol();

            // If we found the coin, print the path and exit
            if (current.getType() == '$') {
                System.out.println("Coin found at (" + rowCur + ", " + colCur + ")");
                printPath(parent, map[currentRoom][wolverineRow][wolverineCol], current);
                return;
            }

            // Check all four possible moves
            for (int i = 0; i < 4; i++) {
                int newRow = rowCur + rowMoves[i];
                int newCol = colCur + colMoves[i];

                // Make sure the move is valid (within bounds, not visited, and not a wall)
                if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols) {
                    if (!visited[newRow][newCol] && map[currentRoom][newRow][newCol].getType() != '@') {
                        queue.add(map[currentRoom][newRow][newCol]); // Add valid tile to the queue
                        visited[newRow][newCol] = true; // Mark as visited
                        parent[newRow][newCol] = current; // Store where we came from
                    }
                }
            }
        }
        System.out.println("Coin not reachable!");
    }

    // Helper method to print the path from start to goal
    private void printPath(Tile[][] parent, Tile start, Tile end) {
        char[][] mapCopy = new char[numRows][numCols]; // Copy of the map for modification

        // Fill mapCopy with original map values
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                mapCopy[r][c] = map[0][r][c].getType(); // Start with the map from the first room
            }
        }

        // Track the path from end to start and mark it with '+'
        Tile step = end;
        while (step != null && step != start) {
            // Don't overwrite the coin '$'
            if (step.getType() != '$') {
                mapCopy[step.getRow()][step.getCol()] = '+'; // Mark path
            }

            step = parent[step.getRow()][step.getCol()]; // Move to previous tile
        }

        mapCopy[start.getRow()][start.getCol()] = 'W'; // Mark Wolverine's starting position with 'W'

        // Print the updated map with the path marked
        System.out.println("Map with Shortest Path:");
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                System.out.print(mapCopy[r][c]); // Print each tile
            }
            System.out.println(); // Newline for next row
        }
    }
}