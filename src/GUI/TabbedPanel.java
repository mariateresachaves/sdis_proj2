package GUI;

import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class TabbedPanel extends JTabbedPane {
	
	
	public TabbedPanel() {
		this.addTab("DC", new DirectConnectionsPanel());
		
		
		this.setVisible(true);
	}
}
