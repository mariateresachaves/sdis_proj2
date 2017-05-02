package GUI;

import javax.swing.JSplitPane;

public class DirectConnectionsPanel extends JSplitPane {

	public DirectConnectionsPanel() {
		super(JSplitPane.HORIZONTAL_SPLIT, new PeerListPanel(), new ComsPanel());
		this.setOneTouchExpandable(true);
		this.setDividerLocation(150);

	}

}
