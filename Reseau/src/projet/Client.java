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
	}

	public static void main(String[] args) {
			Client client = new Client(10);
			client.clientRun();
			System.out.println(client.getAnswer());
	}
	
	public void clientRun() {
		try {
			Socket clientSocket = new Socket(InetAddress.getLocalHost(), 50000);
			PrintStream output = new PrintStream(clientSocket.getOutputStream());
			String text = Integer.toString(this.increment);
			Scanner sc = new Scanner(System.in);
			PrintWriter pw = new PrintWriter(output);
			pw.println(text);
			System.out.println("clientRun avant hasnext");
			pw.flush();
			sc.hasNext();
			System.out.println("clientRun après hasnext");
			this.answer = Integer.parseInt(sc.nextLine());
			clientSocket.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getAnswer() {
		return this.answer;
	}
}
