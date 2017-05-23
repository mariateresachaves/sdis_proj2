package GUI;

import javax.swing.*;


public class DirectConnectionsPanel extends JSplitPane {

	public static PeerListPanel pl= new PeerListPanel();
	public static ComsPanel cp= new ComsPanel();
	
	public DirectConnectionsPanel() {
		super(JSplitPane.HORIZONTAL_SPLIT, null,cp);
		this.setLeftComponent(pl);
		
		
		this.setOneTouchExpandable(true);
		this.setDividerLocation(150);

	}

}
