import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class DFSBasic implements MazeAlgorithm{

	ArrayList<MazeNode> visited = new ArrayList<MazeNode>();
	Random randomGenerator;

	/**
	 * turns a grid into a maze. That is, this function carves the paths of the maze removing relevant walls
	 */
	public Point carvePath(Maze toBePathed, int seed) {
		//this is where you implement the maze algo
//		System.out.println("starting to carve the maze");
		MazeNode start = toBePathed.getNode(0);
		randomGenerator = new Random(seed);
		dfs(start);
		Point startPoint = new Point(0,0);
		return startPoint;
	}
	
	/**
	 * random dfs algorithm with a defined start so you could 
	 * @param start
	 */
	public void dfs(MazeNode start) {
		visited.add(start);
		ArrayList<MazeConnection> neighbours = start.getNeighbouringConnections();
		while(neighbours.isEmpty() == false) {
			int position = randomGenerator.nextInt(neighbours.size());
			//System.out.println("neighbours is of size " + neighbours.size() + " and I am choosing pos " + position);
			MazeConnection next = neighbours.get(position);
			neighbours.remove(position);
			if (visited.contains(next.getNode()) == false) {
				next.removeWall();
				dfs(next.getNode());
			}
		}
	}
}
