package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PeerListPanel extends JList{

	public static HashMap<String, ArrayList<String>> conversas = new HashMap<>();
	
	public PeerListPanel() {
		this.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String conv="";
				if(e!=null){
					
					String ind=(String)PeerListPanel.this.getSelectedValue();
					
					for (Map.Entry<String, ArrayList<String>> entry : DirectConnectionsPanel.pl.conversas.entrySet()) {
					    String key = entry.getKey();
					    ArrayList<String> value = entry.getValue();
					    if(key.equalsIgnoreCase(ind)){
					    	for(String x:value){
					    		conv+=">"+x+"\n";
					    	}
					    	
							
					    	
					    }
					}
					
					DirectConnectionsPanel.cp.conversa.setText(conv);
				}
				
			}
		});
	}
	
	
}
