package GUI;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class TabbedPanel extends JTabbedPane {
	
	
	public TabbedPanel() {
		this.addTab("DC", new DirectConnectionsPanel());
		this.addTab("Group", new GroupsPanel());
		this.addTab("Server Settings", new ServerPanel());
		
		
		this.setVisible(true);
	}
}
