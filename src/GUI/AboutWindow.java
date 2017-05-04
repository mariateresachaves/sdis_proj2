package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AboutWindow extends JDialog{
	public AboutWindow() {
		this.setModal(true);
		this.setLayout(new GridLayout(3,2));
		
		this.add(new JLabel("The Bloody Authors"));
		this.add(new JLabel(" "));
		
		this.add(new JLabel(" "));
		this.add(new JLabel("Maria Teresa Chaves"));
		this.add(new JLabel(" "));
		this.add(new JLabel("Pedro Rodrigues"));
		
		this.add(new JLabel(" "));
		JButton disp=new JButton("Close");
		disp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				AboutWindow.this.dispose();
				
			}
		});
		this.add(disp);
		
		this.pack();
		this.setLocationRelativeTo(null);
		
	}
}
