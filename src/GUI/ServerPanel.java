package GUI;

import java.awt.GridLayout;

import javax.swing.*;

public class ServerPanel extends JSplitPane {

	private static final String Color = null;
	private JLabel running = new JLabel("No");
	private JTextArea log = new JTextArea();

	public ServerPanel() {
		super(JSplitPane.VERTICAL_SPLIT);
		
		JPanel forma = new JPanel();
		forma.setLayout(new GridLayout(3, 2));
		forma.add(new JLabel("Server Settings"));
		forma.add(new JLabel(""));

		forma.add(new JLabel("Running"));
		running.setBackground(java.awt.Color.RED);
		forma.add(running);

		forma.add(new JLabel(""));
		JPanel startStopButtons = new JPanel();
		JButton startServer = new JButton("Start");
		JButton stopServer = new JButton("Stop");
		startStopButtons.add(stopServer);
		startStopButtons.add(startServer);
		forma.add(startStopButtons);

		// Log do servico
		log.setEnabled(false);

		this.setLeftComponent(forma);
		this.setRightComponent(log);

	}
}
