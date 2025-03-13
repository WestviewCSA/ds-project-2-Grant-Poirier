import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class p2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("p2");
		
		readMap("src/TEST01");
//		readMap("src/TEST02");
//		readMap("src/TEST03");
//		readMap("src/TEST04");
		//readMap("src/TEST05");
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
			
			//process the map!
			while (scanner.hasNextLine()) {
				
				//grab a line (one row of the map)
				String row = scanner.nextLine();
				System.out.println(row);
				
				if (row.length()>0) {
					
					for(colIndex = 0; colIndex < numCols && colIndex < row.length(); colIndex++) {
						
						char el = row.charAt(colIndex);
						Tile obj = new Tile(rowIndex, colIndex, el);
						tiles[rowIndex][colIndex][roomIndex] = obj;
						
						System.out.println("Tile at (" + tiles[rowIndex][colIndex][roomIndex].getRow() 
								+ ", " + tiles[rowIndex][colIndex][roomIndex].getCol() + ", " 
								+ tiles[rowIndex][colIndex][roomIndex].getType() + ")");
						
					}
					
					rowIndex++;
				}
			}
			
		}catch (FileNotFoundException e){
			System.out.println(e);
		}
		
	}

	
	
}