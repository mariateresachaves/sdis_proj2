import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Server {
	private static PublicKey pubKey;
	private static PrivateKey privKey;
	
	public static void main(String[] args) throws Exception {
		// Keys
		generateKeys();
		
		ServerSocket sersock = new ServerSocket(3000);

		System.out.println("Server ready for chatting");
		Socket sock = sersock.accept();

		// reading from keyboard (keyRead object)
		BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));

		// sending to client (pwrite object)
		OutputStream ostream = sock.getOutputStream();
		PrintWriter pwrite = new PrintWriter(ostream, true);

		// receiving from server ( receiveRead object)
		InputStream istream = sock.getInputStream();
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

		String receiveMessage, sendMessage;
		while (true) {
			if ((receiveMessage = receiveRead.readLine()) != null)
				System.out.println("Original received: " + receiveMessage);

			//sendMessage = keyRead.readLine();
			//pwrite.println(sendMessage);
			//pwrite.flush();
		}
	}
	
	private static void generateKeys() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
		
		kpg.initialize(1024);
		
		KeyPair kp = kpg.generateKeyPair();
		
		pubKey = kp.getPublic();
		privKey = kp.getPrivate();
	}
}
