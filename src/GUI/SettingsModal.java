package GUI;

import java.awt.GridLayout;

import javax.swing.*;

public class SettingsModal extends JDialog{
	
	public SettingsModal() {
		this.setTitle("Settings");
		this.setModal(true);
		this.setLayout(new GridLayout(8,2));
		
		//Isto depois pode mudar dependendo do que faremos
		this.add(new JLabel("PEER_ID"));
		this.add(new JTextField());
		
		this.add(new JLabel("KEYS_LOCATION"));
		this.add(new JTextField());
		
		this.add(new JLabel("KEY_SIZE"));
		this.add(new JTextField());
		
		this.add(new JLabel("KEYS_ALGORITHM"));
		this.add(new JTextField());
		
		this.add(new JLabel("HS_ID"));
		this.add(new JTextField());
		
		this.add(new JLabel("SOCK_POR"));
		this.add(new JTextField());
		
		this.add(new JLabel("SOCK_ADDR"));
		this.add(new JTextField());
		
		
		
		JButton save= new JButton("Save");
		JButton cancel= new JButton("Cancel");
		this.add(save);
		this.add(cancel);
		this.pack();
		this.setLocationRelativeTo(null);
		
	}

}
