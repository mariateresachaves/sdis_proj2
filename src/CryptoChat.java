import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import torCommunications.TorController;
import GUI.Client_GUI;

public class CryptoChat {

	public static TorController tc = new TorController(); 
	
	public static void main(String[] args) throws FileNotFoundException {
		

		System.out.println(tc.isTorRunning());
		ArrayList<String> abc = tc
				.readTorConfigFile(new File(Configs.Util.getProperties().getProperty("TORRC", "/etc/tor/torrc")));

		for (String x : abc) {
			System.out.println(x);
		}

		System.out.println(tc.checkTor());
		// start GUI
		new Client_GUI();

	}

}
