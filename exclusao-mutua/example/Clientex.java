
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Clientex {

	public static void main(String[] args) {
		try {
			Socket cliente = new Socket("localhost", 9999);
			System.out.println("Cliente conectado com o servidor");
			
//			Scanner teclado = new Scanner(System.in);
//			System.out.print("Informe o apelido do usuário: ");
//			String nick = teclado.nextLine();

			PrintStream escrita = new PrintStream(cliente.getOutputStream());
			//manda mensagem para o servidor
			escrita.println("Preciso do recurso 19");
			new Thread() {
				public void run() {
					try {
						Scanner leitura = new Scanner(cliente.getInputStream());			

						while (leitura.hasNext()) {
							System.err.println(leitura.nextLine());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();

//			while(teclado.hasNext()) {
//				escrita.println(nick + ": "+ teclado.nextLine());
//			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
