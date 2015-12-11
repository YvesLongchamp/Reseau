import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ReceiverUDP {
	/**
	 * Recois des datagrammes
	 * sur le port 50000.
	 */
	public static void main(String[] args) {
		/**
		 * @param aucun, c'est le main.
		 * @return rien, spécifié par le void.
		 */
		DatagramSocket datagramSocket = null;
		try {
			datagramSocket = new DatagramSocket(50000, InetAddress.getLocalHost());
			
			byte[] b = new byte[200];
			DatagramPacket datagramPacket = new DatagramPacket(b,b.length);
			
			datagramSocket.receive(datagramPacket);
			System.out.println(new String(b));
		
		} catch (SocketException e) {
			e.printStackTrace();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		datagramSocket.close();
	}
}
