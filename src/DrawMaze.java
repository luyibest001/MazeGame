import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Serves a <code>JPanel</code> with the maze from the relevant <code>IMazeSession</code>
 * drawn and prepared.
 * Handles all the drawing logic. An object of this class is largely useable just by
 * instantiating it and adding it to a <code>Component</code>
 */
public class DrawMaze extends JPanel{
	
	private static final long serialVersionUID = 3731311043173065702L;

	private IMazeSession mazeSession;
	private Dimension startLoc;
	private Dimension endLoc;
	private Dimension mazeSize;
	
	/**
	 * Constructor for the <code>DrawMaze</code> class.
	 * Sets up the <code>JPanel</code> to be placed... somewhere.
	 * @param mazeSession A reference to the current maze game session (so it can
	 * be drawn appropriately)
	 */
	public DrawMaze(IMazeSession mazeSession) {
		this.mazeSession = mazeSession;
		this.startLoc = new Dimension(0,0);
		this.endLoc = new Dimension(this.getSize().width, this.getSize().height);
		this.mazeSize = new Dimension(0,0);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		updateDimensions();
		super.paintComponent(g);
		IMaze maze = this.mazeSession.getCurrentMaze();
		this.paintMaze(g, maze);
		if (this.mazeSession.getMazeType(maze) == IMazeSession.ABOVE_GROUND) {
			markSpecial(g, maze);
		} else {
			markGoal(g, maze);
		}
		this.drawPlayer(g, maze);
	}
	
	/**
	 * Returns the current score, as defined by the session
	 * @return The current score, as defined by the session
	 */
	public int getScore() {
		return mazeSession.getScore();
	}
	
	/**
	 * Private method to update the dimensions of the maze, and its
	 * position in its own alloted frame
	 */
	private void updateDimensions() {
		int tileSize = this.getTileSize();
		IMaze currMaze = this.mazeSession.getCurrentMaze();
		this.mazeSize.setSize(currMaze.getWidth() * tileSize, 
				currMaze.getHeight() * tileSize);
		this.startLoc.width = (this.getSize().width - this.mazeSize.width)/2;
		this.startLoc.height = (this.getSize().height - this.mazeSize.height)/2;
		this.endLoc.width = this.getSize().width - this.startLoc.width;
		this.endLoc.height = this.getSize().height - this.startLoc.height;
		
	}
	
	/**
	 * Gets the size of a single "tile", or grid square in the maze
	 * @return The maximum possible length of a grid square that can fit
	 * in the current frame
	 */
	private int getTileSize() {
		IMaze maze = this.mazeSession.getCurrentMaze();
		int xwidth = maze.getWidth();
		int yheight = maze.getHeight();
		int tileSize = (this.getSize().height)/(yheight+1);
		if (tileSize > (this.getSize().width)/(xwidth+1)) {
			tileSize = (int) (this.getSize().width)/(xwidth+1);
		}
		return tileSize;
	}
	
	/**
	 * Helper method called by <code>paintComponent(g)</code> to paint the walls
	 * and floor of the maze.
	 * @param g The <code>Graphics</code> object passed into <code>paintComponent</code>
	 * @param maze The maze to be drawn
	 */
	private void paintMaze(Graphics g, IMaze maze) {
		if (maze == null) return;
		BufferedImage wallHorizontal = null;
		BufferedImage wallVertical = null;
		BufferedImage floor = null;
		//grab the relevant images
		switch (this.mazeSession.getMazeType(maze)) {
			case IMazeSession.ABOVE_GROUND:
				try {
				    wallHorizontal = ImageIO.read(new File("assets/above_wall_horiz.png"));
				} catch (IOException e) {
					System.out.println("FILE NOT FOUND");
				}
				try {
				    wallVertical = ImageIO.read(new File("assets/above_wall_vert.png"));
				} catch (IOException e) {
					System.out.println("FILE NOT FOUND");
				}
				try {
				    floor = ImageIO.read(new File("assets/above_floor.png"));
				} catch (IOException e) {
					System.out.println("FILE NOT FOUND");
				}
				break;
			case IMazeSession.BELOW_GROUND_CAVE:
				try {
				    wallHorizontal = ImageIO.read(new File("assets/passage_wall_horiz.png"));
				} catch (IOException e) {
					System.out.println("FILE NOT FOUND");
				}
				try {
				    wallVertical = ImageIO.read(new File("assets/passage_wall_vert.png"));
				} catch (IOException e) {
					System.out.println("FILE NOT FOUND");
				}
				try {
				    floor = ImageIO.read(new File("assets/passage_floor.png"));
				} catch (IOException e) {
					System.out.println("FILE NOT FOUND");
				}
				break;
			case IMazeSession.BELOW_GROUND_TRAP:
				try {
				    wallHorizontal = ImageIO.read(new File("assets/trap_wall_horiz.png"));
				} catch (IOException e) {
					System.out.println("FILE NOT FOUND");
				}
				try {
				    wallVertical = ImageIO.read(new File("assets/trap_wall_vert.png"));
				} catch (IOException e) {
					System.out.println("FILE NOT FOUND");
				}
				try {
				    floor = ImageIO.read(new File("assets/trap_floor.png"));
				} catch (IOException e) {
					System.out.println("FILE NOT FOUND");
				}
				break;
		}
		
		int xwidth = maze.getWidth();
		int yheight = maze.getHeight();
		int xpos = startLoc.width;
		int ypos = startLoc.height;
		int tileSize = this.getTileSize();
		int drawSize = tileSize * 6 / 5;
		int i, j;
		
		//draw the floor first
		for (i = 0; i < xwidth; i++) {
			for (j = 0; j < yheight; j++) {
				g.drawImage(floor, xpos + tileSize * i, ypos + tileSize * j, 
						drawSize, drawSize, null);
			}
		}
		//draw the walls so they don't look weird
		Point cell = new Point(0,0);
		for (i = 0; i < xwidth; i++) {
			for (j = 0; j < yheight; j++) {
				cell.setLocation(i,j);
				if (maze.isWall(IMaze.MOVE_UP, cell)) {
					g.drawImage(wallHorizontal, xpos + tileSize * i, 
							ypos + tileSize * j, drawSize, drawSize/6, null);
				} 
				if (maze.isWall(IMaze.MOVE_DOWN, cell)) {
					g.drawImage(wallHorizontal, xpos + tileSize * i, 
							ypos + tileSize * (j + 1), drawSize, drawSize/6, null);
				} 
				if (maze.isWall(IMaze.MOVE_LEFT, cell)) {
					g.drawImage(wallVertical, xpos + tileSize * i, 
							ypos + tileSize * j, drawSize/6, drawSize, null);
				} 
				if (maze.isWall(IMaze.MOVE_RIGHT, cell)) {
					g.drawImage(wallVertical, xpos + tileSize * (i + 1), 
							ypos + tileSize * j, drawSize/6, drawSize, null);				
				}
				
			}
		}

	}
	
	/**
	 * Marks the trap and passage tiles for the above-ground maze
	 * @param g The <code>Graphics</code> object passed into <code>paintComponent</code>
	 * @param maze The maze to be drawn
	 */
	private void markSpecial(Graphics g, IMaze maze) {
		BufferedImage passageEntry = null;
//		BufferedImage passageExit = null;
		BufferedImage trap = null;
		BufferedImage goal = null;
		try {
		    passageEntry = ImageIO.read(new File("assets/passage_entry.png"));
		} catch (IOException e) {
			System.out.println("FILE NOT FOUND");
		}
//		try {
//		    passageExit = ImageIO.read(new File("assets/passage_exit.png"));
//		} catch (IOException e) {
//			System.out.println("FILE NOT FOUND");
//		}
		try {
		    trap = ImageIO.read(new File("assets/trap_entry.png"));
		} catch (IOException e) {
			System.out.println("FILE NOT FOUND");
		}
		try {
		    goal = ImageIO.read(new File("assets/final_goal.png"));
		} catch (IOException e) {
			System.out.println("FILE NOT FOUND");
		}
		HashMap<Point, Integer> specialTiles = this.mazeSession.getSpecialTiles(maze);
		int tileSize = this.getTileSize();
		int xStart = startLoc.width;
		int yStart = startLoc.height;
		for (Point p : specialTiles.keySet()) {
			switch (specialTiles.get(p)) {
			case IMazeSession.CHANCE_TRAP:
				g.drawImage(trap, xStart + p.x * tileSize + tileSize/5, 
						yStart + p.y * tileSize + tileSize/5
						, tileSize * 3 / 4, tileSize * 3 / 4, null);
				break;
			case IMazeSession.PASSAGE_ENTRY:
				g.drawImage(passageEntry, xStart + p.x * tileSize + tileSize/5, 
						yStart + p.y * tileSize + tileSize/5
						, tileSize * 3 / 4, tileSize * 3 / 4, null);
				break;
//			case IMazeSession.PASSAGE_EXIT:
//				g.drawImage(passageExit, xStart + p.x * tileSize + tileSize/5, 
//						yStart + p.y * tileSize + tileSize/5
//						, tileSize * 3 / 4, tileSize * 3 / 4, null);
//				break;
			case IMazeSession.GOAL:
				g.drawImage(goal, xStart + p.x * tileSize + tileSize/5, 
						yStart + p.y * tileSize + tileSize/5
						, tileSize * 3 / 4, tileSize * 3 / 4, null);
				break;
			}
		}
	}
	
	/**
	 * Marks the goal for the below-ground mazes
	 * @param g The <code>Graphics</code> object passed into <code>paintComponent</code>
	 * @param maze The maze to be drawn
	 */
	private void markGoal(Graphics g, IMaze maze) {
		BufferedImage goal = null;
		try {
		    goal = ImageIO.read(new File("assets/passage_exit.png"));
		} catch (IOException e) {
			System.out.println("FILE NOT FOUND");
		}
		int tileSize = this.getTileSize();
		Point p = maze.getGoal();
		g.drawImage(goal, this.startLoc.width + p.x * tileSize + tileSize/5, 
				this.startLoc.height + p.y * tileSize + tileSize/5
				, tileSize * 3 / 4, tileSize * 3 / 4, null);
	}
	
	/**
	 * Draws the player's sprite on the map
	 * @param g The <code>Graphics</code> object passed into <code>paintComponent</code>
	 * @param maze The maze to be drawn
	 */
	private void drawPlayer(Graphics g, IMaze maze) {
		IPlayer player = this.mazeSession.getCurrentPlayer();
		if (maze == null || player == null) return;
		int tileSize = this.getTileSize();
		int xpos = startLoc.width + player.getPosition().x * tileSize + tileSize/3;
		int ypos = startLoc.height + player.getPosition().y * tileSize + tileSize/4;
		BufferedImage playerSprite = null;
		try {
		    playerSprite = ImageIO.read(new File("assets/player.png"));
		} catch (IOException e) {
			System.out.println("FILE NOT FOUND");
		}
		g.drawImage(playerSprite, xpos, ypos, tileSize/2, tileSize*2/3, null);		
	}
	
	
}
