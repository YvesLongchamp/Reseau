

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	private ServerSocket serverSocket;
	
	Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
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
			String text = "";
			if (sc.hasNext()) {
				text = sc.nextLine();
			}
			int increment = Integer.parseInt(text);
			PrintWriter pw = new PrintWriter(output);
			if (increment == 0) {
				pw.println(1);
			} else {
				Client client = new Client(increment - 1, serverSocket.getLocalPort());
				client.clientRun();
				System.out.println(increment);
				int answer = client.getAnswer();
				client.setAnswer(answer * increment);
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
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));
			Server server = new Server(serverSocket);

			while (true) {
				Socket socketClient = serverSocket.accept();
				ClientThread clientThread = server.new ClientThread(
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
