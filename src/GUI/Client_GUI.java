package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

public class Client_GUI extends JFrame{

	public Client_GUI() {
		Dimension d=new Dimension(600,400);
		this.setPreferredSize(d);
		this.setSize(d);
		
		JMenuBar barramenu = new JMenuBar();
		JMenu fileMenu= new JMenu("File");
		JMenu aboutMenu= new JMenu("About");
		
		JMenuItem quit= new JMenuItem("Quit");
		JMenuItem settings= new JMenuItem("Settings");
		
		
		fileMenu.add(settings);
		fileMenu.add(quit);
		
		
		barramenu.add(fileMenu);
		barramenu.add(aboutMenu);
		this.setJMenuBar(barramenu);
		
		
		this.getContentPane().add(new TabbedPanel());
		this.setVisible(true);
		this.setTitle("Crypto Chat");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}
