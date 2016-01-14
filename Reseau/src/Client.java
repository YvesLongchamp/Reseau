

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private int increment;
	private int answer;
	private int port;

	Client(int i, int port) {
		this.increment = i;
		this.answer = 1;
		this.port = port;
	}

	public static void main(String[] args) {
		Client client = new Client(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		client.clientRun();
		System.out.println(client.getAnswer());
	}

	public void clientRun() {
		try {
			Socket clientSocket = new Socket(InetAddress.getLocalHost(), this.port);
			PrintStream output = new PrintStream(clientSocket.getOutputStream());
			String text = Integer.toString(this.increment);
			Scanner sc = new Scanner(clientSocket.getInputStream());
			PrintWriter pw = new PrintWriter(output);
			pw.println(text);
			pw.flush();
			if (sc.hasNext()) {
				String texte2 = sc.nextLine();
				this.answer = Integer.parseInt(texte2);
			}
			clientSocket.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getAnswer() {
		return this.answer;
	}

	public void setAnswer(int i) {
		this.answer = i;
	}
}
