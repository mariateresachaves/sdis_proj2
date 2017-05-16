import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import Configs.Util;

public class ReceiveThread extends Thread {
	
	private static DatagramSocket socket;
	private static MulticastSocket mcast_socket;
	private static InetAddress mcast_address;
	
	public void run() {

		try {
			socket = new DatagramSocket();
			mcast_socket = new MulticastSocket(Integer.parseInt(Util.getProperties().getProperty("MC_PORT")));
			mcast_address = InetAddress.getByName(Util.getProperties().getProperty("MC_IP"));
			
			// Join multicast channel
			mcast_socket.joinGroup(mcast_address);
			
			// Receive response
			byte[] buf = new byte[256];
			DatagramPacket packet_received = new DatagramPacket(buf, buf.length);
			
			mcast_socket.receive(packet_received);

			String response = new String(packet_received.getData());
			System.out.println("msg: " + response);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Teste
		
		/*while(true) {
			try {
				Thread.sleep(5000);
				System.out.println("Hello from a thread!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
    }
}
