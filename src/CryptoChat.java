import groupServer.ClientCrontoller;
import groupServer.ServerController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;

import torCommunications.TorController;
import GUI.Client_GUI;

public class CryptoChat {

	public static TorController tc = new TorController();

	public static void main(String[] args) throws IOException {

		// Settings
		Configs.Util.loadPropertiesFile("./settings.properties");
		System.out.println(Configs.Util.getProperties().getProperty("HS_ID"));
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		//ClientCrontoller.getInstance().sendMessage("ltqibw3wnfrjwmmm.onion","SENT");
		//ServerController.getController().run();

		new Client_GUI();

		//System.exit(0);
		System.out.println(tc.isTorRunning());
		ArrayList<String> abc = tc.readTorConfigFile(new File(Configs.Util
				.getProperties().getProperty("TORRC", "/etc/tor/torrc")));

		for (String x : abc) {
			System.out.println(x);
		}

		System.out.println(tc.checkTor());
		// start GUI

	}

}
