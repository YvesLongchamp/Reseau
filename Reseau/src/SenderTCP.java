import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SenderTCP {
	public static void main(String[] args) {
		try {
			
			Socket client = new Socket(InetAddress.getByName("162.38.111.44"), 50000);
			PrintStream output = new PrintStream(client.getOutputStream());
			String text = "";
			Scanner sc = new Scanner(System.in);
			while(!text.equals("close"))	{
				text = sc.nextLine();
				output.println(text);
			}
			client.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
