package groupServer;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ListModel;

import org.bouncycastle.openpgp.PGPException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import BouncyCastle.BCPGPDecryptor;
import BouncyCastle.BCPGPEncryptor;
import GUI.DirectConnectionsPanel;
import GUI.PeerListPanel;

public class ServerController implements Runnable{

	private static ServerController svc = null;

	private static String hostnameServerID = Configs.Util.getProperties().getProperty("HS_ID");
	private static String pwd = Configs.Util.getProperties().getProperty("HS_PW");
	private static String privateKeyFileLocation;

	// HashMap String->MessagesOnStack
	private static HashMap<String, ArrayList<String>> clients = new HashMap<>();

	public static ServerController getController() throws IOException {
		if (svc == null) {
			ServerController.svc = new ServerController();
		}
		return svc;
	}

	private ServerController() throws IOException {

		ServerController.privateKeyFileLocation = "bin/keys/Private/"+hostnameServerID +"-private.key";

	}
	public void run(){

		int portNumber = 8080;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException ex) {
			System.out.println("Can't setup server on this port number. ");
		}

		while (true) {
			try {

				Socket socket = null;
				InputStream in = null;
				OutputStream out = null;
				File f = File.createTempFile("RecvE", ".txt");

				try {
					socket = serverSocket.accept();
				} catch (IOException ex) {
					System.out.println("Can't accept client connection. ");
				}

				try {
					in = socket.getInputStream();
				} catch (IOException ex) {
					System.out.println("Can't get socket input stream. ");
				}

				try {
					out = new FileOutputStream(f);
				} catch (FileNotFoundException ex) {
					System.out.println("File not found. ");
				}

				byte[] bytes = new byte[16 * 1024];

				int count;
				while ((count = in.read(bytes)) > 0) {
					out.write(bytes, 0, count);
				}

				// What to do
				// String msg = in.readLine();
				// String buff="";
				// while((buff=in.readLine())!=null){
				// msg+=buff;
				// }

				// msg= new String(Base64.decode(msg));
				System.out.println(f.toPath());
				String msg = decryptFile(f.getAbsolutePath());

				System.out.println(msg);

				if (isJoinning(msg)) {
					joinToGroup(msg);
				}

				if (isSendingMessage(msg)) {
					// Replicate to Self
					replicateMessage(msg);
				}

				if (isRqstMessages(msg)) {
					retriveMessages(msg);
				}

				if (isLeavingRoom(msg)) {
					justLeave(msg);
				}

				out.close();
				in.close();
				socket.close();
				// serverSocket.close();

				// End of what to do
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Something bad happen, check logs");
			}
		}
	}

	private String decryptFile(String path) {
		BCPGPDecryptor dec = new BCPGPDecryptor();
		dec.setPassword("");
		File tempf = null;

		try {
			// Public Key of the server
			System.out.println("Decrypt using "+privateKeyFileLocation);
			dec.setPrivateKeyFilePath(privateKeyFileLocation);

			tempf = File.createTempFile("msgSU", ".txt");
			System.out.println(tempf.getAbsolutePath());
			dec.decryptFile(new File(path), tempf);

		} catch (PGPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (tempf != null) {
			Path path1 = Paths.get(tempf.getAbsolutePath());
			byte[] data = null;
			try {
				data = Files.readAllBytes(path1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String encrypt = new String(data);
			return encrypt;
		}
		return null;

	}

	private String decrypt(String msg) {
		BCPGPDecryptor dec = new BCPGPDecryptor();
		dec.setPassword("");
		File tempf = null;

		try {
			// Public Key of the server
			dec.setPrivateKeyFilePath(privateKeyFileLocation);

			// Create Temporary File
			File temp = File.createTempFile("msgSE", ".txt");
			Formatter f = new Formatter(temp);
			f.format("%s", msg);
			f.close();

			temp = new File(temp.getPath());

			tempf = File.createTempFile("msgSU", ".txt");
			System.out.println(tempf.getAbsolutePath());
			dec.decryptFile(temp, tempf);

		} catch (PGPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (tempf != null) {
			Path path = Paths.get(tempf.getAbsolutePath());
			byte[] data = null;
			try {
				data = Files.readAllBytes(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String encrypt = new String(data);
			return encrypt;
		}
		return null;

	}

	private void replicateMessage(String msg) {
		String locationID = msg.split(":")[0];
		String mensagem = msg.split(":")[2];
		
		for (Map.Entry<String, ArrayList<String>> entry : DirectConnectionsPanel.pl.conversas.entrySet()) {
		    String key = entry.getKey();
		    ArrayList<String> value = entry.getValue();
		    if(key.equalsIgnoreCase(locationID)){
		    	value.add(mensagem);
		    	DirectConnectionsPanel.pl.setListData(DirectConnectionsPanel.pl.conversas.keySet().toArray());
				
		    	return;
		    }
		}
		ArrayList<String> novalista= new ArrayList<>();
		novalista.add(mensagem);
		DirectConnectionsPanel.pl.conversas.put(locationID, novalista);
		DirectConnectionsPanel.pl.setListData(DirectConnectionsPanel.pl.conversas.keySet().toArray());
		return;
		
		

		/*
		 * for (Entry<String, ArrayList<String>> entry : clients.entrySet()) {
		 * String key = entry.getKey();
		 * 
		 * if (!key.equalsIgnoreCase(locationID)) {
		 * 
		 * entry.getValue().add(msg.split(":")[2]);
		 * 
		 * return; }
		 * 
		 * }
		 */
	}

	private void retriveMessages(String msg) throws FileNotFoundException {
		String locationID = msg.split(":")[0];

		for (Entry<String, ArrayList<String>> entry : clients.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();

			if (key.equalsIgnoreCase(locationID)) {
				// Obter mensagens pendentes
				String retMsg = "";
				for (String x : value) {
					retMsg += x;
				}

				retMsg = encryptMsg(retMsg, locationID);
				// TODO: SEND MESSAGE to locationID!

				return;
			}

		}

	}

	private String encryptMsg(String req, String destination) throws FileNotFoundException {

		BCPGPEncryptor enc = new BCPGPEncryptor();
		File tempf = null;
		try {
			// Public Key of the server
			enc.setPublicKeyFilePath("bin/keys/Public/" + destination + "-public.key");

			// Create Temporary File
			File temp = File.createTempFile("msgU", ".txt");
			Formatter f = new Formatter(temp);
			f.format("%s", req);
			f.close();

			tempf = File.createTempFile("msgE", ".txt");
			System.out.println(tempf.getAbsolutePath());
			enc.encryptFile(temp, tempf);

		} catch (PGPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (tempf != null) {
			Path path = Paths.get(tempf.getAbsolutePath());
			byte[] data = null;
			try {
				data = Files.readAllBytes(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String encrypt = new String(data);
			return encrypt;
		}
		return null;
	}

	private void justLeave(String msg) {
		String locationID = msg.split(":")[0];

		for (String key : clients.keySet()) {
			if (key.equalsIgnoreCase(locationID)) {
				clients.remove(key);
				return;
			}
		}

	}

	private void joinToGroup(String msg) {
		String locationID = msg.split(":")[0];
		// TODO, Check for PublicKey of the locationID

		// Check for PWD
		if (msg.split(":")[2].equals(pwd)) {
			ArrayList<String> simulatedStack = new ArrayList<>();
			clients.put(locationID, simulatedStack);
		}

		// Client added

	}

	private boolean isLeavingRoom(String msg) {
		String[] split = msg.split(":");

		String action = split[1];

		if (action.equalsIgnoreCase("LEAVE")) {
			return true;
		}

		return false;
	}

	private boolean isRqstMessages(String msg) {
		String[] split = msg.split(":");

		String action = split[1];

		if (action.equalsIgnoreCase("RQSTMSG")) {
			return true;
		}

		return false;
	}

	private boolean isSendingMessage(String msg) {
		String[] split = msg.split(":");

		String action = split[1];

		if (action.equalsIgnoreCase("SNDMSG")) {
			return true;
		}

		return false;
	}

	private boolean isJoinning(String msg) {
		String[] split = msg.split(":");

		String action = split[1];

		if (action.equalsIgnoreCase("JOIN")) {
			return true;
		}

		return false;
	}

	public void getParticipants(String msg) {
		// TODO:THIS
	}

}