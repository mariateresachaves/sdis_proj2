package GUI;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class ComsPanel extends JPanel{

	private JTextArea conversa= new JTextArea();
	private JTextArea areaDeConversa= new JTextArea();
	
	public ComsPanel() {
		super();
		
		
		this.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(conversa);
		conversa.setAutoscrolls(true);
		conversa.setEnabled(false);
		
		
		
		JPanel sender=new JPanel();
		areaDeConversa.setAutoscrolls(true);
		areaDeConversa.setEnabled(true);
		JScrollPane scrollMensagem = new JScrollPane(areaDeConversa);
		
		sender.setLayout(new BorderLayout());
		sender.add(scrollMensagem,BorderLayout.CENTER);
		sender.add(new JButton("Send"),BorderLayout.EAST);
		
		
		JSplitPane body= new JSplitPane(JSplitPane.VERTICAL_SPLIT,scroll,sender);
		body.setDividerLocation(250);
		
		this.add(body,BorderLayout.CENTER);
	}
}
