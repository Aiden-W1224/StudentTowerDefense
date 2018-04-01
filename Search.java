import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Search {
	int x;
	int y; 
	Search read;
	public Search(int x, int y, Search read) {
		this.x = x;
		this.y = y;
		this.read = read;
	}
	public Search getInfo() {
		return this.read;
	}
	public String toString() {
	    return "x = " + x + " y = " + y;
	}
	public static Queue<Search> path = new LinkedList<Search>();
	/**
	 * 
	 * @param x x cordinate of array
	 *
	 * @param y y cordinate of array
	 * @return True if the maze is 0 or 3, 2, returns false if it is anything else
	 */
	public static boolean isFree(int x, int y) {
	        if((x >= 0 && x < TheMaze.maze.length) && (y >= 0 && y < TheMaze.maze[x].length) && (TheMaze.maze[x][y] == 0 || TheMaze.maze[x][y] == 3 || TheMaze.maze[x][y] == 2)) {
	            return true;
	        }
	        return false;
	}
	
	public static Search searchMaze(int x, int y) {
		path.add(new Search(x,y,null));
		while (!path.isEmpty()) {
			Search p = path.remove();
			if (TheMaze.maze[p.x][p.y] == 3) {
				System.out.println("Exit");
				return p;
			}
			/*
			 * this basically updates everything when it moves through the maze
			 * it changes the 0's to 2's everytime it moves, 
			 *
			 */
			if(isFree(p.x+1,p.y)) {
                TheMaze.maze[p.x][p.y] = 2;
                Search nextP = new Search(p.x+1,p.y, p);
                path.add(nextP);
            }

			if(isFree(p.x-1,p.y)) {
                TheMaze.maze[p.x][p.y] = 2;
                Search nextP = new Search(p.x-1,p.y, p);
                path.add(nextP);
            }
			if(isFree(p.x,p.y+1)) {
                TheMaze.maze[p.x][p.y] = 2;
                Search nextP = new Search(p.x,p.y+1, p);
                path.add(nextP);
            }
			if(isFree(p.x,p.y-1)) {
                TheMaze.maze[p.x][p.y] = 2;
                Search nextP = new Search(p.x,p.y-1, p);
                path.add(nextP);
            }
		}
		return null;
	}
}
	