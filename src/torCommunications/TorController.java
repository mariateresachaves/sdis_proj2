package torCommunications;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TorController {

	public TorController() {
		System.setProperty("socksProxyHost", "127.0.0.1");
		System.setProperty("socksProxyPort", "9550");

	}

	public static ArrayList<String> readTorConfigFile(File f)
			throws FileNotFoundException {
		ArrayList<String> res = new ArrayList<String>();

		if (f == null) {
			System.err.println("Null Reference to torrc file");
		}
		File parsingF = new File(f.getPath());
		Scanner sc = new Scanner(parsingF);

		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			// Existe um serviço configurado, vai ser gravado como
			// hostname:port:hidden_service_domain
			if (line.startsWith("HiddenServiceDir")) {
				String path = line.split(" ")[1];

				String listingPort = sc.nextLine();
				String port = listingPort.split(":")[1];

				String getHiddenDomain = getHostname(path);
				if (getHiddenDomain == null) {
					System.err
							.println("Detected not configured hidden service->"
									+ path);
				} else {
					res.add(String.format("%s:%s:%s", path, port,
							getHiddenDomain));
				}

			}
		}

		return res;
	}

	public static String getHostname(String path) throws FileNotFoundException {
		if (path != null) {

			File f;

			if (path.endsWith("/")) {
				f = new File(path + "hostname");
			} else {
				f = new File(path + "/hostname");
			}
			Scanner sc = new Scanner(f);
			return sc.nextLine();
		}
		return null;

	}

	public boolean isTorAddres(String domain) {
		return domain.endsWith(".onion");
	}

	public void torListeningService(int port) {

		try {
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
					true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			out.println();
			System.out.println("In->");

		} catch (Exception e) {
			System.err.println("The following Exception has ocorred!");
			System.err.println(e.getMessage());
		}
	}

	// http://stackoverflow.com/questions/54686/how-to-get-a-list-of-current-open-windows-process-with-java
	public boolean isTorRunning() {
		try {
			String line;
			String exec = "pgrep -l tor$";
			Process p = Runtime.getRuntime().exec(exec);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (line.contains("tor")) {
					// Tor is Running
					return true;
				}
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return false;

	}

	//http://www.xyzws.com/Javafaq/how-to-use-httpurlconnection-post-data-to-web-server/139
	public static boolean checkTor() {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL("https://check.torproject.org/");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes("");
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			
			if(response.toString().contains("Congratulations. This browser is configured to use Tor.")){
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {

			e.printStackTrace();
			return false;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
