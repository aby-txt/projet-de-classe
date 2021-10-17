package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket client;
		BufferedReader entre;
		PrintWriter sortie;
		Scanner sc = new Scanner(System.in);
		try {
			client = new Socket("127.0.0.1", 3000);
			sortie = new PrintWriter(client.getOutputStream());
			entre = new BufferedReader(new InputStreamReader(client.getInputStream()));
			Thread envoyer = new Thread(new Runnable() { /*début_du_thread_envoi*/
	             String msg; @Override
	              public void run() {
	                while(true){
	                  msg = sc.nextLine();
	                  sortie.println(msg);
	                  sortie.flush();
	                }
	             }
	         }); /*fin du thread*/
	         envoyer.start();
	         
	         Thread recevoir = new Thread(new Runnable() { /*debut du thread recevoir*/
	             String msg; @Override
	             public void run() {
	                try {
	                  msg = entre.readLine();
	                  while(msg!=null){
	                      System.out.println("Serveur : "+msg);
	                      msg = entre.readLine();
	                   }
	                   System.out.println("Serveur déconecté");
	                   sortie.close();
	                   client.close();
	                 } catch (IOException e) {
	                     e.printStackTrace();
	                 }
	              }
	          }); /*fin du thread*/
	          recevoir.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
