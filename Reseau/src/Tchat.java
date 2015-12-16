import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Tchat {
	public static void main(String[] args) {
		try {
			// Init de la connexion du server
			ServerSocket serverSocket = new ServerSocket(50000);
			Socket socketClient = serverSocket.accept();
			InputStream inputStream = socketClient.getInputStream();
			Scanner sc = new Scanner(inputStream);
			
			// Init connexion au server
			Socket client = new Socket(InetAddress.getByName("162.38.111.44"), 60000);
			PrintStream output = new PrintStream(client.getOutputStream());
			String text = "";
			Scanner sc2 = new Scanner(System.in);
			
			while(!text.equals("close"))	{
				// receive message
				text = sc.nextLine();
				System.out.println(text);
				
				// send message
				text = sc2.nextLine();
				output.println(text);
			}
			
			client.close();
			serverSocket.close();
			socketClient.close();
			sc.close();
			sc2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
