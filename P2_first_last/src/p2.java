import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class p2 {
	public static void main(String[] args) {
		
		String filename = "src/TEST02";
		readMap(filename);
	}
	
	public static void readMap(String filename) {
		
		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			
			int numRows  = scanner.nextInt();
			int numCols  = scanner.nextInt();
			int numRooms = scanner.nextInt();
			
			int rowIndex = 0;
			int roomIndex = 0;
			int colIndex = 0;
			
			Tile[][][] tiles = new Tile[numRows][numCols][numRooms];
			
			Map map = new Map(numRows, numCols);
			
			int wolverineRow = -1;
			int wolverineCol = -1;
			
			//process the map!
			while (scanner.hasNextLine()) {
				
				//grab a line (one row of the map)
				String row = scanner.nextLine();
				//System.out.println(row);
				
				if (row.length()>0) {
					
					for(colIndex = 0; colIndex < numCols && colIndex < row.length(); colIndex++) {
						
						char el = row.charAt(colIndex);
						Tile obj = new Tile(rowIndex, colIndex, el);
						tiles[rowIndex][colIndex][roomIndex] = obj;
						map.setTile(rowIndex, colIndex, el);
						
						
						if(el == 'W') {
							wolverineRow = rowIndex;
							wolverineCol = colIndex;
						}
//						if(wolverineRow != -1) {
//							System.out.println("W at (" + wolverineRow + ", " + wolverineCol + ")");
//					
//						}
					}
					rowIndex++;
				}
			}
		
	        // Check if Wolverine's position was found
	        if (wolverineRow != -1) {
	            System.out.println("Wolverine found at (" + wolverineRow + ", " + wolverineCol + ")");
	            map.findShortestPath(wolverineRow, wolverineCol); // Call the pathfinding function
	        } else {
	            System.out.println("Wolverine not found in the map!");
	        }
			
			
		}catch (FileNotFoundException e){
			System.out.println(e);
		}
		
	}
	
	
}
