
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
 * @return les resultats aux clients qui demandent un fibonacci, avec un
 *         cache prevu.
 * 
 */
public class ServerFibo {
	private ServerSocket serverSocket;

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
			if (increment == 0) { // Si on arrive a zero, on renvoie le
									// resultat.
				pw.println(1);
			} else {
				if (resultats.get(increment) == null) {// On verifie si le
														// resultat est absent
														// des resultats deja
														// presents. Si oui, on
														// le calcule.
					ClientFacto client = new ClientFacto(increment - 1, serverSocket.getLocalPort());
					client.clientRun();
					int answer = client.getAnswer();
					client.setAnswer(answer * increment);
					resultats.put(increment - 1, answer);
					pw.println(answer * increment);
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
		ServerSocket serverSocket = null;
		try {// Un nouveau serveur avec comme parametre le port donne lors de la
				// commande.
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));
			ServerFibo server = new ServerFibo(serverSocket);
			Hashtable<Integer, Integer> resultats = new Hashtable<Integer, Integer>();

			while (true) {// On accepte tous les clients, et on fait un nouveau
							// thread a chaque fois.
				Socket socketClient = serverSocket.accept();
				ClientThread clientThread = server.new ClientThread(socketClient, resultats);
				clientThread.start();
			}
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
