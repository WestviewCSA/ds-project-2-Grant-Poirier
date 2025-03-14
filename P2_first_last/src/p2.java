import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class p2 {
	public static void main(String[] args) {
		
		String filename = "src/TEST03";
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
						
						
						if(el == 'W') {
							wolverineRow = rowIndex;
							wolverineCol = colIndex;
						}
						
						System.out.println("W at (" + wolverineRow + ", " + wolverineCol + ")");
					}
					
					rowIndex++;
				}
			}
			
			
		}catch (FileNotFoundException e){
			System.out.println(e);
		}
		
	}
	
	
}
