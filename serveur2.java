import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class serveur2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serveur;
		Socket client;
		BufferedReader entre;
		PrintWriter sortie;
		Scanner sc= new Scanner(System.in);
		
		try {
			serveur = new ServerSocket(3000);
			client = serveur.accept();
			entre = new BufferedReader(new InputStreamReader(client.getInputStream()));
			sortie = new PrintWriter(client.getOutputStream());
			Thread envoie = new Thread (new Runnable() { /*début_du_thread_envoi*/
				String msg; @Override
				public void run( ) {
					while(true) {
						msg = sc.nextLine();
						sortie.println(msg);
						sortie.flush();
					}
				}
			}); /*fin du thread*/
			envoie.start();
			
			Thread recevoir = new Thread (new Runnable() { /*debut du thread recevoir*/
				String msg; @Override
				public void run( ) {
					try {
						msg = entre.readLine();
						while(msg != null) {	
							System.out.println("Client : " + msg);
							msg = entre.readLine();
						}
						System.out.println ("Client déconnecté");
						sortie.close();
						client.close();
						serveur.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});/*fin du thread*/
			recevoir.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
