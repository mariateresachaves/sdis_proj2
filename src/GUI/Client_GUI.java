package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
		JMenu aboutMenu = new JMenu("About");
		aboutMenu.addMenuListener(new MenuListener() {

			public void menuSelected(MenuEvent arg0) {
				AboutWindow aw = new AboutWindow();
				aw.setVisible(true);// TODO Auto-generated method stub

			}

			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				// nao faz nada

			}

			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				// nao faz nada

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
				SettingsModal sm= new SettingsModal();
				sm.setVisible(true);
				
			}
		});

		fileMenu.add(settings);
		fileMenu.add(quit);

		barramenu.add(fileMenu);
		barramenu.add(aboutMenu);
		this.setJMenuBar(barramenu);

		this.getContentPane().add(new TabbedPanel());
		this.setVisible(true);
		this.setTitle("Crypto Chat");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
