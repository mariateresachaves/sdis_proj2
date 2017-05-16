package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.rmi.CORBA.Util;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;



import torCommunications.TorController;

public class TorConfig extends JDialog{
	public TorConfig() throws FileNotFoundException {
		this.setTitle("Tor Configuration");
		
		this.setModal(true);
		
		this.setLayout(new BorderLayout());
		
		JPanel north=new JPanel();
		north.setLayout(new GridLayout(1,4));
		JTextField thost= new JTextField();
		JTextField tport=new JTextField();
		north.add(new JLabel("Tor Host"));
		north.add(thost);
		north.add(new JLabel("Tor Port"));
		north.add(tport);
		

		String[] data=TorController.readTorConfigFile(new File(Configs.Util.getProperties().getProperty("TORRC", "/etc/tor/torrc"))).toArray(new String[0]);
		JList<String> torHiddenS= new JList<String>(data);
		JScrollPane center= new JScrollPane(torHiddenS);		

		
		
		
		
		JButton disp=new JButton("Close");
		disp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				TorConfig.this.dispose();
				
			}
		});
		JButton save=new JButton("save");
		disp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				//FALTA ADICIONAR AS OPCOES
				TorConfig.this.dispose();
				
			}
		});
		
		JPanel south= new JPanel(new GridLayout(1,2));
		south.add(disp);
		south.add(save);
		
		this.add(north,BorderLayout.NORTH);
		this.add(center,BorderLayout.CENTER);
		this.add(south,BorderLayout.SOUTH);
		this.pack();
		this.setLocationRelativeTo(null);
		
	}
}
