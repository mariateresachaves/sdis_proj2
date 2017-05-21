package groupServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



import org.bouncycastle.bcpg.*;

public class ClientCrontoller {

	private static ClientCrontoller instance = new ClientCrontoller();
	private static String myID;
	private static String connectedServer;

	private ClientCrontoller() {
		// TODO Auto-generated constructor stub
	}

	public static ClientCrontoller getInstance() {
		return instance;
	}

	public boolean connectToServer(String serverID, String pwd) {

		/*************************** TESTTTTTTTT *////////////////////
		
		
		 BCPGPEncryptor encryptor = new BCPGPEncryptor();
			encryptor.setArmored(false);
			encryptor.setCheckIntegrity(true);
			encryptor.setPublicKeyFilePath("./test.gpg.pub");
			encryptor.encryptFile("./test.txt", "./test.txt.enc");
		
		
		
		
		String locationID = Configs.Util.getProperties().getProperty("HS_ID");

		String req = String.format("%s:JOIN:%s", locationID, pwd);

		try (
		// 4 test
		Socket kkSocket = new Socket("ltqibw3wnfrjwmmm.onion", 80);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						kkSocket.getInputStream()));) {

			out.write(req);
			out.flush();

			// TODO:CHECK IF OK
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public String getMessages() {
		String locationID = Configs.Util.getProperties().getProperty("HS_ID",
				"BAJORAS");

		String req = String.format("%s:RQSTMSG", locationID);
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

	public boolean leaveServer(String serverID) {

		String locationID = Configs.Util.getProperties().getProperty("HS_ID",
				"BAJORAS");

		String req = String.format("%s:LEAVE", locationID);

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

	public boolean sendMessage(String serverID,String message) {

		String locationID = Configs.Util.getProperties().getProperty("HS_ID",
				"BAJORAS");

		String req = String.format("%s:SNDMSG:%s", locationID,message);

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
