import java.awt.Point;

/**
 * A cave is a feature of SuperMaze. It is a marker which indicates a subMaze in the superMaze.
 * Caves are entered through one entrance and have an exit on the final 3rd of the maze solution.
 * <br> <br>
 * Caves contain a entrance and finish location, a boolean that says whether the cave has been used as caves
 * should only be used one and then disappear and an id which tells you which submaze it belongs to in the session.
 * @author Cameron
 *
 */
public class Cave {
	Point location;
	Boolean used;
	Point finish;
	int id;
	
	/**
	 * To instantiate a maze you must enter the caves entrance and finish points as well as the id.
	 * @param start
	 * @param finish
	 * @param id
	 */
	public Cave(Point start, Point finish, int id) {
		this.location = start;
		this.finish = finish;
		this.used = false;
		this.id = id;
	}
	
	public Point getStart() {
		return this.location;
	}
	
	public Point getFinish() {
		return this.finish;
	}
	
	public boolean isUsed() {
		return this.used;
	}
	
	/**
	 * pre condition only used afer isUsed has been checked
	 */
	public void use(){
		this.used = true;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void reset() {
		this.used = false;
	}
}
