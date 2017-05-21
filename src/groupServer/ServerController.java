package groupServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ServerController {

	private static ServerController svc = null;

	private static String hostnameServerID = Configs.Util.getProperties()
			.getProperty("HS_ID");
	private static String pwd = Configs.Util.getProperties().getProperty(
			"HS_PW");

	// HashMap String->MessagesOnStack
	private static HashMap<String, ArrayList<String>> clients = new HashMap<>();

	public static ServerController getController() throws IOException {
		if (svc == null) {
			ServerController.svc = new ServerController();
		}
		return svc;
	}

	private ServerController() throws IOException {

	}

	public void startServer() throws IOException {

		int portNumber = 8088;

		while (true) {
			try (ServerSocket serverSocket = new ServerSocket(portNumber);
					Socket clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(
							clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));) {
				// What to do
				String msg = in.readLine();

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

	private void retriveMessages(String msg) {
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

				// TODO: SEND MESSAGE to locationID!

				return;
			}

		}

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
