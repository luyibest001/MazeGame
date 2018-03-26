import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.border.EmptyBorder;

/**
 * Attributes the artistic work of the images used to the original owner
 */
public class CreditsScreen extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3141550504897101397L;
	PropertyChangeSupport csPcs;
	
	/**
	 * Constructor class for CreditsScreen
	 */
	public CreditsScreen(){
		csPcs = new PropertyChangeSupport(this);
		showContent();
	}
	
	public void showContent(){
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
		
		JPanel backPanel = new JPanel();
		backPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		backPanel.setOpaque(false);
		JButton backBtn = new JButton();
		backBtn.setOpaque(false);
		backBtn.setContentAreaFilled(false);
		backBtn.setBorderPainted(false);
		backBtn.setFocusPainted(false);
		backBtn.setIcon(new ImageIcon("assets/back.jpg"));
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				csPcs.firePropertyChange("exit", null, null);
			}
		});
		backPanel.add(backBtn);
		panel.add(backPanel, BorderLayout.NORTH);
		
		JPanel creditsPanel = new JPanel();
		creditsPanel.setOpaque(false);
		BoxLayout layout1 = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout1);
		panel.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
		panel.setOpaque(false);
		panel.add(creditsPanel, BorderLayout.CENTER);
		
		JLabel forest = new JLabel("Forests: "
				+ "Copyright 2012-2013 Julien Jorge <julien.jorge@stuff-o-matic.com>");
		creditsPanel.add(forest);
		JLabel treasure = new JLabel("Treasure chest: http://opengameart.org/content/tresure-chest");
		creditsPanel.add(treasure);
		JLabel nonCharacter = new JLabel("Non-character sprites: http://opengameart.org/content/forest-tiles-0");
		creditsPanel.add(nonCharacter);
		JLabel character = new JLabel("Character sprites: "
				+ "http://opengameart.org/content/space-guy-8-directional-animated-character-16x16");
		creditsPanel.add(character);
		// background image
	    ImagePanel image = new ImagePanel(
	    		 new ImageIcon("assets/forest3.png").getImage());
		add(image);
	}
	public void addPropertyChangeListener (PropertyChangeListener listener) {
		csPcs.addPropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		csPcs.addPropertyChangeListener(propertyName, listener);
	}
	
	public void removePropertyChangeListener (PropertyChangeListener listener) {
		csPcs.removePropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener (String propertyName, PropertyChangeListener listener) {
		csPcs.removePropertyChangeListener(propertyName, listener);
	}
	
	
}
