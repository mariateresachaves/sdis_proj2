import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import Configs.Util;

public class Peer {
	private static String name;
	private static PublicKey pubKey;
	private static PrivateKey privKey;
	private static ArrayList<String> peers = new ArrayList<String>();
	
	// Multicast
	private static DatagramSocket socket;
	private static MulticastSocket mcast_socket;
	private static InetAddress mcast_address;

	public static void main(String args[]) throws Exception {
		// Properties file must be passed as argument
		if (args.length != 1) {
			System.out.println("[?] Usage: Client <PropertiesFile>");
			System.exit(Util.ErrorCode.ERR_WRONG_ARGS.ordinal());
		}

		// Load properties file
		Util.loadPropertiesFile(args[0]);

		// Gets peer name on the properties file
		name = Util.getProperties().getProperty("PEER_ID");

		// Generate private and public key
		generateKeys();

		Thread t = new Thread(new ReceiveThread());
		t.start();
		System.out.println("BU!");
		
		// Start multicast
		startNetwork();
		
		sendMessage("Hello BU!");
		
		// Discover other peers on the network
		//discovery();
	}

	// Gets
	public static String getPeerName() {
		return name;
	}

	public static PublicKey getPubKey() {
		return pubKey;
	}

	// Generate private and public key
	private static void generateKeys() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");

		kpg.initialize(4096);

		KeyPair kp = kpg.genKeyPair();

		pubKey = kp.getPublic();
		privKey = kp.getPrivate();
	}

	// Find other peers on the network
	private static void discovery() throws IOException {
			DatagramSocket c = new DatagramSocket();
			c.setBroadcast(true);
			
			// Discover message

			byte[] sendData = "DISCOVER_PEERS".getBytes();

			DatagramPacket packetMsg = new DatagramPacket(sendData, sendData.length,
					InetAddress.getByName(Util.getProperties().getProperty("SOCK_ADDR")),
					Integer.parseInt(Util.getProperties().getProperty("SOCK_PORT")));
			
			c.send(packetMsg);
			
	        c.close();
	}
	
	private static void startNetwork() {
		try {
			socket = new DatagramSocket();
			mcast_socket = new MulticastSocket(Integer.parseInt(Util.getProperties().getProperty("MC_PORT")));
			mcast_address = InetAddress.getByName(Util.getProperties().getProperty("MC_IP"));
			
			// Join multicast channel
			mcast_socket.joinGroup(mcast_address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void sendMessage(String msg) throws IOException {
		byte[] buf = new byte[256];
		buf = msg.getBytes(Charset.forName("UTF-8"));
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		mcast_socket.send(packet);
	}

}
