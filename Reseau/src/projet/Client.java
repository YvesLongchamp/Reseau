package projet;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private int increment;
	private int answer;

	Client(int i) {
		this.increment = i;
		this.answer = 1;
	}

	public static void main(String[] args) {
		Client client = new Client(10);
		client.clientRun();
	}

	public void clientRun() {
		try {
			Socket clientSocket = new Socket(InetAddress.getLocalHost(), 50000);
			PrintStream output = new PrintStream(clientSocket.getOutputStream());
			String text = Integer.toString(this.increment);
			Scanner sc = new Scanner(System.in);
			PrintWriter pw = new PrintWriter(output);
			pw.println(text);
			pw.flush();
//			String texte2 = sc.nextLine();
//			this.answer = Integer.parseInt(texte2);
//			System.out.println("Après le nextLine");
//			System.out.println("après if");
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
