package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Client_GUI extends JFrame {

	public Client_GUI() {
		Dimension d = new Dimension(600, 400);
		this.setPreferredSize(d);
		this.setSize(d);

		JMenuBar barramenu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				AboutWindow aw = new AboutWindow();
				aw.setVisible(true);
			}
		});

		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Client_GUI.this.dispose();

			}
		});
		quit.setMnemonic(KeyEvent.VK_W);
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		JMenuItem settings = new JMenuItem("Settings");
		settings.setMnemonic(KeyEvent.VK_S);
		settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		settings.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				SettingsModal sm = new SettingsModal();
				sm.setVisible(true);

			}
		});
		helpMenu.add(about);

		JMenuItem cTor = new JMenuItem("Check Tor");
		cTor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				boolean recv = torCommunications.TorController.checkTor();
				if (recv) {
					JOptionPane.showMessageDialog(null, "You are connected to TOR network",
							"Succesfull",
							JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Something went wrong and you are not longer connected to the Tor network",
							"ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		JMenuItem configTor= new JMenuItem("Tor Configuration");
		configTor.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				TorConfig tconfig;
				try {
					tconfig = new TorConfig();
					tconfig.setVisible(true);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});		
		
		fileMenu.add(configTor);
		fileMenu.add(cTor);
		fileMenu.add(settings);
		fileMenu.add(quit);

		barramenu.add(fileMenu);
		barramenu.add(helpMenu);
		this.setJMenuBar(barramenu);

		this.getContentPane().add(new TabbedPanel());
		this.setVisible(true);
		this.setTitle("Crypto Chat");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
