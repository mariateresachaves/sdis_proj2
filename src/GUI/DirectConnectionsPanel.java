package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


public class DirectConnectionsPanel extends JSplitPane {

	public static PeerListPanel pl= new PeerListPanel();
	public static ComsPanel cp= new ComsPanel();
	
	public DirectConnectionsPanel() {
		super(JSplitPane.HORIZONTAL_SPLIT, null,cp);
		JPanel leftThing= new JPanel(new BorderLayout());
		leftThing.add(pl,BorderLayout.CENTER);
		JButton addpeer= new JButton("Add Peer");
		addpeer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String onionName=JOptionPane.showInputDialog(DirectConnectionsPanel.this, "Please Insert the Onion Address to chat","Add Peer",JOptionPane.OK_CANCEL_OPTION);
				if(onionName!=null && !onionName.equalsIgnoreCase("")){
					//Try to add to the list the onion address
					ArrayList<String> novalista= new ArrayList<>();
					novalista.add("");
					//Onion Bro, My hero
					synchronized (DirectConnectionsPanel.pl) {
						DirectConnectionsPanel.pl.conversas.put(onionName, novalista);
						DirectConnectionsPanel.pl.setListData(DirectConnectionsPanel.pl.conversas.keySet().toArray());
					}
					
				}
			}
		});
		leftThing.add(addpeer,BorderLayout.SOUTH);
		this.setLeftComponent(leftThing);
		
		
		this.setOneTouchExpandable(true);
		this.setDividerLocation(150);

	}

}