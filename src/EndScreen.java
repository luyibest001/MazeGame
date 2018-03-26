import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.border.EmptyBorder;

/**
 * EndScreen is displayed when the player finishes the maze.
 * It shows the score that the player achieved(if he wins) and the ID of the maze.
 * The player is given the option to replay the maze or to go back to the main menu.
 */
public class EndScreen extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162752120994041958L;
	private int score;
	private String courseID;
	private boolean result;
	private PropertyChangeSupport wsPcs;

	/**
	 * Constructor for the EndScreen class
	 * @param result TRUE if the player wins, FALSE if the player loses
	 * @param score The score achieved by the player
	 * @param courseID The course ID of the maze played
	 */
	public EndScreen(boolean result, int score, String courseID){
		this.result = result;
		this.score = score;
		this.courseID = courseID;
		wsPcs = new PropertyChangeSupport(this);
		showContent();
	}


	/**
	 * Sets up container for displaying its components
	 * Uses the Overlay Layout to lay the components on top of one another,
	 * with the background image as the base.
	 * Components: message(depending whether the player wins or loses), replay and
	 * back to main menu button.
	 */
	public void showContent(){
		LayoutManager overlay = new OverlayLayout(this);
		setLayout(overlay);
		
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.setBorder(new EmptyBorder(new Insets(70,0,70,0)));
		panel.setOpaque(false);
		add(panel);
		
		JLabel message;
		if(result){
			message = new JLabel("Congratulations! You won! Your score is "
					+ score + ". The course ID is " + courseID + ".");
			//message.setForeground(Color.GRAY);
		} else {
			message = new JLabel("You lost! The course ID is " + courseID + ".");
		}
		message.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(message);
		panel.add(Box.createRigidArea(new Dimension(0,200)));
		
		JButton replay = new JButton("");
		replay.setIcon(new ImageIcon("assets/replay.jpg"));
		replay.setPreferredSize(new Dimension(148,50));
		replay.setMaximumSize(new Dimension(148,50));
		replay.setAlignmentX(Component.CENTER_ALIGNMENT);
		replay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				wsPcs.firePropertyChange("replay", null, courseID);
			}
		});
		panel.add(replay);
		panel.add(Box.createRigidArea(new Dimension(0,20)));
		
		JButton menu = new JButton("");
		menu.setIcon(new ImageIcon("assets/menu.jpg"));
		menu.setPreferredSize(new Dimension(149,50));
		menu.setMaximumSize(new Dimension(149,50));
		menu.setAlignmentX(Component.CENTER_ALIGNMENT);
		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				wsPcs.firePropertyChange("exit", null, null);
			}
		});
		panel.add(menu);
		
		// background image
		ImagePanel image;
		if(result) {
			image = new ImagePanel(
		            new ImageIcon("assets/forest3.png").getImage());
		} else {
			image = new ImagePanel(
		            new ImageIcon("assets/forest3.png").getImage());
		}
		add(image);
		
	}
	
	public void addPropertyChangeListener (PropertyChangeListener listener) {
		wsPcs.addPropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		wsPcs.addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener (PropertyChangeListener listener) {
		wsPcs.removePropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		wsPcs.removePropertyChangeListener(propertyName, listener);
	}
	
	
}
