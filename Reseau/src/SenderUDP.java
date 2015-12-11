import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class SenderUDP {
	/**
	 * Emet des datagrammes
	 * sur le port 60000.
	 * 
	 */
	public static void main(String[] args) {
		/**
		 *@param aucun c'est le main.
		 *@return rien, spécifié void.
		 */
		DatagramSocket datagramSocket = null;
		DatagramPacket datagramPacket = null;
		/**
		 * le try catch permet de gérer les erreurs. 
		 * Si il est dans une SocketException : applique le premier cas.
		 * S'il est dans un UnknownHostException : applique le second cas.
		 */
		try {
			datagramSocket = new DatagramSocket(60000, InetAddress.getLocalHost());
			String pwait = new String("C'etait pas ecris la methode D:");
			// ici on précise l'adresse de reception et le port de reception.
			datagramPacket = new DatagramPacket(pwait.getBytes(),pwait.length(), InetAddress.getLocalHost(), 50000);
			
			/**
			 * Ici, on utilise un datagramme socket, qui prend en parametre 
			 * un port et une adresse (ici on prend l'adresse locale). On definit ici 
			 * un Socket. Ensuite, on défini un packet, et ici on doit avoir un byte buffer
			 * (d'où le getBytes() du string). Ensuite, il prend la longueur de la chaine.
			 * On utilise ensuite .send du datagramSocket pour envoyer le packet.
			 */
			
			datagramSocket.send(datagramPacket);
			
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
