
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Classe du serveur, pour gerer les clients et pouvoir creer d'autres clients.
 * 
 * @return les resultats aux clients qui demandent un fibonacci, avec un cache
 *         prevu.
 * 
 */
public class ServerFibo extends Thread {
	private ServerSocket serverSocket;
	private Hashtable<Integer, Integer> resultats = new Hashtable<Integer, Integer>();
	private static int port1;
	private static int port2;
	/**
	 * Constructeur du serveur
	 * 
	 * @param serverSocket
	 *            le socket du serveur.
	 */
	ServerFibo(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	/**
	 * Classe du client en thread, utilise de façon recursive.
	 *
	 */
	public class ClientThread extends Thread {
		private Socket socket;
		private InputStream input;
		private OutputStream output;
		private Hashtable<Integer, Integer> resultats;

		/**
		 * Constructeur du clientThread
		 * 
		 * @param socket
		 *            le socket du serveur, recupere pour aller le retourner.
		 * @param Resultats
		 *            la Hashtable pour rentrer les résultats au fur et a
		 *            mesure.
		 */
		ClientThread(Socket socket, Hashtable<Integer, Integer> Resultats) {
			try {
				this.socket = socket;
				output = socket.getOutputStream();
				input = socket.getInputStream();
				resultats = Resultats;
			} catch (Exception e) {
			}
		}

		/**
		 * Run du client thread, permet de recevoir l'etape correspondante et
		 * demander au serveur le resultat suivant.
		 */
		@Override
		public void run() {
			Scanner sc = new Scanner(input);
			String text = "";
			if (sc.hasNext()) { // s'il y a un suivant
				text = sc.nextLine();
			}
			int increment = Integer.parseInt(text);
			PrintWriter pw = new PrintWriter(output);
			if (increment <= 2) { // Si on est entre 2 et 0, on renvoie 1, sinon
									// on envoie 0 pour 0.
				if (increment == 0) {
					pw.println(0);
				} else {
					pw.println(1);
				}
			} else {
				if (resultats.get(increment) == null) {// On verifie si le
														// resultat est absent
														// des resultats deja
														// presents. Si oui, on
														// le calcule.
					ClientFibo client1 = new ClientFibo(increment - 1, port1);
					ClientFibo client2 = new ClientFibo(increment - 2, port2);
					//On envoie sur les deux serveurs.
					client2.clientRun();
					client1.clientRun();
					int answer = client1.getAnswer();
					int answer2 = client2.getAnswer();
					resultats.put(increment, answer + answer2);
					pw.println(answer + answer2);
				} else {// On renvoie le resultat stocke si ce n'est pas le cas.
					pw.println(resultats.get(increment));
				}

			}
			pw.flush();
			try {
				this.socket.close();
				sc.close();// On le ferme une fois fini.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		ServerSocket serverSocket1 = null;
		ServerSocket serverSocket2 = null;
		try {// Un nouveau serveur avec comme parametre le port donne lors de la
				// commande.
			port1 = Integer.parseInt(args[0]);
			port2 = Integer.parseInt(args[1]);
			serverSocket1 = new ServerSocket(port1);
			serverSocket2 = new ServerSocket(port2);
			ServerFibo server1 = new ServerFibo(serverSocket1);
			ServerFibo server2 = new ServerFibo(serverSocket2);
			server1.start();
			server2.start();

			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		
		while (true) {// On accepte tous les clients, et on fait un nouveau
			// thread a chaque fois.
			Socket socketClient;
			try {
				socketClient = this.serverSocket.accept();
				ClientThread clientThread = this.new ClientThread(socketClient, resultats);
				clientThread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
