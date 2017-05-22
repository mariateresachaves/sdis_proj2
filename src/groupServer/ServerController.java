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

import org.bouncycastle.openpgp.PGPException;

import BouncyCastle.BCPGPDecryptor;
import BouncyCastle.BCPGPEncryptor;

public class ServerController {

	private static ServerController svc = null;

	private static String hostnameServerID = Configs.Util.getProperties()
			.getProperty("HS_ID");
	private static String pwd = Configs.Util.getProperties().getProperty(
			"HS_PW");
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
		ServerController.privateKeyFileLocation = "bin/keys/Private/nmjopc4w7u4a5kse.onion-public.key";
	}

	public void startServer() throws IOException {

		int portNumber = 8080;

		while (true) {
			try (ServerSocket serverSocket = new ServerSocket(portNumber);
					Socket clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(
							clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));) {
				// What to do
				String msg = in.readLine();

				msg = decrypt(msg);

				if (isJoinning(msg)) {
					joinToGroup(msg);
				}

				if (isSendingMessage(msg)) {
					replicateMessage(msg);
				}

				if (isRqstMessages(msg)) {
					retriveMessages(msg);
				}

				if (isLeavingRoom(msg)) {
					justLeave(msg);
				}

				clientSocket.close();
				// End of what to do
			}
		}
	}

	private String decrypt(String msg) {
		BCPGPDecryptor dec = new BCPGPDecryptor();
		File tempf = null;
		try {
			// Public Key of the server
			dec.setPrivateKeyFilePath(privateKeyFileLocation);

			// Create Temporary File
			File temp = File.createTempFile("msgSE", ".txt");
			Formatter f = new Formatter(temp);
			f.format("%s", msg);
			f.close();

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

		for (Entry<String, ArrayList<String>> entry : clients.entrySet()) {
			String key = entry.getKey();

			if (!key.equalsIgnoreCase(locationID)) {

				entry.getValue().add(msg.split(":")[2]);

				return;
			}

		}

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

				
				retMsg=encryptMsg(retMsg,locationID);
				// TODO: SEND MESSAGE to locationID!
				
				return;
			}

		}

	}

	private String encryptMsg(String req,String destination) throws FileNotFoundException {

		BCPGPEncryptor enc = new BCPGPEncryptor();
		File tempf = null;
		try {
			// Public Key of the server
			enc.setPublicKeyFilePath("bin/keys/Public/"+destination+"-public.key");

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
