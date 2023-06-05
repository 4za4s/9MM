package Display;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout; 
import java.awt.Component; 

public class PlayerSelectionDisplay extends MenuDisplay{

public PlayerSelectionDisplay(int width, int height, Window window) {
        super(width, height, window);
        
        JFrame playerSelectionFrame = new JFrame("Player Selection");
        playerSelectionFrame.setVisible(true);
        playerSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerSelectionFrame.setSize(500, 500);
        playerSelectionFrame.setLocation(430, 100);

        JPanel playerSelectionPanel = new JPanel();
        playerSelectionPanel.setLayout(new BoxLayout(playerSelectionPanel, BoxLayout.Y_AXIS)); 

        playerSelectionFrame.add(playerSelectionPanel);

        JLabel playerSelectionLabel = new JLabel("Select Your Type of Player");
        playerSelectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        playerSelectionPanel.add(playerSelectionLabel);

        String[] playerChoices = { "AI Player", "Human Player" };

        final JComboBox<String> playerType = new JComboBox<String>(playerChoices);

        playerType.setMaximumSize(playerType.getPreferredSize()); 
        playerType.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerSelectionPanel.add(playerType);

        playerSelectionPanel.add(start);

        playerSelectionFrame.setVisible(true); 
    }
}
