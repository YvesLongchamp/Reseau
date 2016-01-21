
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Classe client, pour demander un factoriel, et avoir le résultat.
 *
 */
public class ClientFacto {
	private int increment;
	private int answer;
	private int port;

	/**
	 * Constructeur du client.
	 * 
	 * @param i
	 *            l'increment, pour savoir ou on en est et de quel nombre on
	 *            veut la factorielle.
	 * @param port
	 *            le port ou on veut envoyer les informations.
	 */
	ClientFacto(int i, int port) {
		this.increment = i;
		this.answer = 1;
		this.port = port;
	}

	public static void main(String[] args) {// On recupere les arguments de la
											// console pour creer le client, on
											// le lance puis on affiche le
											// resultat.
		ClientFacto client = new ClientFacto(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		client.clientRun();
		System.out.println(client.getAnswer());
	}

	/**
	 * Ici on envoie au serveur la requete, et on envoie notre demande vis a vis
	 * de la factorielle. On voudra avoir le resultat.
	 */
	public void clientRun() {
		try {
			Socket clientSocket = new Socket(InetAddress.getLocalHost(), this.port);
			PrintStream output = new PrintStream(clientSocket.getOutputStream());
			String text = Integer.toString(this.increment);
			Scanner sc = new Scanner(clientSocket.getInputStream());
			PrintWriter pw = new PrintWriter(output);
			pw.println(text);
			pw.flush();// Nécessaire pour le bon fonctionnement.
			if (sc.hasNext()) {
				String texte2 = sc.nextLine();
				this.answer = Integer.parseInt(texte2);
			}
			clientSocket.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * getter de la reponse.
 * @return la reponse
 */
	public int getAnswer() {
		return this.answer;
	}
/**
 * setter de la reponse.
 * @param i la nouvelle reponse.
 */
	public void setAnswer(int i) {
		this.answer = i;
	}
}
