import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author jacob, Athena, Aiden, Daniel, PICKLE RICK
 *
 */
public class TheMaze {
	public static int[][] maze = {
		/**
		 * maze = [row][col]
		 * 
		 * 0 = not-visted node
		 * 1 = wall
		 * 2 = visited node
		 * 3 = targeted node(you can change this anywhere you want)
		 * 
		 * 
		 */			//  5
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 3, 1},
		{1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1},
		{1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
		{1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
		{1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1},
		{1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1},
		{1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1},
		{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1}, 	
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}

			};
	public static void main(String[] args) {
		//initiates the search for the 3(targeted node) using 1,1 as the starting point of the maze,
		//you can change the starting point to anywhere inside the "walls"
		Search p = Search.searchMaze(1,1);

		//This prints out the maze
        for (int i = 0; i < 10; i++) {
           for (int j = 0; j < 13; j++) {
               System.out.print(maze[i][j]);
           }
           System.out.println();
       }
        
        // This prints out what is happening with the maze solver, this keeps going until targeted node is reached
       while(p.getInfo() != null) {
           System.out.println(p);
           p = p.getInfo();

	}
	
	
	
}
	}

