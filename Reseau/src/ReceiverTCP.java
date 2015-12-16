import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ReceiverTCP {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			String text = "";
			serverSocket = new ServerSocket(50000);
			Socket socketClient = serverSocket.accept();
			InputStream inputStream = socketClient.getInputStream();
			Scanner sc = new Scanner(inputStream);
			
			while (!text.equals("close")) {

				/**
				 * on alloue un nouveau serveur, sur le port 25565 on accepte
				 * les connexions des clients on prends le stream qui rentre on
				 * scan les bits, et on en ressort le string on met un texte on
				 * l'affiche.
				 */
				text = sc.nextLine();
				System.out.println(text);
			}
			sc.close();
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
