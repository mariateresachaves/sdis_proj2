package groupServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Scanner;

import org.bouncycastle.bcpg.*;
import org.bouncycastle.openpgp.PGPException;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import BouncyCastle.BCPGPDecryptor;
import BouncyCastle.BCPGPEncryptor;

public class ClientCrontoller {

	private static ClientCrontoller instance = new ClientCrontoller();
	private static String myID;
	private static String connectedServer;
	private static String pathtoServerPubKey;

	private ClientCrontoller() {
		ClientCrontoller.pathtoServerPubKey="bin/keys/Public/nmjopc4w7u4a5kse.onion-public.key";
	}

	public static ClientCrontoller getInstance() {
		return instance;
	}

	public boolean connectToServer(String serverID, String pwd) {

		String locationID = Configs.Util.getProperties().getProperty("HS_ID");

		String req = String.format("%s:JOIN:%s", locationID, pwd);
		String fileE ="";
		try {
			fileE= encryptMsg(req);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
		// 4 test
		Socket kkSocket = new Socket(serverID, 80);
				
		/*PrintWriter out = new PrintWriter(kkSocket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));) {

			
			out.write(b);

			out.flush();
*/
				File file = new File(fileE);
		        // Get the size of the file
		        long length = file.length();
		        byte[] bytes = new byte[16 * 1024];
		        InputStream in = new FileInputStream(file);
		        OutputStream out = kkSocket.getOutputStream();

		        int count;
		        while ((count = in.read(bytes)) > 0) {
		            out.write(bytes, 0, count);
		        }

		        out.close();
		        in.close();
		        kkSocket.close();
			// TODO:CHECK IF OK
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

				
		return false;
	}

	private String encryptMsg(String req) throws FileNotFoundException {

		BCPGPEncryptor enc = new BCPGPEncryptor();
		File tempf = null;
		try {
			// Public Key of the server
			enc.setPublicKeyFilePath(pathtoServerPubKey);

			// Create Temporary File
			File temp = File.createTempFile("msgU", ".txt");
			Formatter f = new Formatter(temp);
			f.format("%s", req);
			f.close();

			tempf = File.createTempFile("msgE", ".txt");
			System.out.println(tempf.getAbsolutePath());
			enc.encryptFile(temp, tempf);
			return tempf.getAbsolutePath();

		} catch (PGPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*
		if (tempf != null) {
			Path path = Paths.get(tempf.getAbsolutePath());
			byte[] data = null;
			try {
				data = Files.readAllBytes(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	*/		
			return null;
		
	}

	public String getMessages() throws FileNotFoundException {
		String locationID = Configs.Util.getProperties().getProperty("HS_ID",
				"BAJORAS");

		String req = String.format("%s:RQSTMSG", locationID);
		req=encryptMsg(req);
		String res = "";
		try (
		// 4 test
		Socket kkSocket = new Socket("ltqibw3wnfrjwmmm.onion", 80);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						kkSocket.getInputStream()));) {

			out.write(req);
			out.flush();

			res = in.readLine();
			System.out.println("Respnse");

			System.out.println("Socket Closed");
			System.out.println(res);

			kkSocket.close();
			
			return res;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;

	}

	public boolean leaveServer(String serverID) throws FileNotFoundException {

		String locationID = Configs.Util.getProperties().getProperty("HS_ID",
				"BAJORAS");

		String req = String.format("%s:LEAVE", locationID);
		req=encryptMsg(req);
		try (
		// 4 test
		Socket kkSocket = new Socket("ltqibw3wnfrjwmmm.onion", 80);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						kkSocket.getInputStream()));) {

			out.write(req);
			out.flush();
			kkSocket.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public boolean sendMessage(String serverID, String message) throws FileNotFoundException {

		String locationID = Configs.Util.getProperties().getProperty("HS_ID",
				"BAJORAS");

		String req = String.format("%s:SNDMSG:%s", locationID, message);
		req=encryptMsg(req);
		try (
		// 4 test
		Socket kkSocket = new Socket("ltqibw3wnfrjwmmm.onion", 80);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						kkSocket.getInputStream()));) {

			out.write(req);
			out.flush();
			kkSocket.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}
}
