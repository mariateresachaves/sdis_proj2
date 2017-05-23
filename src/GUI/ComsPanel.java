package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class ComsPanel extends JPanel{

	public JTextArea conversa= new JTextArea();
	public JTextArea areaDeConversa= new JTextArea();
	public JButton send= new JButton("Send");
	
	public ComsPanel() {
		super();
		
		
		this.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(conversa);
		conversa.setAutoscrolls(true);
		conversa.setEnabled(false);
		
		
		
		JPanel sender=new JPanel();
		areaDeConversa.setAutoscrolls(true);
		areaDeConversa.setEnabled(true);
		areaDeConversa.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					e.consume();
					ComsPanel.this.send.doClick();
				}
				
			}
		});
		JScrollPane scrollMensagem = new JScrollPane(areaDeConversa);
		
		sender.setLayout(new BorderLayout());
		sender.add(scrollMensagem,BorderLayout.CENTER);
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		sender.add(send,BorderLayout.EAST);
		
		
		JSplitPane body= new JSplitPane(JSplitPane.VERTICAL_SPLIT,scroll,sender);
		body.setDividerLocation(250);
		
		this.add(body,BorderLayout.CENTER);
	}
}
