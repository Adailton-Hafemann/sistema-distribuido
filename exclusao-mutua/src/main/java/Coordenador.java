import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Coordenador {

	private int buscaNovoIdProcesso() {
		Random gerador = new Random();
		return gerador.nextInt(10000);
	}

	private boolean isIdRepetido(List<Processo> listaProcesso, int novoId) {
		return listaProcesso.stream().filter(processo -> processo.getId() == novoId).collect(Collectors.toList())
				.isEmpty();
	}

	private Processo eleicaoNovoCoordenador(List<Processo> listaProcesso) {
		Collections.sort(listaProcesso, (a, b) -> {
			return a.getId() < b.getId() ? -1 : a.getId() > b.getId() ? 1 : 0;
		});
		return listaProcesso.get(0);
	}

	public void coordenador() {
		List<Processo> listaProcesso = new ArrayList();
		Recurso recurso = new Recurso();
		FilaPedidoRecurso filaRecurso = new FilaPedidoRecurso();
		try {

			ServerSocket servidor = new ServerSocket(9999);
			System.out.println("Servidor nomes lendo a porta 9999");

			while (true) {
				Socket socket = servidor.accept();

				new Thread() {
					public void run() {
						System.out.println("Cliente conectou: " + socket.getInetAddress().getHostName());
						System.out.println("Cliente conectou: " + socket.getInetAddress().getHostAddress());
						try {
							PrintStream novoProcesso = new PrintStream(socket.getOutputStream());
							int novoId = buscaNovoIdProcesso();
							while (!isIdRepetido(listaProcesso, novoId)) {
								novoId = buscaNovoIdProcesso();
							}
							listaProcesso.add(new Processo(novoId, novoProcesso, socket));
							novoProcesso.println(novoId);
							Scanner leitura = new Scanner(socket.getInputStream());
							while (leitura.hasNext()) {
								int id = leitura.nextInt();
//								if (text.equals("Recurso")) {
								Processo processo = buscaProcesso(id, listaProcesso);
								if (recurso.isUtilizado()) {
									filaRecurso.insere(processo);
									System.out.println(
											"Recurso sendo utilizado, add a fila o processo: " + processo.getId());
								} else {
									UtilizarRecurso(recurso, processo, filaRecurso);
								}
//								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Processo buscaProcesso(int id, List<Processo> listaProcesso) {
		return listaProcesso.stream().filter(processo -> processo.getId() == id).collect(Collectors.toList()).get(0);
//		return null;
	}

	public int buscaTempoConsumoRecurso() {
		Random gerador = new Random();
		return gerador.nextInt(10) + 5;
	}

	public void buscaProcessoFila(Recurso recurso, FilaPedidoRecurso filaRecurso) {
		if (!filaRecurso.vazia()) {
			UtilizarRecurso(recurso, filaRecurso.remove(), filaRecurso);
		}
	}

	public void UtilizarRecurso(Recurso recurso, Processo processo, FilaPedidoRecurso filaRecurso) {
		recurso.utilizar(processo.getId());
		Thread thread = new Thread(() -> {
			boolean acabou = false;
			while (!acabou) {
				try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(buscaTempoConsumoRecurso()));
					recurso.acabou(processo.getId());
					acabou = true;
					processo.getCliente().println("true");
					buscaProcessoFila(recurso, filaRecurso);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		thread.start();
	}
}
