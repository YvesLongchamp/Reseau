package projet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
			sc.hasNext();
			String text = sc.nextLine();
			int increment = Integer.parseInt(text);
			PrintWriter pw = new PrintWriter(output);
			if (increment == 0) {
				System.out.println("pwait");
				pw.println(1);
			} else {
				System.out.println(increment);
				Client client = new Client(increment - 1);
				client.clientRun();
				System.out.println(increment);
				int answer = client.getAnswer();
				client.setAnswer(answer * increment);
				System.out.println("answer * increment " + answer);
				System.out.println("********" + answer * increment);
				pw.println(answer * increment);
			}
			pw.flush();
			try {
				this.socket.close();
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(50000);

			while (true) {
				Socket socketClient = serverSocket.accept();
				ClientThread clientThread = new Server().new ClientThread(
						socketClient);
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
