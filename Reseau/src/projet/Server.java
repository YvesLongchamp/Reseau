package projet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
	public class ClientThread extends Thread {
		private Socket socket;
		private InputStream input;
		private OutputStream output;

		ClientThread(Socket socket) {
			try {
				this.socket = socket;
				output = socket.getOutputStream();
				input = socket.getInputStream();
			} catch (Exception e) {
			}
		}
		@Override
		public void run() {
			Scanner sc = new Scanner(input);
			System.out.println("nouveau scanner");
			sc.hasNext();
			System.out.println("après hasNext clientThread");
			String text = sc.nextLine();
			int increment = Integer.parseInt(text);
			PrintWriter pw = new PrintWriter(output);
			System.out.println("après printWriter");
			if(increment == 0) {
				System.out.println(increment);
				pw.println(increment);
			} else {
				System.out.println(increment);
				Client client = new Client(increment - 1);
				client.clientRun();
				int answer = client.getAnswer();
				pw.println(answer * increment);
			}
			pw.flush();
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("****fin client****");
		}
		
	}

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(50000);

			while (true) {

				/**
				 * on alloue un nouveau serveur, sur le port 25565 on accepte
				 * les connexions des clients on prends le stream qui rentre on
				 * scan les bits, et on en ressort le string on met un texte on
				 * l'affiche.
				 */
				Socket socketClient = serverSocket.accept();
				System.out.println("Server main while true");
				ClientThread clientThread =  new Server().new ClientThread(socketClient);
				clientThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
