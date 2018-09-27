import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Cliente {
	
	private int id = 0;
	private String hostCoordenador;

//	@Override
//	public void run() {
//		System.out.println("Iniciou");
//		iniciaNovoCliente();
//	}

	public void iniciaNovoCliente() {
		try {
			Socket cliente = new Socket("localhost", 9999);
			System.out.println("Cliente conectado com o servidor");

			PrintStream escrita = new PrintStream(cliente.getOutputStream());
			escrita.println("Qual o meu ID:");
			new Thread() {
				public void run() {
					try {
						Scanner leitura = new Scanner(cliente.getInputStream());

						while (leitura.hasNext()) {
							if (id == 0) {
								id = leitura.nextInt();
								System.out.println("Meu id = " + id);
							}							
							hostCoordenador = leitura.nextLine();
							System.out.println(hostCoordenador);
							
							if (id != 0 && !hostCoordenador.trim().isEmpty()) {
								cliente.close();
								break;
							}else {
								escrita.println("Eleição");
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block  b    
						e.printStackTrace();
					}
				}
			}.start();
									

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int buscaTempoRequisitarProcesso() {
		Random gerador = new Random();
		return gerador.nextInt(15) + 10;
	}
	
	public void solicitarRecurso(PrintStream escrita) {
		Thread thread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(buscaTempoRequisitarProcesso()));
					escrita.println("Solicitando acesso ao recurso");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		thread.start();
	}
}
