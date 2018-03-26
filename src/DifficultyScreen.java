import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.border.EmptyBorder;

/**
 * DifficultyScreen(Main Menu) allows the player to choose the difficulty of the maze.
 * It also allows the player to input a valid seed of a maze.
 */
public class DifficultyScreen extends JPanel{

	private static final long serialVersionUID = -8017826586923156510L;
	private String seed_id;
	private PropertyChangeSupport dsPcs;
	
	/**
	 * Constructor for the DifficultyScreen class
	 */
	public DifficultyScreen(){
		this.dsPcs = new PropertyChangeSupport(this);
		showContent();
	}
	
	/**
	 * Sets up container for displaying its components
	 * Uses the Overlay Layout to lay the components on top of one another,
	 * with the background image as the base.
	 * Components: easy, medium, difficult and seed button, seed text field
	 */
	private void showContent(){
		
		LayoutManager overlay = new OverlayLayout(this);
		setLayout(overlay);
		
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.setBorder(new EmptyBorder(new Insets(70,0,0,0)));
		panel.setOpaque(false);
		add(panel);
		
		addAButton("Easy", panel);
		addAButton("Medium", panel);
		addAButton("Hard", panel);

		JPanel seedPanel = new JPanel();
		seedPanel.setLayout(new FlowLayout());
		seedPanel.setOpaque(false);
		panel.add(seedPanel);

		JLabel enter = new JLabel();
		enter.setIcon(new ImageIcon("assets/enter.jpg"));
		seedPanel.add(enter);
		
		JTextField seed = new JTextField();
		seed.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
		        JTextField textField = (JTextField) e.getSource();
		        seed_id = textField.getText();
		    }
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyPressed(KeyEvent e) {
		    }
		});
		seed.setColumns(10);
		seedPanel.add(seed);
		
		JButton btnSeed = new JButton();
		btnSeed.setPreferredSize(new Dimension(150, 30));
        btnSeed.setMaximumSize(new Dimension(150,30));
		btnSeed.setIcon(new ImageIcon("assets/generate.jpg"));
		
		//Checks that the seed entered matches the pattern.
		//An error message will pop up if it is invalid.
		btnSeed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
		        if(MazeSession.isValidSeed(seed_id)){
		        	dsPcs.firePropertyChange("difficulty", null, seed_id);
		        } else {
		        	JOptionPane.showMessageDialog(null, "To enter a valid seed, please type\nE|M|H-<digits>\neg. E-1000");
		        }
			}
		});
		seedPanel.add(btnSeed);
		

		JPanel creditPanel = new JPanel();
		creditPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		creditPanel.setOpaque(false);
		JButton creditBtn = new JButton();
		creditBtn.setContentAreaFilled(false);
		creditBtn.setBorderPainted(false);
		creditBtn.setFocusPainted(false);
		creditBtn.setIcon(new ImageIcon("assets/credit.jpg"));
		creditBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dsPcs.firePropertyChange("credits", null, null);
			}
		});
		creditPanel.add(creditBtn);
		panel.add(creditPanel);

	    // background image
	    ImagePanel image = new ImagePanel(
	    		 new ImageIcon("assets/forest2.png").getImage());
		add(image);
	}

	/**
	 * Adds a JButton to the panel with an icon and sets the button to a specific size.
	 * @param text Name of the button
	 * @param container The panel that the button will be added to
	 */
	private void addAButton(final String text, Container container) {
		
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(270, 65));
        button.setMaximumSize(new Dimension(270,65));
        if(text.equals("Easy")){
        	button.setIcon(new ImageIcon("assets/easy.jpg"));
        } else if(text.equals("Medium")){
        	button.setIcon(new ImageIcon("assets/medium.jpg"));
        } else if(text.equals("Hard")){
        	button.setIcon(new ImageIcon("assets/hard.jpg"));
        }
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(text.equals("Easy")){
					dsPcs.firePropertyChange("difficulty", null, "E");
				} else if(text.equals("Medium")){
					dsPcs.firePropertyChange("difficulty", null, "M");
				} else if(text.equals("Hard")){
					dsPcs.firePropertyChange("difficulty", null, "H");
				} 
			}
		});
        container.add(button);
        container.add(Box.createRigidArea(new Dimension(0,20)));
        
    }
	
	public void addPropertyChangeListener (PropertyChangeListener listener) {
		dsPcs.addPropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		dsPcs.addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener (PropertyChangeListener listener) {
		dsPcs.removePropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		dsPcs.removePropertyChangeListener(propertyName, listener);
	}
	
}