import java.io.IOException;
import java.net.ServerSocket;


public class SenderTCP {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket.accept();
			serverSocket = new ServerSocket(25565);
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
