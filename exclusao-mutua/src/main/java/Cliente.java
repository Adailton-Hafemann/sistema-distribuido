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

	public void iniciaNovoCliente() {
		try {
			Socket cliente = new Socket("localhost", 9999);
			System.out.println("Cliente conectado com o servidor");

			PrintStream escrita = new PrintStream(cliente.getOutputStream());
			new Thread() {
				public void run() {
					try {
						Scanner leitura = new Scanner(cliente.getInputStream());
						boolean solicitacaoFeita = false;
						boolean solicitacaoRespondida = true;
						while (leitura.hasNext()) {
							if (id == 0) {
								String text = leitura.nextLine();
								id = Integer.parseInt(text);
								System.out.println("Meu id = " + id);
							} else {
								solicitacaoRespondida = leitura.nextBoolean();
							}
							if (solicitacaoRespondida) {
								solicitacaoFeita = false;
							}

							if (!solicitacaoFeita) {
								solicitacaoRespondida = false;
								solicitacaoFeita = true;
								solicitarRecurso(escrita);
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block b
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
			boolean recursoPedido = false;
			while (!recursoPedido) {
				try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(buscaTempoRequisitarProcesso()));
					escrita.println(id);
					System.out.println("Cliente Pediu Recurso: " + id);
					recursoPedido = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		thread.start();
	}
}
