package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import groupServer.ClientCrontoller;

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
				String conv="";
				String ind=(String)DirectConnectionsPanel.pl.getSelectedValue();
				System.out.println("Debug TO->"+DirectConnectionsPanel.pl.getSelectedValue().toString());
				try {
					synchronized (DirectConnectionsPanel.pl) {
					ClientCrontoller.getInstance().sendMessage(DirectConnectionsPanel.pl.getSelectedValue().toString(), areaDeConversa.getText());
					
					for (Map.Entry<String, ArrayList<String>> entry : DirectConnectionsPanel.pl.conversas.entrySet()) {
					    String key = entry.getKey();
					    ArrayList<String> value = entry.getValue();
					    if(key.equalsIgnoreCase(ind)){
					    	for(String x:value){
					    		conv+=">"+x+"\n";
					    	}
					    	conv+=ComsPanel.this.areaDeConversa.getText();
					    	
							
					    	
					    }
					}
					
					DirectConnectionsPanel.cp.conversa.setText(conv);
					
					}
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				areaDeConversa.setText("");
				
			}
		});
		sender.add(send,BorderLayout.EAST);
		
		
		JSplitPane body= new JSplitPane(JSplitPane.VERTICAL_SPLIT,scroll,sender);
		body.setDividerLocation(250);
		
		this.add(body,BorderLayout.CENTER);
	}
}