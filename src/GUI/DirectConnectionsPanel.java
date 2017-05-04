package GUI;

import javax.swing.*;


public class DirectConnectionsPanel extends JSplitPane {

	private PeerListPanel pl= new PeerListPanel();
	
	public DirectConnectionsPanel() {
		super(JSplitPane.HORIZONTAL_SPLIT, null, new ComsPanel());
		this.setLeftComponent(pl);
		
		
		this.setOneTouchExpandable(true);
		this.setDividerLocation(150);

	}

}
