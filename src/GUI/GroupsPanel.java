package GUI;

import java.awt.Component;

import javax.swing.JSplitPane;

public class GroupsPanel extends JSplitPane {

	private PeerListPanel pl= new PeerListPanel();
	private GroupListPanel gl= new GroupListPanel();
	private JSplitPane groupOrganizarJSplitPane;
	
	public GroupsPanel() {
		super(JSplitPane.HORIZONTAL_SPLIT, null, new ComsPanel());
		
		//JSplit com Grupos e Peers
		groupOrganizarJSplitPane= new JSplitPane(JSplitPane.VERTICAL_SPLIT, gl, pl);
		
		groupOrganizarJSplitPane.setDividerLocation(150);
		
		this.setLeftComponent(groupOrganizarJSplitPane);
		
		
		this.setOneTouchExpandable(true);
		this.setDividerLocation(150);

	}
	
}
