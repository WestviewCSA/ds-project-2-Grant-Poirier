import java.util.LinkedList;
import java.util.Queue;
public class Map {
	
	private Tile[][] map; //is 2D enough
	private int numRows, numCols;
	
	public Map(int rows, int cols) {
		
		this.numRows = rows;
	    this.numCols = cols;
	    map = new Tile[rows][cols];
	    
	}
	  
	
	public void setTile(int row, int col, char type) {
		
		map[row][col] = new Tile(row, col, type);
	
	}
	
	  
	public Tile getTile(int row, int col) {
		
		return map[row][col];
		
    }
	  
	public boolean isValid(int row, int col) {
		
		if(row >= 0 && row < numRows && col >= 0 && col < numCols && map[row][col].getType() != '@') {
			return true;
		}
		return false;
	   
	}
	  
	public void findShortestPath(int wolverineRow, int wolverineCol) {
		Queue<Tile> queue = new LinkedList<>();
		queue.add(map[wolverineRow][wolverineCol]); // Start BFS from Wolverine's position
	      
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
	            printPath(parent, map[wolverineRow][wolverineCol], current);
	            return;
	            
	        }
	        // Check all four possible moves
	        for (int i = 0; i < 4; i++) {
	        	
	        	int newRow = rowCur + rowMoves[i];
	            int newCol = colCur + colMoves[i];
	            
	            // Make sure the move is valid (within bounds, not visited, and not a wall)
	            if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols) {
	            	if (!visited[newRow][newCol] && map[newRow][newCol].getType() != '@') {
	            		
	            		queue.add(map[newRow][newCol]); // Add valid tile to the queue
	                    visited[newRow][newCol] = true; // Mark as visited
	                    parent[newRow][newCol] = current; // Store where we came from
	                    
	                }
	            }
	        }
	   }
	       System.out.println("Coin not reachable!");
	}
	  
	   private void printPath(Tile[][] parent, Tile start, Tile end) {
		   
	       char[][] mapCopy = new char[numRows][numCols]; // Copy of the map for modification
	       
	       // Fill mapCopy with original map values
	       for (int r = 0; r < numRows; r++) {
	           for (int c = 0; c < numCols; c++) {
	               mapCopy[r][c] = map[r][c].getType();
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

