import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.border.EmptyBorder;

/**
 * GameScreen is the screen in which the maze will be displayed in.
 * The player can choose to return back to the main menu, restart the maze or exit the game.
 * The course ID of the maze and the current score of the player is displayed at the bottom of the screen.
 */
public class GameScreen extends JPanel{
	
	private static final long serialVersionUID = -9050411254533252919L;
	
	private PropertyChangeSupport gsPcs;
	private IMazeSession mazeSession;
	private DrawMaze mazeDrawer;
	private JLabel scoreLabel;
	
	/**
	 * Constructor for the GameScreen class
	 * @param seed The course ID of the maze to be displayed
	 */
	public GameScreen(String seed){
		this.mazeSession = new MazeSession(seed);
		this.mazeDrawer = new DrawMaze(this.mazeSession);
		gsPcs = new PropertyChangeSupport(this);
		showContent();
	}
	
	/**
	 * Sets up container for displaying its components
	 * Uses the Overlay Layout to lay the components on top of one another,
	 * with the background image as the base.
	 * Components: back home, restart and exit button, the maze panel, courseID and score
	 */
	private void showContent(){
		LayoutManager overlay = new OverlayLayout(this);
		setLayout(overlay);
		
		JPanel panel = new JPanel();
		BorderLayout layout = new BorderLayout();
		layout.setHgap(10);
	    layout.setVgap(10);
		panel.setLayout(layout); 
		panel.setBorder(new EmptyBorder(new Insets(20,50,20,50)));
		panel.setOpaque(false);
		add(panel);
		
	    /*
	     * Top Panel of the Game Screen
	     * Buttons: back to home, suspend, quit and restart
	     */
	    JPanel controlPanel = new JPanel();
	    controlPanel.setLayout(new GridLayout(1,4, 1, 1));
		controlPanel.setOpaque(false);
		panel.add(controlPanel, BorderLayout.NORTH);
		
		Icon back = new ImageIcon("assets/back.jpg");
		JButton btnBack = new JButton(back);
		btnBack.setOpaque(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gsPcs.firePropertyChange("exit", null, null);
			}
		});
		controlPanel.add(btnBack);
		controlPanel.add(new JLabel(""));
		//controlPanel.add(new JLabel(""));
		
		Icon restart = new ImageIcon("assets/restart.jpg");
		JButton btnRestart = new JButton(restart);
		btnRestart.setOpaque(false);
		btnRestart.setContentAreaFilled(false);
		btnRestart.setBorderPainted(false);
		btnRestart.setFocusPainted(false);
		btnRestart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeSession.resetSession();
				repaint();
			}
		});
		controlPanel.add(btnRestart);

		Icon exit = new ImageIcon("assets/exit.jpg");
		JButton btnQuit = new JButton(exit);
		btnQuit.setOpaque(false);
		btnQuit.setContentAreaFilled(false);
		btnQuit.setBorderPainted(false);
		btnQuit.setFocusPainted(false);
		btnQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(showConfirmExit() == JOptionPane.OK_OPTION){
					System.exit(0);
				}
			}
			public int showConfirmExit(){
		        return JOptionPane.showConfirmDialog(null,"Are you sure?","Yes",JOptionPane.OK_CANCEL_OPTION);
		        
			}
		});
		controlPanel.add(btnQuit);
		
		this.mazeDrawer.setOpaque(false);
		this.mazeDrawer.setPreferredSize(new Dimension(150, 150));
		panel.add(this.mazeDrawer, BorderLayout.CENTER);
		
		/*
		 * Bottom Panel of the Game Screen
		 * Contains music slider and game score 
		 */
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		FlowLayout layout1 = new FlowLayout();
	    layout1.setHgap(10);   
	    bottomPanel.setLayout(layout1);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		
		JLabel lblCourseID = new JLabel("Course ID: ");
		lblCourseID.setForeground(Color.WHITE);
		lblCourseID.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		bottomPanel.add(lblCourseID);
		JLabel courseID = new JLabel (mazeSession.getCourseID());
		courseID.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		courseID.setForeground(Color.WHITE);
		bottomPanel.add(courseID);
		
		JLabel lblScore = new JLabel("\t\t\t\t\t\t\tScore:");
		lblScore.setForeground(Color.WHITE);
		lblScore.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		this.scoreLabel = new JLabel(Integer.toString(this.mazeSession.getScore()));
		scoreLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		scoreLabel.setForeground(Color.WHITE);
		bottomPanel.add(lblScore);
		bottomPanel.add(this.scoreLabel);
		
		// background image
		ImagePanel image = new ImagePanel(
			new ImageIcon("assets/forest2.png").getImage());
		add(image);
	}
	
	@Override
	public void paintComponent (Graphics g) {
		this.scoreLabel.setText(Integer.toString(this.mazeSession.getScore()));
	}
	
	public void makeMove(int direction) {
		this.mazeSession.makeMove(direction);
		if (this.mazeSession.isFinished()) 
			gsPcs.firePropertyChange("finished", 
					this.mazeSession.getCourseID(), 
					Integer.toString(this.mazeSession.getScore()));
		else if (this.mazeSession.getScore() <= 0) 
			gsPcs.firePropertyChange("failed", 
					null, this.mazeSession.getCourseID());
	
		
	}
	
	public void addPropertyChangeListener (PropertyChangeListener listener) {
		gsPcs.addPropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		gsPcs.addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener (PropertyChangeListener listener) {
		gsPcs.removePropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		gsPcs.removePropertyChangeListener(propertyName, listener);
	}
	
}